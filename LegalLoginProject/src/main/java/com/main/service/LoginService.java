package com.main.service;
import com.main.DTO.LoginDTO;
import com.main.Exception.BadRequestException;
import com.main.Exception.ResponseClass;
import com.main.entity.Admin;
import com.main.entity.Firm;
import com.main.entity.Lawyer;
import com.main.repo.AdminRepository;
import com.main.repo.FirmRepository;
import com.main.repo.LawyerRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class LoginService {

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    @Value("${recaptcha.secretKey}")
    private String recaptchaSecretKey;

    public ResponseEntity<?> loginUser(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
//        String recaptchaResponse = loginDTO.getRecaptchaResponse();
//
//        if (!validateRecaptcha(recaptchaResponse)) {
//            throw new BadRequestException("Invalid reCAPTCHA");
//        }

        Admin admin = adminRepository.findByAdminEmail(email);
        if (admin != null) {
            String hashedPassword = admin.getAdminPassword();
            if (passwordEncoder.matches(password, hashedPassword)) {

                return  ResponseClass.responseId(admin.getAdminId(), "Super Admin Login Successfully");
            }
        }

        Firm firm = firmRepository.findByManagerEmail(email);
        if (firm != null) {
            String hashedPassword = firm.getManagerPassword();
            if (passwordEncoder.matches(password, hashedPassword)) {

                return  ResponseClass.responseId(firm.getFirmId(), "Firm Manager Login Successfully");

            }
        }

        Lawyer lawyer = lawyerRepository.findByLawyerEmail(email);
        if (lawyer != null) {
            String hashedPassword = lawyer.getLawyerPassword();
            if (passwordEncoder.matches(password, hashedPassword)) {
                return  ResponseClass.responseId(lawyer.getFirms().getFirmId(), "Lawyer Login Successfully");
            }
        }
        throw  new BadRequestException("invalid credentials");
    }

//    private boolean validateRecaptcha(String recaptchaResponse) {
//        try {
//            // Send a request to the Google reCAPTCHA API to verify the response
//            String url = "https://www.google.com/recaptcha/api/siteverify";
//            RestTemplate restTemplate = new RestTemplate();
//
//            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//            parameters.add("secret", recaptchaSecretKey);
//            parameters.add("response", recaptchaResponse);
//            System.out.println(recaptchaResponse);
//            ResponseEntity<Map> response = restTemplate.postForEntity(url, parameters, Map.class);
//            Map<String, Object> responseBody = response.getBody();
//            if (responseBody != null && (boolean) responseBody.get("success")) {
//                return true;
//            }
//        } catch (RestClientException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    public ResponseEntity<?> requestPasswordReset(@RequestParam("email") String userEmail) {
        String resetCode = generateResetCode();
        Firm firmUser = firmRepository.findByManagerEmail(userEmail);
        Lawyer lawyerUser = lawyerRepository.findByLawyerEmail(userEmail);
        Admin adminUser = adminRepository.findByAdminEmail(userEmail);

        if (firmUser == null && lawyerUser == null && adminUser == null) {
            throw new BadRequestException("Wrong email");
        }

        if (firmUser != null) {
            firmUser.setRestCode(resetCode);
            firmRepository.save(firmUser);
        }

        if (lawyerUser != null) {
            lawyerUser.setRestCode(resetCode);
            lawyerRepository.save(lawyerUser);
        }

        if (adminUser != null) {
            adminUser.setResetCode(resetCode);
            adminRepository.save(adminUser);
        }

        boolean isEmailSent = emailService.sendResetCodeEmail(userEmail, resetCode);

        if (isEmailSent) {
            System.out.println("Email sent successfully");
            return ResponseClass.response("Password reset code sent to your email.");
        } else {
            System.out.println("Email not sent");
            return ResponseClass.response("Failed to send the reset code.");
        }
    }


    public String generateResetCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public ResponseEntity<?> verifyPasswordReset(String email, String resetCode) {
        Admin admin = adminRepository.findByAdminEmail(email);
        if (admin != null) {
            if (!resetCode.equals(admin.getResetCode())) {
                throw new BadRequestException("Invalid reset code for Admin");
            }
            return ResponseClass.response("Code Verified Successfully for Admin");
        }

        Firm user = firmRepository.findByManagerEmail(email);
        if (user != null) {
            if (!resetCode.equals(user.getRestCode())) {
                throw new BadRequestException("Invalid reset code for Firm");
            }
            return ResponseClass.response("Code Verified Successfully for Firm");
        }
        Lawyer lawyer = lawyerRepository.findByLawyerEmail(email);
        if (lawyer != null) {
            if (!resetCode.equals(lawyer.getRestCode())) {
                throw new BadRequestException("Invalid reset code for Lawyer");
            }
            return ResponseClass.response("Code Verified Successfully for Lawyer");
        }

        throw new BadRequestException("Invalid email id");
    }


    public ResponseEntity<?> updatePasswordReset(String email, String newPassword, String confirm) {
        Admin admin = adminRepository.findByAdminEmail(email);
        if (admin != null) {
            if (newPassword.equals(confirm)) {
                admin.setAdminPassword(passwordEncoder.encode(confirm));
                adminRepository.save(admin);
                return ResponseClass.response("Password updated Successfully");
            } else {
                throw new BadRequestException("Confirm Password does not match");
            }
        }

        Firm user = firmRepository.findByManagerEmail(email);
        if (user != null) {
            if (newPassword.equals(confirm)) {
                user.setManagerPassword(passwordEncoder.encode(confirm));
                user.setRestCode(null);
                firmRepository.save(user);
                return ResponseClass.response("Password Updated Successfully");
            } else {
                throw new BadRequestException("Confirm Password does not match");
            }
        }

        Lawyer lawyer = lawyerRepository.findByLawyerEmail(email);
        if (lawyer != null) {
            if (newPassword.equals(confirm)) {
                lawyer.setLawyerPassword(passwordEncoder.encode(confirm));
                lawyer.setRestCode(null);
                lawyerRepository.save(lawyer);
                return ResponseClass.response("Password updated Successfully");
            } else {
                throw new BadRequestException("Confirm Password does not match");
            }
        }

        throw new BadRequestException("Wrong email id");
    }

    public ResponseEntity<?> updateCurrentPassword(String id, String newPassword) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(newPassword, admin.getAdminPassword())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                admin.setAdminPassword(encodedPassword);
                adminRepository.save(admin);
                return ResponseClass.response("Password updated successfully");
            } else {
                return ResponseClass.response("Current password does not match");
            }
        }

        Optional<Firm> firmOptional = firmRepository.findById(id);
        if (firmOptional.isPresent()) {
            Firm firm = firmOptional.get();
            if (passwordEncoder.matches(newPassword, firm.getManagerPassword())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                firm.setManagerPassword(encodedPassword);
                firmRepository.save(firm);
                return ResponseClass.response("Password updated successfully");
            } else {
                return ResponseClass.response("Current password does not match");
            }
        }
        Optional<Lawyer> lawyerOptional = lawyerRepository.findById(id);
        if (lawyerOptional.isPresent()) {
            Lawyer lawyer = lawyerOptional.get();
            if (passwordEncoder.matches(newPassword, lawyer.getLawyerPassword())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                lawyer.setLawyerPassword(encodedPassword);
                lawyerRepository.save(lawyer);
                return ResponseClass.response("Password updated successfully");
            } else {
                return ResponseClass.response("Current password does not match");
            }
        }
        return ResponseClass.response("Invalid ID");
    }
}

