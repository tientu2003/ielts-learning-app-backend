package com.project.readingservice;

import com.project.readingservice.external.AnswerData;
import com.project.readingservice.external.NewReadingTest;
import com.project.readingservice.external.ReadingTestAlreadyExistsException;
import com.project.readingservice.external.ReadingTestData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reading")
public class ReadingServiceApi {

    final CRUDReadingService crudReadingService;

    public ReadingServiceApi(CRUDReadingService crudReadingService) {
        this.crudReadingService = crudReadingService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getAllReadingTests(){
        return ResponseEntity.ok(crudReadingService.listAllReadingTestName());
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<AnswerData> getAnswer(@PathVariable("id") String id){
        AnswerData answerData = crudReadingService.getAnswerTest(id);
        if(answerData == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(answerData);
    }

    @GetMapping("/data/{test-name}")
    public ResponseEntity<ReadingTestData> getTestData(@PathVariable("test-name") String testName){
        ReadingTestData readingTestData = crudReadingService.getReadingTestData(testName);
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
