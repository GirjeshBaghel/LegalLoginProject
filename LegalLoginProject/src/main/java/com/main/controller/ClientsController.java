package com.main.controller;


import com.main.entity.Clients;
import com.main.entity.Lawyer;
import com.main.repo.ClientsRepo;
import com.main.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private ClientsRepo clientsRepo;


    @PostMapping("/registerClient/{lawyerId}")
    public ResponseEntity<?> register(@PathVariable  String lawyerId,@RequestBody Clients clients) {
        ResponseEntity<?> registeredUser = clientsService.registerUser(lawyerId,clients);
        return  registeredUser;
    }

    @GetMapping("/getClientById/{lawyerId}/{clientId}")
    ResponseEntity<?> getClientById(@PathVariable  String lawyerId,@PathVariable  String clientId){
        ResponseEntity<?> response = clientsService.getClientById(lawyerId,clientId);
        return response;
    }
    @GetMapping("/allClients")
    public ResponseEntity<?> getAllClients() {
        ResponseEntity<?> response= clientsService.getAllClients();
        return  response;
    }
    @GetMapping("/allLawyerClient/{lawyerId}")
    public ResponseEntity<?> getAllLawyerClients(@PathVariable String lawyerId) {
        ResponseEntity<?> response= clientsService.getAllLawyerClients(lawyerId);
        return  response;
    }

    @PutMapping("/updateClient/{lawyerId}/{clientId}")
    public ResponseEntity<?> updateClients(@PathVariable String lawyerId,@PathVariable String clientId, @RequestBody Clients clients) {
        ResponseEntity<?> response= clientsService.updateClient(lawyerId,clientId,clients);
        return  response;
    }
    @DeleteMapping("/deleteClient/{lawyerId}/{clientId}")
    public ResponseEntity<?> deleteClientWithCases(@PathVariable String lawyerId,@PathVariable String clientId) {
        ResponseEntity<?> response = clientsService.deleteClient(lawyerId,clientId);
        return  response;
    }
    @GetMapping("/getUnActiveClient/{lawyerId}")
    public List<Clients> getUnActiveClient(@PathVariable String lawyerId){
        List<Clients>  response = clientsRepo.findByClientStatusAndLawyers_LawyerId(false,lawyerId);
        return  response;
    }
    @GetMapping("/getActiveClient/{lawyerId}")
    public List<Clients> getActiveClient(@PathVariable String lawyerId){
        List<Clients>  response = clientsRepo.findByClientStatusAndLawyers_LawyerId(true,lawyerId);
        return  response;
    }


}
