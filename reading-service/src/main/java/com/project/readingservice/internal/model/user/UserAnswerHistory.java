package com.project.readingservice.internal.model.user;

import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.user.BasicReadingHistory;
import com.project.readingservice.external.user.DetailReadingTestRecord;
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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    @NotBlank
    private Duration timeTaken;

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
    @Size(max = 40, min = 40)
    private List<String> suggestions;


    public UserAnswerHistory(UUID userId,
                             UserAnswer userAnswer,
                             Double score,
                             List<Boolean> check,
                             List<String> suggestions) {
        this.userId = userId.toString();
        this.testId = userAnswer.getTestId();
        this.score = score;
        this.createdAt = userAnswer.getCreatedAt();
        this.timeTaken = userAnswer.getTimeTaken();
        this.userAnswers = userAnswer.getAnswers();
        this.suggestions = suggestions;
        this.check = check;
    }

    public DetailReadingTestRecord toDetailReadingTestRecord(AnswerData answerData) {
        return DetailReadingTestRecord.builder()
                .id(this.id)
                .userId(UUID.fromString(this.userId))
                .score(this.score)
                .createAt(this.createdAt)
                .timeTaken(this.timeTaken)
                .answerData(answerData)
                .userAnswers(createListResult())
                .build();
    }


    private List<DetailReadingTestRecord.Result> createListResult() {
        List<DetailReadingTestRecord.Result> results = new ArrayList<>();
        IntStream.range(0, 40).forEach(index ->
                results.add(DetailReadingTestRecord.Result.builder()
                        .check(this.check.get(index))
                        .userAnswer(this.userAnswers.get(index))
                        .suggestion(this.suggestions.get(index))
                        .build())
        );
        return results;
    }


    public BasicReadingHistory toBasicReadingHistory(String testName) {
        return BasicReadingHistory.builder()
                .id(UUID.fromString(this.userId))
                .testName(testName)
                .createdAt(this.createdAt)
                .recordId(this.id)
                .score(this.score)
                .build();
    }
}
