package com.project.readingservice.internal.model.data;

import com.project.readingservice.external.data.QuestionGroup;
import com.project.readingservice.external.data.ReadingTestType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoQuestionGroup {

    @Field(name = "context")
    private List<MongoQuestionContext> questionContexts;

    @Field(name = "questions_type")
    private String questionsType;

    @Field(name = "questions")
    private List<MongoQuestion> questions;

    public MongoQuestionGroup(QuestionGroup questionGroup, List<String> answers, int startQuestionIndex) {
        this.questionsType = questionGroup.getReadingTestType().name();

        List<MongoQuestion> mongoQuestionList = new ArrayList<>();
        for(int i = 0; i < questionGroup.getQuestions().size(); i++) {

            MongoQuestion mongoQuestion = new MongoQuestion();
            mongoQuestion.setQuestionNumber(String.valueOf(startQuestionIndex+i+1));
            mongoQuestion.setQuestionText(questionGroup.getQuestions().get(i));
            mongoQuestion.setAnswer(answers.get(i));

            mongoQuestionList.add(mongoQuestion);
        }

        this.questions = mongoQuestionList;

        this.questionContexts = questionGroup.getContext()
                .stream()
                .map(MongoQuestionContext::new)
                .toList();
    }

    public QuestionGroup toQuestionGroup() {
        ReadingTestType readingTestType = null;
        if (questionsType.equals("unknown")) {
            readingTestType = ReadingTestType.UNKNOWN;
        }
        return QuestionGroup.builder()
                .context(questionContexts.stream()
                        .map(MongoQuestionContext::getData)
                        .toList()
                )
                .questions(questions.stream()
                        .map(MongoQuestion::getQuestionText)
                        .toList()
                )
                .readingTestType(readingTestType)
                .build();
    }

    public List<String> getAnswer(){
        return questions.stream()
                .map(MongoQuestion::getAnswer)
                .toList();
    }
}
