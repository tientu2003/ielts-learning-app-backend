package com.project.speakingservice.internal.entity.user;

import com.project.common.dto.BasicUserRecordDTO;
import com.project.speakingservice.external.IdQuestion;
import com.project.speakingservice.external.user.DetailRecord;
import com.project.speakingservice.external.user.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@Document(collection = "speaking_user")
public class MongoUserHistory {
    @Id
    private String id;

    @Field
    private String userId;

    @Field
    private Date createDate;

    @Field
    private String testName;
    
    @Field
    private Double score;

    @Field
    private List<MongoUserAnswerDetail> answersOne;
    @Field
    private MongoUserAnswerDetail answersTwo;
    @Field
    private List<MongoUserAnswerDetail> answersThree;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "speaking_user")
    public static class MongoUserAnswerDetail{
        @Field
        private Integer number;
        @Field
        private String topic;
        @Field
        private String question;
        @Field
        private String url;
    }

    public MongoUserHistory(String userId, UserAnswer userAnswer) {
        this.userId = userId;
        this.testName = userAnswer.getTestName();
        this.createDate = new Date();
        this.answersOne = userAnswer.getAnswersOne().stream().map( e ->
                new MongoUserAnswerDetail(e.getNumber(),e.getTopic(), e.getQuestion(), e.getUrl())).toList();
        IdQuestion partTwo = userAnswer.getAnswersTwo();
        if(partTwo == null || partTwo.getUrl() == null || partTwo.getUrl().isBlank()){
            this.answersTwo = null;
        }else{
            this.answersTwo = new MongoUserAnswerDetail(partTwo.getNumber(),partTwo.getTopic(), partTwo.getQuestion(), partTwo.getUrl());
        }
        this.answersThree = userAnswer.getAnswersThree().stream().map( e ->
                new MongoUserAnswerDetail(e.getNumber(),e.getTopic(), e.getQuestion(), e.getUrl())).toList();
    }

    public BasicUserRecordDTO toBasicUserRecordDTO() {
        return BasicUserRecordDTO.builder()
                .date(this.createDate)
                .score(this.score)
                .id(this.id)
                .name(this.testName)
                .topics(Stream.of(
                                answersOne.parallelStream().map(MongoUserAnswerDetail::getTopic),
                                Stream.of(answersTwo).map(MongoUserAnswerDetail::getTopic),
                                answersThree.parallelStream().map(MongoUserAnswerDetail::getTopic))
                        .flatMap(s -> s)
                        .distinct()
                        .toList())
                .build();
    }

    public DetailRecord toDetailRecord() {
        return DetailRecord.builder()
                .id(this.id)
                .testName(this.testName)
                .score(String.valueOf(this.score))
                .partOne(this.answersOne.stream()
                        .map(a -> DetailRecord.UserAnswerDetail.builder()
                                .number(a.getNumber())
                                .topic(a.getTopic())
                                .question(a.getQuestion())
                                .url(a.getUrl())
                                .build())
                        .toList())
                .partTwo(DetailRecord.UserAnswerDetail.builder()
                        .number(this.answersTwo.getNumber())
                        .topic(this.answersTwo.getTopic())
                        .question(this.answersTwo.getQuestion())
                        .url(this.answersTwo.getUrl())
                        .build())
                .partThree(this.answersThree.stream()
                        .map(a -> DetailRecord.UserAnswerDetail.builder()
                                .number(a.getNumber())
                                .topic(a.getTopic())
                                .question(a.getQuestion())
                                .url(a.getUrl())
                                .build())
                        .toList())
                .build();
    }
}
