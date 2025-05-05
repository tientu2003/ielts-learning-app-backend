package com.project.readingservice.internal;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.SuggestionService;
import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.CRUDReadingService;
import com.project.readingservice.external.util.UserService;
import com.project.readingservice.internal.model.user.UserReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingSuggestionServiceImpl implements SuggestionService {

    final UserService userService;

    final CRUDReadingService crudReadingService;

    final LanguageProficiencyService languageProficiencyService;

    final UserReadingRepository userReadingRepository;


    @Override
    public String getPersonalRecommendation() {
        return "";
    }

    @Override
    public BasicExamDTO getSuggestedNextExam() {
        String userId = userService.getUserId();
        List<BasicExamDTO> allExams = crudReadingService.listAllReadingTestName();

        // Get user's language proficiency data for all topics
        List<LanguageProficiencyDTO> topicProficiencies = languageProficiencyService.getAllTopicProficiencyIndexs(
                languageProficiencyService.getAllTopicsByUserId()
        );
        if (topicProficiencies == null) {
            return allExams.isEmpty() ? null : allExams.get((int) (Math.random() * allExams.size()));
        }

        // Get user's recent exam history (last 2 months)
        Date twoMonthsAgo = Date.from(LocalDate.now().minusWeeks(2)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<String> recentExamIds = userReadingRepository.findByUserIdAndDateAfter(userId, twoMonthsAgo);

        // Filter out recently taken exams
        List<BasicExamDTO> eligibleExams = allExams.stream()
                .filter(exam -> !recentExamIds.contains(exam.getId()))
                .toList();

        if (eligibleExams.isEmpty()) {
            return null;
        }

        // Find weakest topics based on TPI (Topic Proficiency Index)
        List<String> weakestTopics = topicProficiencies.stream()
                .sorted(Comparator.comparing(LanguageProficiencyDTO::getTpi))
                .map(LanguageProficiencyDTO::getTopic)
                .limit(3)
                .toList();

        // Score each eligible exam based on multiple factors
        return eligibleExams.stream()
                .map(exam -> new ExamScore(exam, calculateExamScore(exam, weakestTopics, topicProficiencies, true)))
                .max(Comparator.comparing(ExamScore::getScore))
                .map(ExamScore::getExam)
                .orElse(eligibleExams.getFirst());
    }
}
