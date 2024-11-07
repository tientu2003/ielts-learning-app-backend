package com.project.accountservice.internal;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Lob
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Lob
    @Column(name = "PASSWORD", nullable = false)
    private String password;

}