package com.vaccination.portal.controller;

import com.vaccination.portal.entity.Student;
import com.vaccination.portal.entity.VaccinationDrive;
import com.vaccination.portal.entity.VaccinationRecord;
import com.vaccination.portal.repository.StudentRepository;
import com.vaccination.portal.repository.VaccinationDriveRepository;
import com.vaccination.portal.repository.VaccinationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/vaccination-records")
public class VaccinationRecordController {

    @Autowired
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccinationDriveRepository driveRepository;

    // DTO for cascading record info
    public static class VaccinationRecordDetails {
        public Long id;
        public String name;
        public Long age;
        public String className;
        public String vaccinationStatus;
        public String vaccineName;
        public String driveDate;
        public String applicableClasses;
        public String vaccinationDate;

        public VaccinationRecordDetails(
                Long id, String name, Long age, String className, String vaccinationStatus,
                String vaccineName, String driveDate, String applicableClasses, String vaccinationDate
        ) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.className = className;
            this.vaccinationStatus = vaccinationStatus;
            this.vaccineName = vaccineName;
            this.driveDate = driveDate;
            this.applicableClasses = applicableClasses;
            this.vaccinationDate = vaccinationDate;
        }
    }

    @PostMapping
    public ResponseEntity<?> addVaccinationRecord(@RequestBody VaccinationRecord vaccinationRecord) {
        // Validate IDs
        if (vaccinationRecord.getStudentId() == null || !studentRepository.existsById(vaccinationRecord.getStudentId())) {
            return ResponseEntity.badRequest().body("Invalid or missing studentId");
        }
        if (vaccinationRecord.getDriveId() == null || !driveRepository.existsById(vaccinationRecord.getDriveId())) {
            return ResponseEntity.badRequest().body("Invalid or missing driveId");
        }
        // Validate date
        if (vaccinationRecord.getVaccinationDate() == null) {
            return ResponseEntity.badRequest().body("Vaccination date is required");
        }
        if (vaccinationRecord.getVaccinationDate().isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Vaccination date cannot be in the future");
        }

        VaccinationRecord savedRecord = vaccinationRecordRepository.save(vaccinationRecord);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/details")
    public ResponseEntity<List<VaccinationRecordDetails>> getAllVaccinationRecordDetails() {
        List<VaccinationRecordDetails> result = vaccinationRecordRepository.findAll().stream().map(record -> {
            Optional<Student> studentOpt = studentRepository.findById(record.getStudentId());
            Optional<VaccinationDrive> driveOpt = driveRepository.findById(record.getDriveId());

            Student student = studentOpt.orElse(null);
            VaccinationDrive drive = driveOpt.orElse(null);

            return new VaccinationRecordDetails(
                    record.getId(),
                    student != null ? student.getName() : null,
                    student != null ? student.getAge() : null,
                    student != null ? student.getClassName() : null,
                    student != null ? student.getVaccinationStatus() : null,
                    drive != null ? drive.getVaccineName() : null,
                    drive != null && drive.getDriveDate() != null ? drive.getDriveDate().toString() : null,
                    drive != null ? drive.getApplicableClasses() : null,
                    record.getVaccinationDate() != null ? record.getVaccinationDate().toString() : null
            );

        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

}
