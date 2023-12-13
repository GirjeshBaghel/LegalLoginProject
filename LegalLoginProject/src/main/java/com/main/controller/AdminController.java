package com.main.controller;

import com.main.entity.Admin;

import com.main.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {


    @Autowired
    private AdminService  adminService;
    @PostMapping("/addAdmin")
    ResponseEntity<?> createAdmin(@RequestBody Admin lawyer){
        ResponseEntity<?> response = adminService.addAdmin(lawyer);
        return response;
    }


}
