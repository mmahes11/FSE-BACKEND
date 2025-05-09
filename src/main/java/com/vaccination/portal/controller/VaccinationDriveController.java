package com.vaccination.portal.controller;

import com.vaccination.portal.entity.VaccinationDrive;
import com.vaccination.portal.services.VaccinationDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drives")
public class VaccinationDriveController {
    @Autowired
    private VaccinationDriveService driveService;

    @PostMapping
    public VaccinationDrive addDrive(@RequestBody VaccinationDrive drive) {
        return driveService.addDrive(drive);
    }

    @GetMapping
    public List<VaccinationDrive> getAllDrives() {
        return driveService.getAllDrives();
    }

    @PutMapping("/{id}")
    public VaccinationDrive updateDrive(@PathVariable Long id, @RequestBody VaccinationDrive drive) {
        return driveService.updateDrive(id, drive);
    }

    @DeleteMapping("/{id}")
    public void deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
    }
}
