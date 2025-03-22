package com.project.speakingservice.external;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeakingExam {
    private String id;
    private String partOne;
    private String partTwo;
    private String partThree;
}
