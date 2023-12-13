package com.main.repo;

import com.main.entity.Cases;
import com.main.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasesRepo extends JpaRepository<Cases,String> {


    Cases findByCaseNumber(String caseId);

    @Query("SELECT a FROM Cases a WHERE a.clients.clientId =:clientId  AND a.caseNumber=:caseNumber")
    Cases findByCaseNumberClientId(@Param("clientId") String clientId, @Param("caseNumber") String caseNumber);

    @Query("SELECT a FROM Cases a WHERE a.clients.clientId = :clientId")
    List<Cases> findAllCasesByClientId(@Param("clientId") String clientId);

    List<Cases> findByCaseStatusAndClients_ClientId(boolean status, String clientId);

}
