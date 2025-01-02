package com.project.listeningservice;

import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.ListeningSummary;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.user.UserSimpleRecord;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/listening")
public class ListeningServiceApi {
    final CrudListeningService crudListeningService;
    final UserListeningService userListeningService;

    public ListeningServiceApi(CrudListeningService crudListeningService, UserListeningService userListeningService) {
        this.crudListeningService = crudListeningService;
        this.userListeningService = userListeningService;
    }

    @GetMapping("/user/{id}/summary")
    public ResponseEntity<ListeningSummary> getSummary(@PathVariable("id") String id) {
        ListeningSummary data = userListeningService.getListeningScore(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/{id}/answer")
    public ResponseEntity<List<UserSimpleRecord>> getAllListeningRecords(@PathVariable("id") String id) {
        List<UserSimpleRecord> data = userListeningService.listAllListeningHistory(id);
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @PostMapping("/user/{id}/answer")
    public ResponseEntity<String> sendUserAnswer(@PathVariable("id") String userId, @RequestBody UserAnswer userAnswer) {
        String received =  userListeningService.createUserAnswer(userAnswer,userId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{record-id}")
                .buildAndExpand(received)
                .toUri();
        log.info("New Listening User Record: {}", location);
        return ResponseEntity.created(location).body(received);
    }

    @GetMapping("/user/{id}/answer/{record_id}")
    public ResponseEntity<DetailRecord> getDetailListeningAnswerRecord(@PathVariable("id") String userId,
                                                                     @PathVariable("record_id") String record_id) {
        DetailRecord result = userListeningService.getListeningDetailRecord(record_id);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<ListeningAnswer> getAnswer(@PathVariable("id") String id){
        ListeningAnswer answerData = crudListeningService.getListeningAnswer(id);
        if(answerData == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answerData);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<ListeningExam> getListeningExamData(@PathVariable String id){
        ListeningExam listeningExam = crudListeningService.getListeningExamById(id);
        if(listeningExam == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listeningExam);
    }

}
