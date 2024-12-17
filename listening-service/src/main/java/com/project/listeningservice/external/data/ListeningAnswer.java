package com.project.listeningservice.external.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListeningAnswer {
    String id;

    String testName;

    List<String> answers;
}
