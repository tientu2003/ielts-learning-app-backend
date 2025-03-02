package com.project.writingservice;

import com.project.writingservice.external.data.IdName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<IdName>> listAllWritingExams() {
        return ResponseEntity.ok(crudWritingService.getListAllWritingExams());
    }

}
