package com.project.readingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.data.NewReadingTest;
import com.project.readingservice.external.data.ReadingTestData;
import com.project.readingservice.external.errors.ReadingTestAlreadyExistsException;
import com.project.readingservice.external.user.DetailReadingTestRecord;
import com.project.readingservice.external.user.UserAnswer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reading")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingServiceApi {

    final CRUDReadingService crudReadingService;
    final UserReadingService userReadingService;

    @GetMapping("/user/review")
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
        DetailReadingTestRecord received =  userReadingService.saveUserAnswerData(userAnswer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{record-id}")
                .buildAndExpand(received.getId())
                .toUri();
        log.info("New Reading Test Record: {}", location);
        return ResponseEntity.created(location).body(received.getId());
    }

    @GetMapping("/user/answer/{record_id}")
    public ResponseEntity<DetailReadingTestRecord> getDetailReadingAnswerRecord(@PathVariable("record_id") String record_id) {
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
