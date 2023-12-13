package com.main.repo;

import com.main.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepo extends JpaRepository<Tasks,String> {

    Tasks findByTaskId(String taskId);

    @Query("SELECT a FROM Tasks a WHERE a.cases.caseNumber =:caseNumber  AND a.taskId=:taskId")
    Tasks findByTaskIdCaseNumber(@Param("caseNumber") String caseNumber, @Param("taskId") String taskId);

    @Query("SELECT a FROM Tasks a WHERE a.cases.caseNumber = :caseNumber")
    List<Tasks> findAllTaskByCases(@Param("caseNumber") String caseNumber);

    @Query("SELECT t FROM Tasks t WHERE t.cases.caseNumber = :caseNumber ORDER BY t.lastModification DESC")
   List<Tasks> findLatestModificationByCaseNumber(String caseNumber);


    List<Tasks> findByTaskStatusAndCases_CaseNumber(boolean status, String caseNumber);
}
