package com.project.readingservice;

import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.data.NewReadingTest;
import com.project.readingservice.external.data.ReadingTestAlreadyExistsException;
import com.project.readingservice.external.data.ReadingTestData;
import com.project.readingservice.external.user.BasicReadingHistory;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.GeneralAssessment;
import com.project.readingservice.external.user.UserAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/reading")
public class ReadingServiceApi {

    final CRUDReadingService crudReadingService;
    final UserReadingService userReadingService;
    public ReadingServiceApi(CRUDReadingService crudReadingService, 
                             UserReadingService userReadingService) {
        this.crudReadingService = crudReadingService;
        this.userReadingService = userReadingService;
    }

    @GetMapping("/user/{id}/review")
    public ResponseEntity<GeneralAssessment> getGeneralAssessment(@PathVariable("id") UUID id) {
        GeneralAssessment data = userReadingService.getReadingGeneralAssessment(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/{id}/answer")
    public ResponseEntity<List<BasicReadingHistory>> getAllBasicReadingHistory(@PathVariable UUID id) {
        List<BasicReadingHistory> data = userReadingService.listUserReadingTestHistory(id);
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @PostMapping("/user/{id}/answer")
    public ResponseEntity<String> sendUserAnswer(@PathVariable("id") String userId, @RequestBody UserAnswer userAnswer) {
        DetailReadingTestRecord received =  userReadingService.saveUserAnswerData(UUID.fromString(userId), userAnswer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{record-id}")
                .buildAndExpand(received.getId())
                .toUri();
        log.info("New Reading Test Record: {}", location);
        return ResponseEntity.created(location).body(received.getId());
    }

    @GetMapping("/user/{id}/answer/{record_id}")
    public ResponseEntity<DetailReadingTestRecord> getDetailReadingAnswerRecord(@PathVariable("id") UUID userId,
                                                                                @PathVariable("record_id") String record_id) {
        DetailReadingTestRecord result = userReadingService.getUserDetailReadingTestHistory(record_id);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<AnswerData> getAnswer(@PathVariable("id") String id){
        log.info(id);
        AnswerData answerData = crudReadingService.getAnswerTest(id);
        if(answerData == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answerData);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<ReadingTestData> getTestData(@PathVariable String id){
        ReadingTestData readingTestData = crudReadingService.getReadingTestData(id);
        if(readingTestData == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(readingTestData);
    }

    @PostMapping
    public ResponseEntity<ReadingTestData> createNewReadingTest(@RequestBody NewReadingTest newReadingTest){
        try{
            ReadingTestData readingTestData = crudReadingService.createNewReadingTest(newReadingTest);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{test-name}")
                    .buildAndExpand(readingTestData.getName())
                    .toUri();

            return ResponseEntity.created(location).build();
        }catch (ReadingTestAlreadyExistsException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReadingTest(@PathVariable String id){
        crudReadingService.deleteReadingTest(id);
        return ResponseEntity.noContent().build();
    }
}
