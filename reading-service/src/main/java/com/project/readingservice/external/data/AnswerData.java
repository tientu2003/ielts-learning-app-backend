package com.project.readingservice.external.data;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerData {

    String testId;

    String testName;

    List<String> answers;

    List<String> recommendations;

}
