package com.main.Exception;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class ResponseClass {

    public static ResponseEntity<Map<String, Object>> response(String message) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", message);
        successResponse.put("success", true);
        successResponse.put("status", "OK");
        return ResponseEntity.ok(successResponse);
    }

    public static ResponseEntity<Map<String, Object>> responseId(String id,String message) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("id",id);
        successResponse.put("message", message);
        successResponse.put("success", true);
        successResponse.put("status", "OK");
        return ResponseEntity.ok(successResponse);
    }

    public static ResponseEntity<Map<String, Object>> responseLawFir(String firmId,String id,String message) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("firmId",firmId);
        successResponse.put("lawyerId",id);
        successResponse.put("message", message);
        successResponse.put("success", true);
        successResponse.put("status", "OK");
        return ResponseEntity.ok(successResponse);
    }





}
