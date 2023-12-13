package com.main.repo;

import com.main.entity.Firm;
import com.main.entity.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer,String> {

    Lawyer findByLawyerId(String lawyerId);

    Lawyer findByLawyerEmail(String email);
    @Query("SELECT a FROM Lawyer a WHERE a.firms.firmId =:firmId  AND a.lawyerId=:lawyerId")
    Lawyer findByLawyerIdFirmId(@Param("firmId") String firmId, @Param("lawyerId") String lawyerId);


    @Query("SELECT a FROM Lawyer a WHERE a.firms.firmId = :firmId")
    List<Lawyer> findLawyerByFirmId(@Param("firmId") String firmId);

    @Query("SELECT a FROM Lawyer a WHERE a.firms.firmId = :firmId AND a.lawyerEmail = :lawyerEmail")
    Lawyer findByLawyerIdAndLawyerEmail(@Param("firmId") String firmId, @Param("lawyerEmail") String lawyerEmail);

    Lawyer findByLawyerEmailAndLawyerPassword(String email, String pass);
    List<Lawyer> findByLawyerStatusAndFirms_FirmId(boolean lawyerStatus, String firmId);

}
