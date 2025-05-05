package com.project.writingservice;

import com.project.writingservice.external.data.BasicWritingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/writing")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WritingServicePublicApi {
    private final CrudWritingService crudWritingService;

    @GetMapping("/list")
    public ResponseEntity<List<BasicWritingDTO>> listAllWritingExams() {
        List<BasicWritingDTO> data = crudWritingService.getListAllWritingExams();
        if(data == null || data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/data/topic-list")
    public ResponseEntity<List<String>> listAllWritingTopics() {
        List<String> data = crudWritingService.listAllTopics();
        if(data == null || data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(data);
    }

}
