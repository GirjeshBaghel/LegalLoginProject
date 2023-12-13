package com.main.controller;

import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Firm;
import com.main.entity.Lawyer;
import com.main.repo.LawyerRepository;
import com.main.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {


    @Autowired
    private  LawyerService lawyerService;

    @Autowired
    private LawyerRepository lawyerRepository;


    @PostMapping("/addUser/{firmId}")
    ResponseEntity<?> createLawyer(@PathVariable  String firmId,@RequestBody Lawyer lawyer){
          ResponseEntity<?> response = lawyerService.addLawyer(firmId,lawyer);
        return response;
    }
    @PutMapping("/updateLawyer/{firmId}/{lawyerId}")
    ResponseEntity<?> upadateLawyer(@PathVariable  String firmId,@PathVariable  String lawyerId,@RequestBody Lawyer lawyer) {
        ResponseEntity<?> response = lawyerService.updateLawyer(firmId, lawyerId,lawyer);
        return response;
    }

    @GetMapping("/getAllLawyer")
    public ResponseEntity<?> findAllLawyer() {
        List<Lawyer> lawyers = lawyerRepository.findAll();

        for (Lawyer lawyer : lawyers) {
            if (lawyer.getFirms() != null) {
                lawyer.setFirmName(lawyer.getFirms().getFirmName());
            }
        }

        return ResponseEntity.ok(lawyers);
    }


    @GetMapping("/getAllLawyerInFirm/{firmId}")
    ResponseEntity<?> getLawyerById(@PathVariable  String firmId){
        ResponseEntity<?> response = lawyerService.getAllLawyerInFirm(firmId);
        return response;
    }
    @GetMapping("/getLawyerById/{firmId}/{lawyerId}")
    ResponseEntity<?> getLawyerById(@PathVariable  String firmId,@PathVariable  String lawyerId){
        ResponseEntity<?> response = lawyerService.getLawyerById(firmId,lawyerId);
        return response;
    }

    @DeleteMapping("/deleteLawyer/{firmId}/{lawyerId}")
    ResponseEntity<?> deleteById(@PathVariable  String firmId,@PathVariable  String lawyerId){
        ResponseEntity<?> response = lawyerService.deleteLawyerById(firmId,lawyerId);
        return response;
    }


    @GetMapping("/getUnActiveLawyer/{firmId}")
    public List<Lawyer> getUnActiveLawyer(@PathVariable String firmId){
        List<Lawyer>  response = lawyerRepository.findByLawyerStatusAndFirms_FirmId(false,firmId);
        return  response;
    }
    @GetMapping("/getActiveLawyer/{firmId}")
    public List<Lawyer> getActiveLawyer(@PathVariable String firmId){
        List<Lawyer>  response = lawyerRepository.findByLawyerStatusAndFirms_FirmId(true,firmId);
        return  response;
    }



}
