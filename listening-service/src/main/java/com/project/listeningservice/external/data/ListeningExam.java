package com.project.listeningservice.external.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListeningExam {

    private String examId;
    private String examName;

    private List<Recording> recording;

    @Data
    @AllArgsConstructor
    public static class Recording{
        private String audioUrl;
        private List<String> transcript;
        private List<QuestionGroup> questionGroups;

        @Data
        @AllArgsConstructor
        public static class QuestionGroup{
            private String questionType;
            private List<String> context;
            private List<List<String>> contextTable;
            private List<Question> questions;

            @Data
            @AllArgsConstructor
            public static class Question{
                private String questionNumber;
                private String questionText;
                private List<String> answerOptions;
                private String answer;
            }

        }
    }

}


