package com.project.readingservice.internal.model.user;

import com.project.common.TopicProficiency;
import com.project.common.dto.BasicUserRecordDTO;
import com.project.readingservice.external.data.AnswerData;
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
    private List<TopicProficiency> topicProficiency;

    public UserAnswerHistory(String userId,
                             UserAnswer userAnswer,
                             Double score,
                             List<Boolean> check ){
        this.userId = userId;
        this.testId = userAnswer.getTestId();
        this.score = score;
        this.createdAt = new Date();
        this.timeTaken = userAnswer.getTimeTaken();
        this.userAnswers = userAnswer.getAnswers();
        this.check = check;
    }

    public DetailReadingTestRecord toDetailReadingTestRecord(AnswerData answerData) {
        return DetailReadingTestRecord.builder()
                .id(this.id)
                .userId(this.userId)
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
                        .build())
        );
        return results;
    }


    public BasicUserRecordDTO toBasicReadingHistory(String testName) {
        return BasicUserRecordDTO.builder()
                .id(this.id)
                .name(testName)
                .date(this.createdAt)
                .score(this.score)
                .topics(topicProficiency.stream().map(TopicProficiency::getTopic).toList())
                .build();
    }
}
