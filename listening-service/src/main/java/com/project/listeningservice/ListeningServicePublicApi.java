package com.project.listeningservice;

import com.project.common.dto.BasicExamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/listening")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListeningServicePublicApi {

    final CrudListeningService crudListeningService;

    @GetMapping("/list")
    public ResponseEntity<List<BasicExamDTO>> getAllListeningExam(){
        return ResponseEntity.ok(crudListeningService.listAllListeningExams());
    }

    @GetMapping("/data/topic-list")
    public ResponseEntity<List<String>> getAvailableListeningTopics() {
        List<String> list = crudListeningService.listAllTopics();
        if(list == null || list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

}
