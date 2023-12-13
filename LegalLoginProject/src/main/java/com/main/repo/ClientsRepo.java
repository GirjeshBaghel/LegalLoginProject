package com.main.repo;

import com.main.entity.Clients;
import com.main.entity.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, String> {

    Clients findByClientId(String clientId);
    Clients findByClientEmail(String email);
    @Query("SELECT a FROM Clients a WHERE a.lawyers.lawyerId =:lawyerId  AND a.clientId=:clientId")
    Clients findByClientIdLawyerId(@Param("lawyerId") String lawyerId, @Param("clientId") String clientId);

    @Query("SELECT a FROM Clients a WHERE a.lawyers.lawyerId = :lawyerId")
    List<Clients> findClientsByLawyersId(@Param("lawyerId") String lawyerId);

    List<Clients> findByClientStatusAndLawyers_LawyerId(boolean clientStatus, String lawyerId);


}
