package com.project.readingservice.external.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QuestionGroup {

    List<String> context;

    String diagramUrl;

    ReadingTestType readingTestType;

    List<String> questions;

}

