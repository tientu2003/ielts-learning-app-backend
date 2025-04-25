package com.project.common;

import com.project.common.constraints.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicProficiency {
    private Topic topic;
    // 1 -> Listening
    // 2 -> Reading
    private Integer skill;
    private Double difficulty;
    // Chỉ số thành thạo
    // bandWeight là trọng số tổng điểm của bài thi nếu bài thi được 9.0 thì trọng số là 1
    // lpi =  bandWeight, sectionWeight, accuracy
    private Double bandWeight;
    private Double sessionWeight;
    private Double accuracy;
    private Date dateTaken;

    public Double getTopicProficiencyIndex(){
        return bandWeight*sessionWeight*accuracy*difficulty*timeDecay(Date.from(dateTaken.toInstant()));
    }

    public Double getFinalWeight(){
        return bandWeight*sessionWeight*difficulty*timeDecay(Date.from(dateTaken.toInstant()));
    }

    private static Double timeDecay(Date testDate) {
        LocalDate testLocalDate = testDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysAgo = ChronoUnit.DAYS.between(testLocalDate, currentDate);
        return Math.exp(-daysAgo / 30.0);
    }

    public static Double bandWeight(Double bandScore) {
        return Math.pow(bandScore, 2) / 81.0;
    }

    public static Double calculateSessionWeight(Integer sessionNumber) {
        if (sessionNumber <= 0) {
            throw new IllegalArgumentException("Session number must be positive.");
        }
        return 1 - Math.exp(-0.3 * sessionNumber);
    }

}
