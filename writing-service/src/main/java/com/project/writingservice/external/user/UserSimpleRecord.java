package com.project.writingservice.external.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserSimpleRecord {
    private String id;
    private String name;
    private Double score;
    private Date date;
}

