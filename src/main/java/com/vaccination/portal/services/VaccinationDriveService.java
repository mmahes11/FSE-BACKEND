package com.vaccination.portal.services;

import com.vaccination.portal.entity.VaccinationDrive;
import com.vaccination.portal.repository.VaccinationDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationDriveService {
    @Autowired
    private VaccinationDriveRepository driveRepo;

    public VaccinationDrive addDrive(VaccinationDrive drive) {
        if (drive.getDriveDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new IllegalArgumentException("Drive must be scheduled at least 15 days in advance.");
        }
        return driveRepo.save(drive);
    }

    public List<VaccinationDrive> getAllDrives() {
        return driveRepo.findAll();
    }

    public VaccinationDrive updateDrive(Long id, VaccinationDrive drive) {
        drive.setId(id);
        return driveRepo.save(drive);
    }

    public void deleteDrive(Long id) {
        driveRepo.deleteById(id);
    }
}

