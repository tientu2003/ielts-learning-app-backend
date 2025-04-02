package com.project.accountservice.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    void updateTargetBandById(String targetBand, String id);

    void updateListeningBandById(String listeningBand, String id);

    void updateReadingBandById(String readingBand, String id);

    void updateWritingBandById(String writingBand, String id);

    void updateSpeakingBandById(String speakingBand, String id);
}