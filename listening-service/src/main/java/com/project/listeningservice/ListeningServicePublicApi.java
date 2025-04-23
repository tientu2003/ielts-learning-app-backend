package com.project.listeningservice;

import com.project.common.dto.BasicExamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/listening")
public class ListeningServicePublicApi {

    final CrudListeningService crudListeningService;

    public ListeningServicePublicApi(CrudListeningService crudListeningService) {
        this.crudListeningService = crudListeningService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BasicExamDTO>> getAllListeningExam(){
        return ResponseEntity.ok(crudListeningService.listAllListeningExams());
    }
}
