package com.project.readingservice.external.data;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadingAnswer {

    String testId;

    String testName;

    List<String> answers;

    List<Integer> numberQuestions;

    List<String> topics;

    List<Double> difficulties;
}
