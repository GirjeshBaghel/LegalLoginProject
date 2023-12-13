package com.main.service;

import com.main.Exception.BadRequestException;
import com.main.Exception.ResponseClass;
import com.main.entity.Admin;
import com.main.entity.Firm;
import com.main.entity.Lawyer;
import com.main.repo.AdminRepository;
import com.main.repo.FirmRepository;
import com.main.repo.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
     private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private FirmRepository firmRepository;
    public ResponseEntity<?> addAdmin(Admin admin) {
        Admin existingAdmin = adminRepository.findByAdminEmail(admin.getAdminEmail());
        if (existingAdmin != null) {
            throw new BadRequestException("Email already exists in Admin table");
        }
        Firm existingFirm = firmRepository.findByManagerEmail(admin.getAdminEmail());
        if (existingFirm != null) {
            throw new BadRequestException("Email already exists in Firm table");
        }
        Lawyer existingLawyer = lawyerRepository.findByLawyerEmail(admin.getAdminEmail());
        if (existingLawyer != null) {
            throw new BadRequestException("Email already exists in Lawyer table");
        }
        String encodedPassword = passwordEncoder.encode(admin.getAdminPassword());
        admin.setAdminPassword(encodedPassword);
        adminRepository.save(admin);

        String id = admin.getAdminId();
        return ResponseClass.responseId(id, "SuperAdmin Added Successfully");
    }



}
