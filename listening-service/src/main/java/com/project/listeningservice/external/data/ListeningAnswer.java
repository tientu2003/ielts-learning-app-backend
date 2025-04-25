package com.project.listeningservice.external.data;

import com.project.common.constraints.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListeningAnswer {
    String id;

    String testName;

    List<String> answers;

    List<Integer> numberQuestions;

    List<Topic> topics;

    List<Double> difficulties;
}
