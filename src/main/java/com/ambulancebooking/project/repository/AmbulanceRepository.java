package com.ambulancebooking.project.repository;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<AmbulanceEntity,Long> {

List<AmbulanceEntity>findByAvailableTrue();
}
