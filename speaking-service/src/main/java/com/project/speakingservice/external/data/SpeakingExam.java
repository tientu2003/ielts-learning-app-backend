package com.project.speakingservice.external.data;

import com.project.speakingservice.external.IdQuestion;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SpeakingExam {
    private String id;
    private String testName;
    private Integer type;
    List<IdQuestion> partOne;
    IdQuestion  partTwo;
    List<IdQuestion>  partThree;
}
