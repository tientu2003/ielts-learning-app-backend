package com.project.writingservice.controller;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.writingservice.CrudWritingService;
import com.project.writingservice.UserWritingService;
import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/writing")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WritingServiceApi {
    private final CrudWritingService crudWritingService;
    private final UserWritingService userWritingService;
    private final LanguageProficiencyService languageProficiencyService;

    @GetMapping("/user/answer")
    public ResponseEntity<List<BasicUserRecordDTO>> getHistory() {
        List<BasicUserRecordDTO> list = userWritingService.getAllUserHistoryRecords();
        if(list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/summary")
    public ResponseEntity<UserSummary> getWritingSummary() {
        UserSummary result = userWritingService.getWritingSummary();
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/answer")
    public ResponseEntity<String> saveUserAnswer(@RequestBody UserAnswer exam) {
        String url = userWritingService.createUserAnswer(exam);
        if(url != null) {
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/user/answer/{id}")
    public ResponseEntity<DetailRecord> getWritingExamDetail(@PathVariable String id) {
        DetailRecord result = userWritingService.getUserAnswer(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/data/{id}")
    public ResponseEntity<WritingExam> getWritingExam(@PathVariable String id) {
        WritingExam result = crudWritingService.getWritingExamById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/user/tpi")
    public ResponseEntity<List<LanguageProficiencyDTO>> searchTopicProficiency(@RequestBody List<String> topics) {
        List<LanguageProficiencyDTO> list =  languageProficiencyService.getAllTopicProficiencyIndexs(topics);
        if(list == null || list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user/topic-list")
    public ResponseEntity<List<String>> getUserListeningTopics() {
        List<String> list = languageProficiencyService.getAllTopicsByUserId();
        if(list == null || list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
}
