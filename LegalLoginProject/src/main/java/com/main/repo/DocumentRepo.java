package com.main.repo;

import com.main.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo  extends JpaRepository<Documents,String> {

    List<Documents> findAllByCases_CaseNumber(String caseNumber);
}
