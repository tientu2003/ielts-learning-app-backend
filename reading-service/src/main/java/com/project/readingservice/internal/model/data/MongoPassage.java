package com.project.readingservice.internal.model.data;

import com.project.readingservice.external.data.Passage;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MongoPassage {
    @Field(name = "article_title")
    private String articleTitle;

    @Field(name = "article_context")
    private List<MongoParagraph> paragraphs;

    @Field(name = "question_groups")
    private List<MongoQuestionGroup> questionGroups;

    public MongoPassage(Passage passage, List<String> answers) {
        this.articleTitle = passage.getArticleName();

        this.paragraphs = passage.getParagraphs().stream()
                .map(MongoParagraph::new)
                .toList();

        AtomicInteger counter = new AtomicInteger(0);

        this.questionGroups = passage.getQuestionGroups().stream()
                .map(questionGroup -> {
                    int start = counter.get();
                    List<String> groupAnswer = answers.subList(counter.get(), counter.get() + questionGroup.getQuestions().size());
                    counter.addAndGet(questionGroup.getQuestions().size());

                    return new MongoQuestionGroup(questionGroup, groupAnswer, start);
                })
                .toList();
    }

    public Passage toPassage() {
        return Passage.builder()
                .articleName(articleTitle)
                .paragraphs(paragraphs.stream()
                        .map(MongoParagraph::toParagraph)
                        .toList()
                )
                .questionGroups(questionGroups.stream()
                        .map(MongoQuestionGroup::toQuestionGroup)
                        .toList())
                .build();
    }

    public List<String> getAnswer() {
        return questionGroups.stream()
                .flatMap(mongoQuestionGroup -> mongoQuestionGroup.getAnswer().stream())
                .toList();
    }

    public List<String> toRecommendationData() {
        return new ArrayList<>();
    }
}
