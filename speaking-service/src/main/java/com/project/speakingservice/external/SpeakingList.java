package com.project.speakingservice.external;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeakingList {
    private String id;
    private String name;
}
