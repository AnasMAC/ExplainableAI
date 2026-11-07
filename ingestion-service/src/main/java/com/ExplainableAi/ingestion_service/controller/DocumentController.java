package com.ExplainableAi.ingestion_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/api/documents")
public class DocumentController {

    // // Now this method controls its own destiny
    // @PreAuthorize ("hasRole('ADMIN')") 
    // @DeleteMapping ("/{id}")
    // public void deleteDocument(@PathVariable String id) {
    //     // ...
    // }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") 
    @GetMapping ("/{id}")
    public ResponseEntity<String> getDocument(@PathVariable String id) {
        // ...
        return ResponseEntity.ok("it work");
    }
}
