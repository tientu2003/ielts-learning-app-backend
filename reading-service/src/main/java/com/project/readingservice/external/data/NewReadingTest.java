package com.project.readingservice.external.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewReadingTest {

    String name;

    String level;

    List<Passage> passages;

    List<String> answers;

    List<String> recommendations;

}
