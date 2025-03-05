package com.smartcarebackend.controller;

import com.smartcarebackend.model.Giver;
import com.smartcarebackend.repositories.GiverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/giver")
public class GiverController {

    @Autowired
    private GiverRepository giverRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Giver>> getAllGivers(){
        List<Giver> givers = giverRepository.findAll();
        return ResponseEntity.ok(givers);
    }
}
