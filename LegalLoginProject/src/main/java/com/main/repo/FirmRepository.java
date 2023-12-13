package com.main.repo;

import com.main.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmRepository  extends JpaRepository<Firm,String> {

    Firm findByFirmId(String firmId);
    Firm findByFirmName(String firmName);
    Firm findByManagerEmail(String email);
    Firm findByManagerEmailAndManagerPassword(String email, String pass);
    Firm findByManagerPassword(String pass);
    List<Firm> findByFirmStatus(boolean firmStatus);

}
