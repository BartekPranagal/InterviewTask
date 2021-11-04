package com.pranagal.bartek.luxmedinterviewbackend.repository;

import com.pranagal.bartek.luxmedinterviewbackend.model.dao.UserDataEntity;
import com.pranagal.bartek.luxmedinterviewbackend.model.dto.BasicUserDataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, Long> {

    @Query("select userData from UserDataEntity userData" +
            " where userData.firstName like %:query% or userData.lastName like %:query%" +
            " or userData.personalId like %:query%")
    List<UserDataEntity> searchByQuery(String query);

}
