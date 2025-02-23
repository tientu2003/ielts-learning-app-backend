package com.project.writingservice.external.data;


import lombok.Data;

@Data
public class WritingExam {
    private String id;
    private String name;
    private String context;
    private Integer task;
    private String diagram_url;
}
