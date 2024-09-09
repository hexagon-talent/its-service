package com.its.service.domain.classification.repository;

import com.its.service.domain.classification.entity.Minor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinorRepository extends JpaRepository<Minor, Long> {
    List<Minor> findByMajor_MajorId(Long majorId);
}
