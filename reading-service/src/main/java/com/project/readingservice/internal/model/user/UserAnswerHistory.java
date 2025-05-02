package com.project.readingservice.internal.model.user;

import com.project.common.TopicProficiency;
import com.project.readingservice.external.data.ReadingAnswer;
import com.project.readingservice.external.user.DetailRecord;
import com.project.readingservice.external.user.UserAnswer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Document("reading_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswerHistory {

    @MongoId
    private String id;

    @Field
    @NotBlank
    private String userId;

    @Field
    @NotBlank
    private String testId;

    @Field
    @NotBlank
    private Date createdAt;

    @Field
    private String timeTaken;

    @Field
    @NotBlank
    private Double score;

    @Field
    @Size(max = 40, min = 40)
    private List<String> userAnswers;

    @Field
    @Size(max = 40, min = 40)
    private List<Boolean> check;

    @Field
    private List<TopicProficiency> topicProficiency;

    public UserAnswerHistory(String userId,
                             UserAnswer userAnswer,
                             Double score,
                             List<Boolean> check,
                             ReadingAnswer readingAnswer ){
        this.userId = userId;
        this.testId = userAnswer.getTestId();
        this.score = score;
        this.createdAt = new Date();
        this.timeTaken = userAnswer.getTimeTaken();
        this.userAnswers = userAnswer.getAnswers();
        this.check = check;

        List<TopicProficiency> topicProficiencies = new ArrayList<>();
        int startIndex = 0;
        List<Integer> noQuestionSession = readingAnswer.getNumberQuestions();
        for (int i = 0; i < noQuestionSession.size() ; i++) {
            int sessionSize = noQuestionSession.get(i);
            List<Boolean> sessionCheck = check.subList(startIndex, startIndex + sessionSize);

            double sessionAccuracy = sessionCheck.stream()
                    .mapToDouble(correct -> correct ? 1.0 : 0.0)
                    .average()
                    .orElse(0.0);

            TopicProficiency topicProficiency;
            topicProficiency = TopicProficiency.builder()
                    .topic(readingAnswer.getTopics().get(i))
                    .skill(2) // 1 for Reading
                    .difficulty(readingAnswer.getDifficulties().get(i))
                    .bandWeight(TopicProficiency.bandWeight(score))
                    .sessionWeight(TopicProficiency.calculateSessionWeight(i + 1))
                    .accuracy(sessionAccuracy)
                    .dateTaken(new Date())
                    .build();
            topicProficiencies.add(topicProficiency);
            startIndex += sessionSize;
        }

        this.topicProficiency = topicProficiencies;
    }

    public DetailRecord toDetailReadingTestRecord(ReadingAnswer readingAnswer) {
        return DetailRecord.builder()
                .id(this.id)
                .userId(this.userId)
                .score(this.score)
                .date(this.createdAt)
                .answer(readingAnswer)
                .results(createListResult())
                .build();
    }


    private List<DetailRecord.Result> createListResult() {
        List<DetailRecord.Result> results = new ArrayList<>();
        IntStream.range(0, 40).forEach(index ->
                results.add(DetailRecord.Result.builder()
                        .check(this.check.get(index))
                        .userAnswer(this.userAnswers.get(index))
                        .build())
        );
        return results;
    }


}
