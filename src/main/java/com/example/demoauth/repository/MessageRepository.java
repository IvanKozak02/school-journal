package com.example.demoauth.repository;

import com.example.demoauth.models.Message;
import com.example.demoauth.models.UserPersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByUserPersonalData(UserPersonalData userPersonalData);


//    void deleteMessagesByUserPersonalData(UserPersonalData userPersonalData);

//    void deleteMessagesByUserPersonalDataIn(List<UserPersonalData> userPersonalData);

    void removeMessagesByUserPersonalDataIn(Collection<List<UserPersonalData>> userPersonalData);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_messages WHERE user_personal_data_id = :user_personal_data_id", nativeQuery = true)
    void deleteMessage(@Param("user_personal_data_id") String user_personal_data_id);

}
