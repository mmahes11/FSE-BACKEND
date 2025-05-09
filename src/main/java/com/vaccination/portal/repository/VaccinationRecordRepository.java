package com.vaccination.portal.repository;

import com.vaccination.portal.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {}
