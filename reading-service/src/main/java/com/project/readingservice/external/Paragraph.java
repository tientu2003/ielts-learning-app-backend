package com.project.readingservice.external;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Paragraph {

    String title;

    String paragraph;

}
