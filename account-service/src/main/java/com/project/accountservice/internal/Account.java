package com.project.accountservice.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "ACCOUNT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date timeTarget;

    @Column(name = "TARGET_BAND")
    private String targetBand;

    @Column(name = "LISTENING_BAND")
    private String listeningBand;

    @Column(name = "READING_BAND")
    private String readingBand;

    @Column(name = "WRITING_BAND")
    private String writingBand;

    @Column(name = "SPEAKING_BAND")
    private String speakingBand;
}