package com.project.listeningservice.external.data;

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
}
