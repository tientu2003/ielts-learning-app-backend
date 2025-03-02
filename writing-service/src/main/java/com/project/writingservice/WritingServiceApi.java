package com.project.writingservice;

import com.project.writingservice.external.data.WritingExam;
import com.project.writingservice.external.user.DetailRecord;
import com.project.writingservice.external.user.UserAnswer;
import com.project.writingservice.external.user.UserSimpleRecord;
import com.project.writingservice.external.user.WritingSummary;
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

    @GetMapping("/exam/{id}")
    public ResponseEntity<WritingExam> getWritingExam(@PathVariable String id) {
        WritingExam result = crudWritingService.getWritingExamById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveUserAnswer(@RequestBody UserAnswer exam) {
        log.info("Saving user answer: {}", exam);
        String url = userWritingService.createUserAnswer(exam);
        if(url != null) {
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailRecord> getWritingExamDetail(@PathVariable String id) {
        DetailRecord result = userWritingService.getUserAnswer(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<UserSimpleRecord>> getHistory(@PathVariable String id) {
        List<UserSimpleRecord> list = userWritingService.getAllUserHistoryRecords(id);
        if(list != null && !list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<WritingSummary> getWritingSummary(@PathVariable String id) {
        WritingSummary result = userWritingService.getWritingSummary(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }
}
