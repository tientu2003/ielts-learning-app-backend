package com.project.readingservice.internal.model.data;

import com.project.readingservice.external.data.ReadingAnswer;
import com.project.readingservice.external.data.ReadingExam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
@NoArgsConstructor
@Data
@Document(collection = "reading_exam")
public class MongoReadingExam {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "test_name")
    private String testName;

    @Field(name = "passages")
    private List<MongoPassage> passages;

    @Field(name = "topics")
    private List<String> topics;

    @Field(name = "difficulties")
    private List<Double> difficulties;

    @Field(name = "levels")
    private List<String> levels;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class MongoPassage {
        @Field(name = "article_title")
        private String articleTitle;

        @Field(name = "article_context")
        private List<MongoParagraph> paragraphs;

        @Field(name = "question_groups")
        private List<MongoQuestionGroup> questionGroups;

        public List<String> getAnswer() {
            return questionGroups.stream()
                    .flatMap(mongoQuestionGroup -> mongoQuestionGroup.getAnswer().stream())
                    .toList();
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class MongoParagraph {

            @Field(name = "letter")
            private String letter;

            @Field(name = "paragraph")
            private String paragraph;

        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MongoQuestionGroup {

            @Field(name = "context")
            private List<MongoQuestionContext> questionContexts;

            @Field(name = "questions_type")
            private String questionsType;

            @Field(name = "questions")
            private List<MongoQuestion> questions;


            public List<String> getAnswer(){
                return questions.stream()
                        .map(MongoQuestion::getAnswer)
                        .toList();
            }
            @AllArgsConstructor
            @NoArgsConstructor
            @Data
            public static class MongoQuestion {

                @Field(name = "question_number")
                private String questionNumber;

                @Field(name = "question_text")
                private String questionText;

                @Field(name = "answer")
                private String answer;

            }

            @AllArgsConstructor
            @NoArgsConstructor
            @Data
            public static class MongoQuestionContext {

                @Field(name = "data")
                private String data;

            }

        }

    }


    public ReadingExam toReadingExam() {
        return ReadingExam.builder()
                .id(id)
                .name(testName)
                .passages(passages.stream()
                        .map(mongoPassage -> ReadingExam.Passage.builder()
                                .articleName(mongoPassage.getArticleTitle())
                                .paragraphs(mongoPassage.getParagraphs().stream()
                                        .map(mongoParagraph -> ReadingExam.Passage.Paragraph.builder()
                                                .title(mongoParagraph.getLetter())
                                                .paragraph(mongoParagraph.getParagraph())
                                                .build())
                                        .toList())
                                .questionGroups(mongoPassage.getQuestionGroups().stream()
                                        .map(mongoQuestionGroup -> ReadingExam.Passage.QuestionGroup.builder()
                                                .context(mongoQuestionGroup.getQuestionContexts().stream()
                                                        .map(MongoPassage.MongoQuestionGroup.MongoQuestionContext::getData)
                                                        .toList())
                                                .readingTestType(mongoQuestionGroup.getQuestionsType())
                                                .questions(mongoQuestionGroup.getQuestions().stream()
                                                        .map(MongoPassage.MongoQuestionGroup.MongoQuestion::getQuestionText)
                                                        .toList())
                                                .build())
                                        .toList())
                                .build())
                        .toList())
                .build();
    }

    public ReadingAnswer toAnswerData(){
        List<Integer> numberOfQuestions = passages.stream()
                .map(passage -> passage.getQuestionGroups().parallelStream()
                        .mapToInt(group -> group.getQuestions().size())
                        .sum())
                        .toList();
        return ReadingAnswer.builder()
                .testId(id)
                .testName(testName)
                .answers(passages.stream()
                        .flatMap(passage -> passage.getAnswer().stream())
                        .toList())
                .topics(topics)
                .difficulties(difficulties)
                .numberQuestions(numberOfQuestions)
                .build();
    }

}
