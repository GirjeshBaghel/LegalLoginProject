package com.main.controller;


import com.main.DTO.LoginDTO;
import com.main.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {




    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    ResponseEntity<?> loginPage(@RequestBody LoginDTO loginDTO){
        ResponseEntity<?> response = loginService.loginUser(loginDTO);
        return  response;

    }

    @GetMapping("/")
    public String createUser(){
        return "Login Project is Running";
    }
    @PostMapping("/resetPassword/request")
    ResponseEntity<?> forgetPage(@RequestParam("email") String  email){
        ResponseEntity<?> response = loginService.requestPasswordReset(email);
        return  response;
    }

    @PostMapping("/resetPassword/verify")
    ResponseEntity<?> verifyPage(@RequestParam("email") String  email,@RequestParam("code") String resetCode){
        ResponseEntity<?> response = loginService.verifyPasswordReset(email,resetCode);
        return  response;
    }

    @PostMapping("/resetPassword/update")
    ResponseEntity<?> updatePage(@RequestParam("email") String  email,@RequestParam("newPassword") String newPassword , @RequestParam("confirmPassword") String confirm){
        ResponseEntity<?> response = loginService.updatePasswordReset(email,newPassword,confirm);
        return  response;
    }

    @PostMapping("/updatePassword/{id}/")
    ResponseEntity<?> updateCurrentPassword(@PathVariable String id, @RequestParam String password ){
        ResponseEntity<?> response = loginService.updateCurrentPassword(id,password);
        return  response;
    }






}
