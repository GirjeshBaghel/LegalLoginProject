package com.main.controller;

import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Firm;
import com.main.repo.FirmRepository;
import com.main.service.FirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/firm")
@RestController
public class FirmController {

    @Autowired
    private FirmService firmService;

    @Autowired
    private FirmRepository firmRepository;


    @PostMapping("/addFirm")
    public ResponseEntity<?> addFirm(@RequestBody Firm firm) {
        ResponseEntity<?> response = firmService.addFirmService(firm);
        return response;
    }


    @PutMapping("/updateFirm/{firmId}")
    ResponseEntity<?> updateFirm(@PathVariable  String firmId,@RequestBody Firm firm){
        ResponseEntity<?> response =  firmService.updateFirm(firmId,firm);
        return response;
    }

    @GetMapping("/getAllFirm")
    ResponseEntity<?> getAllFirm(){
        List<Firm> response =  firmRepository.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getById/{firmId}")
    ResponseEntity<?> getById(@PathVariable  String firmId){
      ResponseEntity<?> response = firmService.findFirmById(firmId);
      return  response;
    }


    @GetMapping("/getUnActiveFirm")
    public List<Firm> getUnActiveFirm(){
       List<Firm>  response = firmRepository.findByFirmStatus(false);
       return  response;
    }
    @GetMapping("/getActiveFirm")
    public List<Firm> getActiveFirm(){
        List<Firm>  response = firmRepository.findByFirmStatus(true);
        return  response;
    }



    @DeleteMapping("/deleteById/{firmId}")
    ResponseEntity<?> deleteById(@PathVariable  String firmId){
       ResponseEntity<?> response = firmService.deleteFirmById(firmId);
       return  response;
    }




}
