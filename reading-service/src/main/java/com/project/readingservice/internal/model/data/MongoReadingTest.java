package com.project.readingservice.internal.model.data;

import com.project.readingservice.external.data.AnswerData;
import com.project.readingservice.external.data.NewReadingTest;
import com.project.readingservice.external.data.ReadingTestData;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
@Data
@Document(collection = "reading_exam")
public class MongoReadingTest {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "test_name")
    private String testName;

    @Field(name = "passages")
    private List<MongoPassage> passages;

    public ReadingTestData toReadingTestData(){
        return ReadingTestData.builder()
                .id(id)
                .name(testName)
                .passages(passages.stream()
                        .map(MongoPassage::toPassage)
                        .toList())
                .build();
    }

    public AnswerData toAnswerData(){
        return AnswerData.builder()
                .testId(id)
                .testName(testName)
                .answers(passages.stream()
                        .flatMap(passage -> passage.getAnswer().stream())
                        .toList()
                )
                .recommendations(passages.stream()
                        .flatMap(passage -> passage.toRecommendationData().stream())
                        .toList()
                )
                .build();
    }

    public MongoReadingTest(NewReadingTest newReadingTest){

        this.testName = newReadingTest.getName();

        AtomicInteger answerIndex = new AtomicInteger(0);

        this.passages = newReadingTest.getPassages().stream()
                .map(passage -> {
                    int numberOfQuestions = passage.getNumberOfQuestions();
                    List<String> answers = newReadingTest.getAnswers().subList(answerIndex.get(), answerIndex.get() + numberOfQuestions);
                    answerIndex.addAndGet(numberOfQuestions); // Cập nhật answerIndex
                    return new MongoPassage(passage, answers);
                })
                .toList();
    }


}
