package com.example.demoauth.repository;

import com.example.demoauth.models.AdditionalJournalColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionColumnRepository  extends JpaRepository<AdditionalJournalColumn, Long> {

    List<AdditionalJournalColumn> findByClassJournal_Id(Long id);

//    AdditionalJournalColumn f/indByClassJournal_IdAndDate(Long classJournal_id, String date);

    AdditionalJournalColumn findByClassJournal_IdAndDateAndColumnName(long id,String date, String colName);
}
