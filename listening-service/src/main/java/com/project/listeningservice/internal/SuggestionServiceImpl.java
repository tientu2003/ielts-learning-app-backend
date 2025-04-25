package com.project.listeningservice.internal;

import com.project.common.SuggestionService;
import com.project.common.dto.BasicExamDTO;
import com.project.listeningservice.CrudListeningService;
import com.project.listeningservice.external.util.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SuggestionServiceImpl implements SuggestionService {

    final UserService userService;

    final CrudListeningService crudListeningService;

    @Override
    public String getPersonalRecommendation() {
        return "";
    }

    @Override
    public BasicExamDTO getSuggestedNextExam() {
        String userId = userService.getUserId();
        List<BasicExamDTO> exams = crudListeningService.listAllListeningExams();
        return exams.getFirst();
    }
}
