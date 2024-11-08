package com.project.readingservice.internal.model;

import com.project.readingservice.external.Paragraph;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MongoParagraph {

    @Field(name = "letter")
    private String letter;

    @Field(name = "paragraph")
    private String paragraph;

    public MongoParagraph(Paragraph paragraph) {
        this.letter = paragraph.getTitle();
        this.paragraph = paragraph.getParagraph();
    }

    public Paragraph toParagraph() {
        return Paragraph.builder()
                .title(letter)
                .paragraph(paragraph)
                .build();
    }

}

