package com.project.readingservice.external.data;

import lombok.*;

import java.util.List;

@Data
@Builder
public class ReadingExam {

    String id;

    String name;

    List<Passage> passages;

    @Data
    @Builder
    public static class Passage {
        String articleName;

        List<Paragraph> paragraphs;

        List<QuestionGroup> questionGroups;

        @Data
        @Builder
        public static class Paragraph {

            String title;

            String paragraph;

        }

        @Builder
        @Data
        public static class QuestionGroup {

            List<String> context;

            String diagramUrl;

            String readingTestType;

            List<String> questions;

        }

    }
}
