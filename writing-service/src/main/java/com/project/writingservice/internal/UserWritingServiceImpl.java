package com.project.writingservice.internal;

import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
import com.project.common.dto.BasicExamDTO;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.writingservice.CrudWritingService;
import com.project.writingservice.UserWritingService;
import com.project.writingservice.internal.util.UserService;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.internal.entity.data.WritingExamRepository;
import com.project.writingservice.internal.entity.user.MongoUserWritingRecord;
import com.project.writingservice.internal.entity.user.UserWritingRecordRepository;
import com.project.writingservice.internal.util.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserWritingServiceImpl implements UserWritingService {

    final WritingExamRepository writingExamRepository;

    final UserWritingRecordRepository userWritingRecordRepository;

    final UserService userService;

    final CrudWritingService crudWritingService;

    final SuggestionService suggestionService;

    final LanguageProficiencyService languageProficiencyService;

    final AiService aiService;

    @Override
    public String createUserAnswer(UserAnswer userAnswer) {
        return writingExamRepository.findById(userAnswer.getExamId())
                .map(exam -> {
                    MongoUserWritingRecord newRecord = userWritingRecordRepository.save(new MongoUserWritingRecord(userAnswer, userService.getUserId(), exam.getTopic()));
                    aiService.sendUserAnswerToAi(newRecord.getId(),exam.getContext(), userAnswer.getAnswer());
                    return newRecord.getId();
                })
                .orElse(null); // Return null if the exam is not found
    }



    @Override
    public DetailRecord getUserAnswer(String id) {

        Optional<MongoUserWritingRecord> optional = userWritingRecordRepository.findById(id);
        if(optional.isPresent()){
            MongoUserWritingRecord userWritingRecord = optional.get();
            WritingExam writingExam = crudWritingService.getWritingExamById(userWritingRecord.getExamId());
            return optional.get().toDetailRecord(writingExam);
        }
        return null;
    }

    @Override
    public List<BasicUserRecordDTO> getAllUserHistoryRecords() {
        String userId = userService.getUserId();
        return userWritingRecordRepository.findAllByUserIdWithTestName(userId);
    }

    @Override
    public UserSummary getWritingSummary() {
        String userId = userService.getUserId();
        BasicExamDTO exam = suggestionService.getSuggestedNextExam();
        return UserSummary.builder()
                .userId(userId)
                .averageScore(userWritingRecordRepository.getAverageFinalScoreByUserId(userId))
                .nextTestId(exam.getId())
                .testName(exam.getTestName())
                .topics(languageProficiencyService.getAllTopicsByUserId())
                .personalRecommendation(suggestionService.getPersonalRecommendation())
                .skillLanguageProficiency(languageProficiencyService.getSkillProficiencyDTO())
                .build();
    }
}
