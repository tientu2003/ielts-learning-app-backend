package com.project.readingservice;

import com.project.common.dto.BasicExamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/reading")
public class ReadingServicePublicApi {

    final CRUDReadingService crudReadingService;

    public ReadingServicePublicApi(CRUDReadingService crudReadingService) {
        this.crudReadingService = crudReadingService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BasicExamDTO>> getAllReadingTests(){
        return ResponseEntity.ok(crudReadingService.listAllReadingTestName());
    }

}
