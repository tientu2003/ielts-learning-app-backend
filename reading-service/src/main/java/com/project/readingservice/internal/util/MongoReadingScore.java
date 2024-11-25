package com.project.readingservice.internal.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import com.project.readingservice.external.util.ReadingScore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Component
public class MongoReadingScore extends ReadingScore {

    public MongoReadingScore() {
        super();
    }

    @Override
    public List<Boolean> checkAnswerAndCalculateScore(List<String> userAnswers, List<String> testAnswers) {

        List<Boolean> returnList = new ArrayList<>();
        AtomicInteger countCorrect = new AtomicInteger();

        IntStream.range(0, testAnswers.size()).forEach(
            index -> {
                boolean result = false;

                String testAnswer = testAnswers.get(index);
                String userAnswer = userAnswers.get(index);

                if(testAnswer.contains("[OR]")){
                    String[] options = testAnswer.split("\\[OR]");
                    for(String option : options){
                        if (option.trim().equalsIgnoreCase(userAnswer)) {
                            result = true;
                            countCorrect.getAndIncrement();
                            break;
                        }
                    }
                } else if (testAnswer.contains("/")) {

                    int slashIndex = testAnswer.indexOf('/');
                    // Find the start of the word containing '/'
                    int start = testAnswer.lastIndexOf(' ', slashIndex) + 1;
                    // Find the end of the word containing '/'
                    int end = testAnswer.indexOf(' ', slashIndex);
                    if (end == -1) { // If no space after '/', take till the end of the string
                        end = testAnswer.length();
                    }

                    // Extract the pre-string (before the substring with '/')
                    String preString = testAnswer.substring(0, start).trim();
                    // Extract the post-string (after the substring with '/')
                    String postString = testAnswer.substring(end).trim();
                    String substringWithSlash = testAnswer.substring(start, end);

                    String[] answers = substringWithSlash.split("/");

                    for(String answer : answers){
                        String contcatAnswer = preString + " " + answer + " " + postString;
                        if(userAnswer.equalsIgnoreCase(contcatAnswer.trim())){
                            result = true;
                            countCorrect.getAndIncrement();
                            break;
                        }
                    }

                } else if (testAnswer.contains("(") && testAnswer.contains(")")) {
                    // Full normalized answer
                    String normalizedFullAnswer = testAnswer.replaceAll("[()]", "").trim();
                    // Remove parentheses part for alternate answer
                    String normalizedAlternateAnswer = testAnswer.replaceAll("\\(.*?\\)", "").trim();

                    if(userAnswer.equalsIgnoreCase(normalizedFullAnswer) || userAnswer.equalsIgnoreCase(normalizedAlternateAnswer)) {
                        result = true;
                        countCorrect.getAndIncrement();
                    }

                } else {
                    if(userAnswer.equalsIgnoreCase(testAnswer)){
                        result = true;
                        countCorrect.getAndIncrement();
                    }
                }

                returnList.add(result);
            }
        );

        this.setScore(countCorrect.get());

        return returnList;
    }
}
