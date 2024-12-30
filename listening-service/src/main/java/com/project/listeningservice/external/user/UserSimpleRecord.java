package com.project.listeningservice.external.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSimpleRecord {
    String id;
    String name;
    Double score;
    String date;
}
