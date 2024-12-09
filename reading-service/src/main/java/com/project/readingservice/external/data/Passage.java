package com.project.readingservice.external.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Passage {

    String difficulty;

    String topic;

    String articleName;

    List<Paragraph> paragraphs;

    List<QuestionGroup> questionGroups;

    public int getNumberOfQuestions() {
        return questionGroups.stream()
                .mapToInt(questionGroup -> questionGroup.getQuestions().size())
                .sum();
    }

}
