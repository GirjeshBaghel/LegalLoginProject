package com.main.service;

import com.main.Exception.BadRequestException;
import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Clients;
import com.main.entity.Lawyer;
import com.main.repo.ClientsRepo;
import com.main.repo.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepo clientsRepo;

    @Autowired
    private LawyerRepository lawyerRepository;

    public ResponseEntity<?> registerUser(String lawyerId, Clients clients) {
        Lawyer lawyer = lawyerRepository.findByLawyerId(lawyerId);
        System.out.println(lawyer);
        if(lawyer == null)
        {
            throw  new BadRequestException("wrong lawyer Id");
        }
        Clients clients1 = clientsRepo.findByClientEmail(clients.getClientEmail());
        if(clients1 != null)
        {
            throw  new BadRequestException("this email is already register");
        }
        clients.setLawyers(lawyer);
        clients.setRegisterDate(LocalDateTime.now());
        clientsRepo.save(clients);
        List<Clients> clients2 = lawyer.getClients();
        clients2.add(clients);
        lawyer.setClients(clients2);
        lawyerRepository.save(lawyer);
        String id = clients.getClientId();
        return ResponseClass.responseId(id,"client added successfully");
    }


    public ResponseEntity<?> getClientById(String lawyerId,String clientId) {

        Lawyer lawyer1 = lawyerRepository.findByLawyerId(lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("lawyer not found from this  id");
        }
        Clients clients = clientsRepo.findByClientIdLawyerId(lawyerId,clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this id");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(clients);

    }

    public ResponseEntity<?> getAllLawyerClients(String lawyerId) {
        Lawyer lawyer1 = lawyerRepository.findByLawyerId(lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("lawyer not found from this  id");
        }
        List<Clients>  clients = clientsRepo.findClientsByLawyersId(lawyerId);
        return  ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    public ResponseEntity<?> getAllClients() {
        List<Clients> clients = clientsRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    public ResponseEntity<?> updateClient(String lawyerId, String clientId, Clients clients) {

        Lawyer lawyer1 = lawyerRepository.findByLawyerId(lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("lawyer not found from this  id");
        }
        Clients clients1 = clientsRepo.findByClientIdLawyerId(lawyerId,clientId);
        if(clients == null)
        {
            throw  new ResourceNotFoundException("client not found from this id");
        }
        clients1.setClientAddress(clients.getClientAddress());
        clients1.setClientEmail(clients.getClientEmail());
        clients1.setClientName(clients.getClientName());
        clients1.setClientPhoneNumber(clients.getClientPhoneNumber());
        clients1.setClientStatus(clients1.isClientStatus());
        clientsRepo.save(clients1);
        return  ResponseClass.response("Client Updated Successfully");
    }

    public ResponseEntity<?> deleteClient(String lawyerId, String clientId) {
        Lawyer lawyer1 = lawyerRepository.findByLawyerId(lawyerId);
        if(lawyer1 == null)
        {
            throw  new ResourceNotFoundException("lawyer not found from this  id");
        }
        Clients clients1 = clientsRepo.findByClientIdLawyerId(lawyerId,clientId);
        if(clients1 == null)
        {
            throw  new ResourceNotFoundException("client not found from this id");
        }
        clientsRepo.delete(clients1);
        return ResponseClass.response("Client deleted Successfully");
    }



}
