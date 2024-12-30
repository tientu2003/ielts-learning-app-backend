package com.project.listeningservice.internal.model.user;

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

import java.util.List;
import java.util.stream.IntStream;

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

    public MongoUserHistory(UserAnswer userAnswer,String userId , List<Boolean> check, Double score) {
        this.userId = userId;
        this.check = check;
        this.score = score;
        this.testId = userAnswer.getTestId();
        this.timeTaken = userAnswer.getTimeTaken();
        this.createdAt = userAnswer.getCreatedAt();
        this.userAnswers = userAnswer.getAnswers();
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
