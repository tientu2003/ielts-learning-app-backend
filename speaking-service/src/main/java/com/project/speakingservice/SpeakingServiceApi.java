package com.project.speakingservice;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.speakingservice.external.data.SpeakingExam;
import com.project.speakingservice.external.user.DetailRecord;
import com.project.speakingservice.external.user.UserAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speaking")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpeakingServiceApi {

    private final CrudSpeakingService crudSpeakingService;
    private final UserSpeakingService userSpeakingService;


    @GetMapping("/user/answer")
    public ResponseEntity<List<BasicUserRecordDTO>> getHistory() {
        List<BasicUserRecordDTO> list = userSpeakingService.listUserSpeakingHistory();
        if(list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/user/answer")
    public ResponseEntity<String> saveUserAnswer(@RequestBody UserAnswer data) {
        String url = userSpeakingService.saveUserSpeaking(data);
        if(url != null) {
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/user/answer/{id}")
    public ResponseEntity<DetailRecord> getWritingExamDetail(@PathVariable String id) {
        DetailRecord result = userSpeakingService.getUserSpeakingDetailRecord(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<SpeakingExam> getWritingExam(@PathVariable String id) {
        SpeakingExam result = crudSpeakingService.getSpeakingExamById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
}
