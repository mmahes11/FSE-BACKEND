package com.vaccination.portal.controller;

import com.vaccination.portal.entity.VaccinationRecord;
import com.vaccination.portal.entity.Student;
import com.vaccination.portal.entity.VaccinationDrive;
import com.vaccination.portal.repository.VaccinationRecordRepository;
import com.vaccination.portal.repository.StudentRepository;
import com.vaccination.portal.repository.VaccinationDriveRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
public class VaccinationRecordExportController {

    @Autowired
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccinationDriveRepository vaccinationDriveRepository;

    @GetMapping("/vaccination-records/export/excel")
    public void exportVaccinationRecordsToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vaccination_records.xlsx\"");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vaccination Records");
            Row header = sheet.createRow(0);

            // Header cells
            header.createCell(0).setCellValue("Record ID");
            header.createCell(1).setCellValue("Student Name");
            header.createCell(2).setCellValue("Student Class");
            header.createCell(3).setCellValue("Vaccine Name");
            header.createCell(4).setCellValue("Drive Date");
            header.createCell(5).setCellValue("Vaccination Date");

            int rowNum = 1;
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (VaccinationRecord record : vaccinationRecordRepository.findAll()) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(record.getId() != null ? record.getId() : -1);

                // Fetch student info
                Optional<Student> studentOpt = studentRepository.findById(record.getStudentId());
                row.createCell(1).setCellValue(studentOpt.map(Student::getName).orElse("Unknown"));
                row.createCell(2).setCellValue(studentOpt.map(Student::getClassName).orElse("Unknown"));

                // Fetch drive info
                Optional<VaccinationDrive> driveOpt = vaccinationDriveRepository.findById(record.getDriveId());
                row.createCell(3).setCellValue(driveOpt.map(VaccinationDrive::getVaccineName).orElse("Unknown"));
                row.createCell(4).setCellValue(driveOpt.map(d -> d.getDriveDate() != null ? d.getDriveDate().format(df) : "").orElse(""));

                row.createCell(5).setCellValue(
                        record.getVaccinationDate() != null ? record.getVaccinationDate().format(df) : ""
                );
            }

            workbook.write(response.getOutputStream());
        }
    }
}