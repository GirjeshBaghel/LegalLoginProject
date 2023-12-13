package com.main.service;

import com.main.Exception.BadRequestException;
import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Admin;
import com.main.entity.Firm;
import com.main.entity.Lawyer;
import com.main.repo.AdminRepository;
import com.main.repo.FirmRepository;
import com.main.repo.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FirmService {


    @Autowired
    private FirmRepository firmRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LawyerRepository lawyerRepository;



    public ResponseEntity<?> addFirmService(Firm firm) {

        Firm existingFirm = firmRepository.findByFirmName(firm.getFirmName());
        if (existingFirm != null) {
            throw new BadRequestException("This firm name already exists");
        }
        Firm existingAEmail = firmRepository.findByManagerEmail(firm.getManagerEmail());
        if (existingAEmail != null) {
            throw new BadRequestException("Manager email already exists in Firm table");
        }
        Admin existingAdmin1 = adminRepository.findByAdminEmail(firm.getManagerEmail());
        if (existingAdmin1 != null) {
            throw new BadRequestException("Manager email already exists in Admin table");
        }
        Lawyer existingLawyer2 = lawyerRepository.findByLawyerEmail(firm.getManagerEmail());
        if (existingLawyer2 != null) {
            throw new BadRequestException("Manager email already exists in Lawyer table");
        }
        String encodedPassword = passwordEncoder.encode(firm.getManagerPassword());
        firm.setManagerPassword(encodedPassword);
        firm.setRegisterDateTime(LocalDateTime.now());

        firmRepository.save(firm);


        String id = firm.getFirmId();
        return ResponseClass.responseId(id, "Firm Added Successfully");
    }


    public ResponseEntity<?> updateFirm(String firmId, Firm updatedFirm) {
        Firm existingFirm = firmRepository.findByFirmId(firmId);

        if (existingFirm == null) {
            throw new ResourceNotFoundException("Firm with given firmId not found");
        }

        Firm firmWithSameName = firmRepository.findByFirmName(updatedFirm.getFirmName());
        if (firmWithSameName != null && !firmWithSameName.getFirmId().equals(firmId)) {
            throw new BadRequestException("Another firm with this name already exists");
        }
        Admin adminWithSameEmail = adminRepository.findByAdminEmail(updatedFirm.getManagerEmail());
        if (adminWithSameEmail != null) {
            throw new BadRequestException("Email already exists in Admin table");
        }
        Lawyer lawyerWithSameEmail = lawyerRepository.findByLawyerEmail(updatedFirm.getManagerEmail());
        if (lawyerWithSameEmail != null) {
            throw new BadRequestException("Email already exists in Lawyer table");
        }
        existingFirm.setFirmName(updatedFirm.getFirmName());
        existingFirm.setManagerEmail(updatedFirm.getManagerEmail());
        firmRepository.save(existingFirm);
        return ResponseClass.response("Firm updated successfully");
    }


    public ResponseEntity<?> findFirmById(String firmId) {
        Firm response =  firmRepository.findByFirmId(firmId);
        if(response == null)
        {
            throw  new ResourceNotFoundException("invalid firm Id");
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> deleteFirmById(String firmId) {
        Firm firm =  firmRepository.findByFirmId(firmId);
        if(firm == null)
        {
            throw  new ResourceNotFoundException("invalid firm Id");
        }
        firmRepository.delete(firm);
        return ResponseClass.response("firm deleted successfully");
    }
}
