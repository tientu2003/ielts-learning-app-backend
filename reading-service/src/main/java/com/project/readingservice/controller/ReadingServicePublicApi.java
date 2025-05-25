package com.project.readingservice.controller;

import com.project.common.dto.BasicExamDTO;
import com.project.readingservice.CRUDReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/reading")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadingServicePublicApi {

    final CRUDReadingService crudReadingService;

    @GetMapping("/list")
    public ResponseEntity<List<BasicExamDTO>> getAllReadingTests(){
        return ResponseEntity.ok(crudReadingService.listAllReadingTestName());
    }

    @GetMapping("/data/topic-list")
    public ResponseEntity<List<String>> getAllTopicsTest(){
        return ResponseEntity.ok(crudReadingService.listAllTopics());
    }
}
