package com.vaccination.portal.repository;

import com.vaccination.portal.entity.Student;
import com.vaccination.portal.entity.VaccinationDrive;
import com.vaccination.portal.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}

