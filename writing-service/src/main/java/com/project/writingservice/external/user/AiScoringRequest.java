package com.project.writingservice.external.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiScoringRequest {
    private String id;
    private String prompt;
    public AiScoringRequest(String id, String question, List<String> answer) {
        this.id = id;
        this.prompt = "###Question: " + question + "###Answer: " + String.join(" ", answer) + "###";
    }
}
