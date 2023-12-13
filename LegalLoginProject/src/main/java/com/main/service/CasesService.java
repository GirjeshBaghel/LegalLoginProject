package com.main.service;


import com.main.Exception.BadRequestException;
import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Cases;
import com.main.entity.Clients;
import com.main.entity.Documents;
import com.main.repo.CasesRepo;
import com.main.repo.ClientsRepo;

import com.main.repo.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CasesService {

    @Autowired
    private CasesRepo casesRepo;

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private DocumentRepo documentRepo;
    public ResponseEntity<?> registerCases(String clientId, Cases cases) {
        Clients clients = clientsRepo.findByClientId(clientId);
        if(clients == null)
        {
            throw  new BadRequestException("wrong client Id");
        }
        cases.setClients(clients);
        cases.setRegisterDate(LocalDateTime.now());
        casesRepo.save(cases);
        List<Cases> cases1 = clients.getCases();
        cases1.add(cases);
        clients.setCases(cases1);
        clientsRepo.save(clients);
        String id = cases.getCaseNumber();
        return ResponseClass.responseId(id,"Case Register Successfully");
    }

    public ResponseEntity<?> getCaseByNo(String clientId, String caseNo) {
        Clients clients = clientsRepo.findByClientId(clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this Id");
        }
        Cases cases = casesRepo.findByCaseNumberClientId(clientId,caseNo);
        if(cases == null)
        {
            throw  new ResourceNotFoundException("case is not found from this case number");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(cases);

    }

    public ResponseEntity<?> getAllCaseByClientId(String clientId) {
        Clients clients = clientsRepo.findByClientId(clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this Id");
        }
       List<Cases> cases = casesRepo.findAllCasesByClientId(clientId);
        return  ResponseEntity.status(HttpStatus.OK).body(cases);
    }

    public ResponseEntity<?> deleteCases(String clientId, String caseNo) {
        Clients clients = clientsRepo.findByClientId(clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this Id");
        }
        Cases cases1 = casesRepo.findByCaseNumberClientId(clientId,caseNo);
        if(cases1 == null)
        {
            throw  new ResourceNotFoundException("case is not found from this case number");
        }
        casesRepo.delete(cases1);
        return  ResponseClass.response("Case Deleted Successfully");
    }

    public ResponseEntity<?> updateCase(String clientId, String caseNo, Cases cases) {
        Clients clients = clientsRepo.findByClientId(clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this Id");
        }
        Cases cases1 = casesRepo.findByCaseNumberClientId(clientId,caseNo);
        if(cases1 == null)
        {
            throw  new ResourceNotFoundException("case is not found from this case number");
        }
        cases1.setCaseDescriptions(cases.getCaseDescriptions());
        cases1.setCaseName(cases.getCaseName());
        cases1.setCaseStatus(cases.getClients().isClientStatus());
        cases1.setAddNotes(cases.getAddNotes());
        casesRepo.save(cases1);
        return  ResponseClass.response("Case Updated Successfully");

    }

    public ResponseEntity<?> addDocuments(String caseNo, MultipartFile[] files) {
        Cases cases1 = casesRepo.findByCaseNumber(caseNo);

        if (cases1 == null) {
            throw new ResourceNotFoundException("Case is not found for this case number");
        }

        if (files == null || files.length == 0) {
            return ResponseClass.response("Please upload at least one document");
        }

        List<String> fileUrls = new ArrayList<>();
        String folderPath = "/Users/girjeshbaghel/Documents/Project/LegalLoginProject/DocumentsImage/";

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = folderPath + fileName;

            try {
                file.transferTo(new File(filePath));
                fileUrls.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseClass.response("Failed to upload one or more documents");
            }
        }
        for (String fileUrl : fileUrls) {
            Documents document = new Documents();
            document.setFileName(fileUrl);
            document.setFileType("application/pdf");
            document.setFileUrl(new String[]{fileUrl});
            document.setUploadTime(LocalDateTime.now());
            document.setCases(cases1);

            documentRepo.save(document);
        }

        return ResponseClass.response("Documents uploaded successfully");
    }

    public ResponseEntity<?> getDocuments(String caseNo) {
        Cases cases1 = casesRepo.findByCaseNumber(caseNo);

        if (cases1 == null) {
            throw new ResourceNotFoundException("Case is not found for this case number");
        }
        List<Documents>  documents = documentRepo.findAllByCases_CaseNumber(caseNo);
        return ResponseEntity.status(HttpStatus.OK).body(documents);
    }



}

