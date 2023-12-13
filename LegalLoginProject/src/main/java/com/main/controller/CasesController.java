package com.main.controller;


import com.main.entity.Cases;
import com.main.entity.Clients;
import com.main.entity.Tasks;
import com.main.repo.CasesRepo;
import com.main.service.CasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/cases")
public class CasesController {

    @Autowired
    private CasesService casesService;

    @Autowired
    private CasesRepo casesRepo;


    @PostMapping("/registerCases/{caseId}")
    public ResponseEntity<?> registerCase(@PathVariable  String caseId, @RequestBody Cases cases) {
        ResponseEntity<?> registeredUser = casesService.registerCases(caseId,cases);
        return  registeredUser;
    }

    @GetMapping("/getCaseByCaseNo/{clientId}/{caseNo}")
    public ResponseEntity<?> getCaseByCaseNo(@PathVariable  String clientId, @PathVariable  String caseNo) {
        ResponseEntity<?> registeredUser = casesService.getCaseByNo(clientId,caseNo);
        return  registeredUser;
    }

    @GetMapping("/getAllCaseByClientId/{clientId}")
    public ResponseEntity<?> getAllCaseByClientId(@PathVariable  String clientId) {
        ResponseEntity<?> registeredUser = casesService.getAllCaseByClientId(clientId);
        return  registeredUser;
    }
    @DeleteMapping("/deleteCases/{clientId}/{caseNo}")
    public ResponseEntity<?> deleteClientWithCases(@PathVariable String clientId,@PathVariable String caseNo) {
        ResponseEntity<?> response = casesService.deleteCases(clientId,caseNo);
        return  response;
    }
    @GetMapping("/allCases")
    public ResponseEntity<?> getAllCases() {
        List<Cases> response= casesRepo.findAll();
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/updateCases/{clientId}/{caseNo}")
    public ResponseEntity<?> updateClients(@PathVariable String clientId,@PathVariable String caseNo, @RequestBody Cases cases) {
        ResponseEntity<?> response= casesService.updateCase(clientId,caseNo,cases);
        return  response;
    }

    @PostMapping("/addDocuments/{caseNo}")
    public ResponseEntity<?> addDcuments(@PathVariable String caseNo,@RequestParam(value = "file") MultipartFile[] files) {
        ResponseEntity<?> response= casesService.addDocuments(caseNo,files);
        return  response;
    }

   @GetMapping("/getDocuments/{caseNo}")
    public ResponseEntity<?> getDcuments(@PathVariable String caseNo) {
        ResponseEntity<?> response= casesService.getDocuments(caseNo);
        return  response;
    }

    @GetMapping("/getUnActiveCases/{clientId}")
    public List<Cases> getUnActiveClient(@PathVariable String clientId){
        List<Cases>  response = casesRepo.findByCaseStatusAndClients_ClientId(false,clientId);
        return  response;
    }
    @GetMapping("/getActiveCases/{clientId}")
    public List<Cases> getActiveClient(@PathVariable String clientId){
        List<Cases>  response = casesRepo.findByCaseStatusAndClients_ClientId(true,clientId);
        return  response;
    }

}

