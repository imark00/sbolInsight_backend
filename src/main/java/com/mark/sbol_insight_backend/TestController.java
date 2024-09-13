package com.mark.sbol_insight_backend;


import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {

    @GetMapping(value = "/api/test")
    public ResponseEntity<HashMap<Object, Object>> test() {
        val results = new HashMap<>();

        results.put("status", "Success");
        results.put("app", "SBOL Insight");
        results.put("message", "This is a test message");
        
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

}



