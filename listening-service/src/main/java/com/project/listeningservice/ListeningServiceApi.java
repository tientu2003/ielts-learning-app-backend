package com.project.listeningservice;

import com.project.common.LanguageProficiencyDTO;
import com.project.common.LanguageProficiencyService;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.common.dto.UserSummary;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.UserAnswer;
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
@RequestMapping("/api/listening")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListeningServiceApi {
    final CrudListeningService crudListeningService;
    final UserListeningService userListeningService;
    final LanguageProficiencyService languageProficiencyService;

    @GetMapping("/user/summary")
    public ResponseEntity<UserSummary> getSummary() {
        UserSummary data = userListeningService.getListeningScore();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/answer")
    public ResponseEntity<List<BasicUserRecordDTO>> getAllListeningRecords() {
        List<BasicUserRecordDTO> data = userListeningService.listAllListeningHistory();
        if(data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(data);
        }
    }

    @PostMapping("/user/answer")
    public ResponseEntity<String> sendUserAnswer(@RequestBody UserAnswer userAnswer) {
        String received =  userListeningService.createUserAnswer(userAnswer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{record-id}")
                .buildAndExpand(received)
                .toUri();
        return ResponseEntity.created(location).body(received);
    }

    @GetMapping("/user/answer/{record_id}")
    public ResponseEntity<DetailRecord> getDetailListeningAnswerRecord( @PathVariable("record_id") String record_id) {
        DetailRecord result = userListeningService.getListeningDetailRecord(record_id);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/data/answer/{id}")
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
