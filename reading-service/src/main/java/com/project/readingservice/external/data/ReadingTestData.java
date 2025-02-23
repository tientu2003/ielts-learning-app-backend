package com.project.readingservice.external.data;

import lombok.*;

import java.util.List;

@Data
@Builder
public class ReadingTestData {

    String id;

    String name;

    List<Passage> passages;

}
