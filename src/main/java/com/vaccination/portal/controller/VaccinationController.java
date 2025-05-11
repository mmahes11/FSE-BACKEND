package com.vaccination.portal.controller;

import com.vaccination.portal.entity.VaccinationRecord;
import com.vaccination.portal.repository.StudentRepository;
import com.vaccination.portal.repository.VaccinationDriveRepository;
import com.vaccination.portal.repository.VaccinationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class VaccinationController {
        @GetMapping("/home")
        public String home() {
            return "home";
        }
}


