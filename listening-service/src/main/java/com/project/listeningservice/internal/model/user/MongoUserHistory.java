package com.project.listeningservice.internal.model.user;

import com.project.common.TopicProficiency;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.UserAnswer;
import com.project.listeningservice.external.user.UserSimpleRecord;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.text.SimpleDateFormat;

@Data
@Document("listening_user")
@NoArgsConstructor
public class MongoUserHistory {
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
    private String createdAt;

    @Field
    @NotBlank
    private String timeTaken;

    @Field
    @NotBlank
    private Double score;

    @Field
    @Size(max = 40)
    private List<String> userAnswers;

    @Field
    @Size(max = 40)
    private List<Boolean> check;

    @Field
    private List<TopicProficiency> topicProficiency;

    public MongoUserHistory(UserAnswer userAnswer, String userId, List<Boolean> check, Double score, List<Integer> noQuestionSession) {
        this.userId = userId;
        this.check = check;
        this.score = score;
        this.testId = userAnswer.getTestId();
        this.timeTaken = userAnswer.getTimeTaken();
        this.createdAt = userAnswer.getCreatedAt();
        this.userAnswers = userAnswer.getAnswers();

        List<TopicProficiency> topicProficiencies = new ArrayList<>();
        int startIndex = 0;

        for (int i = 0; i < noQuestionSession.size(); i++) {
            int sessionSize = noQuestionSession.get(i);
            List<Boolean> sessionCheck = check.subList(startIndex, startIndex + sessionSize);

            double sessionAccuracy = sessionCheck.stream()
                    .mapToDouble(correct -> correct ? 1.0 : 0.0)
                    .average()
                    .orElse(0.0);

            TopicProficiency topicProficiency = null;
            try {
                topicProficiency = TopicProficiency.builder()
                        .skill(1) // 1 for Listening
                        .bandWeight(TopicProficiency.bandWeight(score))
                        .sessionWeight(TopicProficiency.calculateSessionWeight(i + 1))
                        .noQuestionAccuracy(sessionAccuracy)
                        .dateTaken(new SimpleDateFormat("yyyy-MM-dd").parse(userAnswer.getCreatedAt()))
                        .build();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            topicProficiencies.add(topicProficiency);
            startIndex += sessionSize;
        }
    
        this.topicProficiency = topicProficiencies;
    }

    public UserSimpleRecord toUserSimpleRecord(String testName){
        return UserSimpleRecord.builder()
                .id(this.id)
                .score(this.score)
                .name(testName)
                .date(this.createdAt)
                .build();
    }

    public DetailRecord toDetailRecord(ListeningAnswer listeningAnswer) {
        // Build the list of DetailRecord.Result objects
        List<DetailRecord.Result> results = IntStream.range(0, userAnswers.size())
                .mapToObj(i -> DetailRecord.Result.builder()
                        .userAnswer(userAnswers.get(i))
                        .check(check.get(i))
                        .build())
                .toList();

        // Return the DetailRecord object
        return DetailRecord.builder()
                .id(this.id)
                .userId(this.userId)
                .score(this.score)
                .answer(listeningAnswer)
                .results(results)
                .date(this.createdAt)
                .build();
    }
}
