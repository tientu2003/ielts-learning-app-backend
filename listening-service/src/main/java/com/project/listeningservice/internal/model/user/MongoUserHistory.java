package com.project.listeningservice.internal.model.user;

import com.project.common.TopicProficiency;
import com.project.listeningservice.external.data.ListeningAnswer;
import com.project.listeningservice.external.user.DetailRecord;
import com.project.listeningservice.external.user.UserAnswer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Data
@Document("listening_user")
@NoArgsConstructor
public class MongoUserHistory {
    @Id
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

    public MongoUserHistory(UserAnswer userAnswer,
                            String userId,
                            List<Boolean> check,
                            Double score,
                            ListeningAnswer listeningAnswer) {
        this.userId = userId;
        this.check = check;
        this.score = score;
        this.testId = userAnswer.getTestId();
        this.timeTaken = userAnswer.getTimeTaken();
        this.createdAt = new Date();
        this.userAnswers = userAnswer.getAnswers();

        List<TopicProficiency> topicProficiencies = new ArrayList<>();
        int startIndex = 0;
        List<Integer> noQuestionSession = listeningAnswer.getNumberQuestions();
        for (int i = 0; i < noQuestionSession.size() ; i++) {
            int sessionSize = noQuestionSession.get(i);
            List<Boolean> sessionCheck = check.subList(startIndex, startIndex + sessionSize);

            double sessionAccuracy = sessionCheck.stream()
                    .mapToDouble(correct -> correct ? 1.0 : 0.0)
                    .average()
                    .orElse(0.0);

            TopicProficiency topicProficiency;
            topicProficiency = TopicProficiency.builder()
                    .topic(listeningAnswer.getTopics().get(i))
                    .skill(1) // 1 for Listening
                    .difficulty(listeningAnswer.getDifficulties().get(i))
                    .accuracy(sessionAccuracy)
                    .dateTaken(new Date())
                    .build();
            topicProficiencies.add(topicProficiency);
            startIndex += sessionSize;
        }
    
        this.topicProficiency = topicProficiencies;
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
