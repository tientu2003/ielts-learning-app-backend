package com.project.speakingservice;

import com.project.common.dto.BasicExamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/speaking")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpeakingServicePublicApi {
    final CrudSpeakingService crudSpeakingService;

    @GetMapping("/list")
    public ResponseEntity<List<BasicExamDTO>> getAllSpeakingTests(){
        List<BasicExamDTO> data = crudSpeakingService.listAllSpeakingExam();
        if(data == null || data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/data/topic-list")
    public ResponseEntity<List<String>> listAllWritingTopics() {
        List<String> data = crudSpeakingService.listAllTopics();
        if(data == null || data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(data);
    }
}
