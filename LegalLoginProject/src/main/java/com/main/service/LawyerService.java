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
import java.util.ArrayList;
import java.util.List;

@Service
public class LawyerService {



    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;


    public ResponseEntity<?> addLawyer(String firmId, Lawyer lawyer) {
        Firm firm = firmRepository.findByFirmId(firmId);
        if (firm == null) {
            throw new ResourceNotFoundException("Invalid firm ID");
        }

        Lawyer existingLawyer = lawyerRepository.findByLawyerEmail(lawyer.getLawyerEmail());
        if (existingLawyer != null) {
            throw new BadRequestException("This email is already in use");
        }

        Admin existingAdmin = adminRepository.findByAdminEmail(lawyer.getLawyerEmail());
        if (existingAdmin != null) {
            throw new BadRequestException("This email is already in use in Admin table");
        }

        // Check if the lawyer's email already exists in other Firm tables
        Firm  firmsWithSameEmail= firmRepository.findByManagerEmail(lawyer.getLawyerEmail());
        if (firmsWithSameEmail != null) {
            throw new BadRequestException("This email is already in use in other Firm(s)");
        }

        // Perform the necessary operations
        String encodedPassword = passwordEncoder.encode(lawyer.getLawyerPassword());
        lawyer.setLawyerPassword(encodedPassword);
        lawyer.setFirms(firm);
        lawyer.setRegisterDateTime(LocalDateTime.now());
        lawyerRepository.save(lawyer);

        List<Lawyer> lawyers = firm.getLawyers();
        lawyers.add(lawyer);
        firm.setLawyers(lawyers);
        firmRepository.save(firm);

        String id = lawyer.getLawyerId();
        return ResponseClass.responseId(id, "Lawyer Added Successfully");
    }


    public ResponseEntity<?> updateLawyer(String firmId, String lawyerId, Lawyer updatedLawyer) {
        Firm firm = firmRepository.findByFirmId(firmId);
        if (firm == null) {
            throw new ResourceNotFoundException("Invalid firm ID");
        }
        Lawyer existingLawyer = lawyerRepository.findByLawyerIdFirmId(firmId, lawyerId);
        if (existingLawyer == null) {
            throw new ResourceNotFoundException("Invalid lawyer ID");
        }

        Lawyer existingLawyerByEmail = lawyerRepository.findByLawyerEmail(updatedLawyer.getLawyerEmail());
        if (existingLawyerByEmail != null && !existingLawyerByEmail.getLawyerId().equals(lawyerId)) {
            throw new BadRequestException("This email is already in use by another lawyer");
        }

        Admin existingAdminByEmail = adminRepository.findByAdminEmail(updatedLawyer.getLawyerEmail());
        if (existingAdminByEmail != null) {
            throw new BadRequestException("This email is already in use by an admin");
        }

        Firm firmsWithSameEmail = firmRepository.findByManagerEmail(updatedLawyer.getLawyerEmail());
        if (firmsWithSameEmail != null) {
            throw new BadRequestException("This email is already in use by other Firm(s)");
        }

        existingLawyer.setLawyerDOB(updatedLawyer.getLawyerDOB());
        existingLawyer.setLawyerEmail(updatedLawyer.getLawyerEmail());
        existingLawyer.setLawyerPhone(updatedLawyer.getLawyerPhone());
        existingLawyer.setLawyerFirstName(updatedLawyer.getLawyerFirstName());
        existingLawyer.setLawyerLastName(updatedLawyer.getLawyerLastName());
        String encodedPassword = passwordEncoder.encode(updatedLawyer.getLawyerPassword());
        existingLawyer.setLawyerPassword(encodedPassword);

        lawyerRepository.save(existingLawyer);

        return ResponseClass.response("Lawyer Updated Successfully");
    }


    public ResponseEntity<?> getLawyerById(String firmId, String lawyerId) {
        Firm firm = firmRepository.findByFirmId(firmId);
        if(firm == null)
        {
            throw  new ResourceNotFoundException("invalid firm id");
        }
        Lawyer lawyer1 = lawyerRepository.findByLawyerIdFirmId(firmId,lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("invalid lawyer id");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(lawyer1);

    }

    public ResponseEntity<?> getAllLawyerInFirm(String firmId) {
        Firm firm = firmRepository.findByFirmId(firmId);
        if(firm == null)
        {
            throw  new ResourceNotFoundException("invalid firm id");
        }
        List<Lawyer> response = lawyerRepository.findLawyerByFirmId(firmId);
        return  ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<?> deleteLawyerById(String firmId, String lawyerId) {
        Firm firm = firmRepository.findByFirmId(firmId);
        if(firm == null)
        {
            throw  new ResourceNotFoundException("invalid firm id");
        }
        Lawyer lawyer1 = lawyerRepository.findByLawyerIdFirmId(firmId,lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("invalid lawyer id");
        }
        lawyerRepository.delete(lawyer1);
        return ResponseClass.response("Lawyer deleted successfully");
    }


    public ResponseEntity<?> findAllLawyer() {
        List<Lawyer> lawyers = lawyerRepository.findAll();

        List<Lawyer> response = new ArrayList<>();

        for (Lawyer lawyer : lawyers) {
            Lawyer lawyerDTO = new Lawyer();
            lawyerDTO.setFirmName(lawyer.getFirms().getFirmName());
            response.add(lawyerDTO);
        }
        return ResponseEntity.ok(response);
        }


}
