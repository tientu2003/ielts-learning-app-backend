package com.project.accountservice.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(value = "update Account set target_band = :targetBand where id like :id returning *" , nativeQuery = true)
    Account updateTargetBandById(String targetBand, String id);
    @Query(value = "update Account set listening_band = :listeningBand where id like :id returning *" , nativeQuery = true)
    Account updateListeningBandById(String listeningBand, String id);
    @Query(value = "update Account set reading_band = :readingBand where id like :id returning *" , nativeQuery = true)
    Account updateReadingBandById(String readingBand, String id);
    @Query(value = "update Account set writing_band = :writingBand where id like :id returning *" , nativeQuery = true)
    Account updateWritingBandById(String writingBand, String id);
    @Query(value = "update Account set speaking_band = :speakingBand where id like :id returning *" , nativeQuery = true)
    Account updateSpeakingBandById(String speakingBand, String id);
    @Query(value = "update Account set date_of_birth = :dateOfBirth where id like :id returning *" , nativeQuery = true)
    Account updateDateOfBirthById(Date dateOfBirth, String id);
    @Query(value = "update Account set time_target = :dateOfTarget where id like :id returning *" , nativeQuery = true)
    Account updateDateTargetById(Date dateOfTarget, String id);

}