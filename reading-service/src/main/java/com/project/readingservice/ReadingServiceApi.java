package com.project.readingservice;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.readingservice.external.data.ReadingAnswer;
import com.project.readingservice.external.data.ReadingExam;
import com.project.readingservice.external.user.DetailRecord;
import com.project.readingservice.external.user.UserAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reading")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingServiceApi {

    final CRUDReadingService crudReadingService;
    final UserReadingService userReadingService;
    final LanguageProficiencyService languageProficiencyService;

    @GetMapping("/user/summary")
    public ResponseEntity<UserSummary> getGeneralAssessment() {
        UserSummary data = userReadingService.getReadingGeneralAssessment();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/answer")
    public ResponseEntity<List<BasicUserRecordDTO>> getAllBasicReadingHistory() {
        List<BasicUserRecordDTO> data = userReadingService.listUserReadingTestHistory();
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @PostMapping("/user/answer")
    public ResponseEntity<String> sendUserAnswer( @RequestBody UserAnswer userAnswer) {
        String received =  userReadingService.saveUserAnswerData(userAnswer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{record-id}")
                .buildAndExpand(received)
                .toUri();
        return ResponseEntity.created(location).body(received);
    }

    @GetMapping("/user/answer/{record_id}")
    public ResponseEntity<DetailRecord> getDetailReadingAnswerRecord(@PathVariable("record_id") String record_id) {
        DetailRecord result = userReadingService.getUserDetailReadingTestHistory(record_id);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/data/answer/{id}")
    public ResponseEntity<ReadingAnswer> getAnswer(@PathVariable("id") String id){
        ReadingAnswer readingAnswer = crudReadingService.getAnswerTest(id);
        if(readingAnswer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(readingAnswer);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<ReadingExam> getTestData(@PathVariable String id){
        ReadingExam readingExam = crudReadingService.getReadingTestData(id);
        if(readingExam == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(readingExam);
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
