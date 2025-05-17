package com.project.listeningservice.internal.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "ai_suggestion")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AiSuggestion {
    @Id
    private String id;

    @Field
    @NotBlank
    private String userId;

    @Field
    @NotBlank
    private String suggestion;

    @Field
    @NotBlank
    private Date createdAt;
}
