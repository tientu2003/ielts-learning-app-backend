package com.project.readingservice.external.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Paragraph {

    String title;

    String paragraph;

}
