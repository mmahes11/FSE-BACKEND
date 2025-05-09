package com.vaccination.portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class VaccinationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private LocalDate driveDate;
    private int availableDoses;
    private String applicableClasses;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(LocalDate driveDate) {
        this.driveDate = driveDate;
    }

    public int getAvailableDoses() {
        return availableDoses;
    }

    public void setAvailableDoses(int availableDoses) {
        this.availableDoses = availableDoses;
    }

    public String getApplicableClasses() {
        return applicableClasses;
    }

    public void setApplicableClasses(String applicableClasses) {
        this.applicableClasses = applicableClasses;
    }

}


