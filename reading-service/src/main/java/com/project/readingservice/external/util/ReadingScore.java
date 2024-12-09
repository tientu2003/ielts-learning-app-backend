package com.project.readingservice.external.util;

import lombok.Data;

import java.util.List;

@Data
public abstract class ReadingScore {

    private Double score;

    private static double[] scale = {
            0.0, 1.0, 2.0, 2.5, 2.5, 3.0, 3.0, 3.5, 3.5, 3.5, // 0-9
            4.0, 4.0, 4.0, 4.5, 4.5, 4.5, 5.0, 5.0, 5.0, 5.0, // 10-19
            5.5, 5.5, 5.5, 6.0, 6.0, 6.0, 6.0, 6.5, 6.5, 6.5, // 20-29
            7.0, 7.0, 7.0, 7.5, 7.5, 8.0, 8.0, 8.5, 8.5, 9.0, // 30-39
            9.0 // 40
    };

    public String toStringFormatScore() {
        return score.toString();
    }

    public ReadingScore(){

    }

    public void setScore(int numberCorrectAnswers) {
        this.score = scale[numberCorrectAnswers];
    }

    public ReadingScore(int numberCorrectAnswer) {

        if(numberCorrectAnswer > 40 || numberCorrectAnswer < 0) {
            throw new IllegalArgumentException("Number of answers must be between 0 and 4");
        }
        this.score = scale[numberCorrectAnswer];
    }


    public abstract List<Boolean> checkAnswerAndCalculateScore(List<String> userAnswers, List<String> testAnswers);

}
