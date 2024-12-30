package com.project.listeningservice.internal.model.data;

import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.data.ListeningExam;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "listening_exam")
public class MongoListeningExam {

    @Id
    private String examId;
    @Field(name = "test_name")
    private String examName;

    @Field(name = "recordings")
    private List<MongoRecording> recordings;

    @Data
    public static class MongoRecording {
        @Field(name = "audio_url")
        private String audioUrl;
        @Field(name = "transcript")
        private List<String> transcript;
        @Field(name = "question_groups")
        private List<MongoQuestionGroup> mongoQuestionGroups;

        @Data
        public static class MongoQuestionGroup {
            @Field(name = "type")
            private String questionType;
            @Field(name = "context")
            private List<String> context;
            @Field(name = "context_table")
            private List<List<String>> contextTable;
            @Field(name = "questions")
            private List<MongoQuestion> questions;

            @Data
            public static class MongoQuestion {
                @Field("question_number")
                private String questionNumber;
                @Field("question_text")
                private String questionText;
                @Field("answer_options")
                private List<String> answerOptions;
                @Field("answer")
                private String answer;
            }

            public Integer getQuestionNumber() {
                return this.questions.size();
            }

        }
    }

    public ListeningExam toListeningExam() {
        return new ListeningExam(
                this.examId,
                this.examName,
                this.recordings.stream().map(recording -> new ListeningExam.Recording(
                        recording.getAudioUrl(),
                        recording.getTranscript(),
                        recording.getMongoQuestionGroups().stream().map(group -> new ListeningExam.Recording.QuestionGroup(
                                group.getQuestionType(),
                                group.getContext(),
                                group.getContextTable(),
                                group.getQuestions().stream().map(question -> new ListeningExam.Recording.QuestionGroup.Question(
                                        question.getQuestionNumber(),
                                        question.getQuestionText(),
                                        question.getAnswerOptions() != null ? question.getAnswerOptions(): null,
                                        question.getAnswer()
                                )).collect(Collectors.toList())
                        )).collect(Collectors.toList())
                )).collect(Collectors.toList())
        );
    }

    public ListeningAnswer toListeningAnswer() {
        // Extract the exam ID and exam name
        String id = this.examId;
        String testName = this.examName;

        // Extract answers from all the questions
        List<String> answers = this.recordings.stream()
                .flatMap(recording -> recording.getMongoQuestionGroups().stream())
                .flatMap(group -> group.getQuestions().stream())
                .map(MongoRecording.MongoQuestionGroup.MongoQuestion::getAnswer)
                .collect(Collectors.toList());

        List<Integer> numberQuestions = this.recordings.stream()
                .map(recording -> recording.getMongoQuestionGroups().stream()
                        .mapToInt(group -> group.getQuestions().size())
                        .sum())
                .collect(Collectors.toList());

        // Return a new ListeningAnswer
        return new ListeningAnswer(id, testName, answers, numberQuestions);
    }

}
