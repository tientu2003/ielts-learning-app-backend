package com.project.readingservice.external;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class AnswerData {

    String testId;

    String testName;

    List<String> answers;

    List<String> recommendations;

}
