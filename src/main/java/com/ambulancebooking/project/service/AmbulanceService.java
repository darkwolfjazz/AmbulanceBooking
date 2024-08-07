package com.ambulancebooking.project.service;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.repository.AmbulanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

  @Autowired
    private AmbulanceRepository ambulanceRepository;

     public AmbulanceEntity bookAmbulance(AmbulanceEntity ambulanceEntity){
        if(ambulanceEntity.isAvailable()){
            ambulanceEntity.setStatus("Confirmed");
        }else{
            ambulanceEntity.setStatus("Pending as their is no availability");
        }
         ambulanceRepository.save(ambulanceEntity);
         return ambulanceEntity;
     }

     public List<AmbulanceEntity> seeAllBookings(){
        return ambulanceRepository.findByAvailableTrue();
     }

     public Optional<AmbulanceEntity> seeAllBookingsByID(Long id){
         return ambulanceRepository.findById(id);

     }

     public void deleteById(Long id){
         ambulanceRepository.deleteById(id);
     }

     public AmbulanceEntity updateambulanceentry(Long id, AmbulanceEntity updatedEntity){
         Optional<AmbulanceEntity> existingEntity= ambulanceRepository.findById(id);
         if(existingEntity.isPresent()){
             AmbulanceEntity currentEntity=existingEntity.get();
             updatedEntity.setAddress(currentEntity.getAddress());
             updatedEntity.setCost(currentEntity.getCost());
             updatedEntity.setAvailable(currentEntity.isAvailable());
             updatedEntity.setPatient_name(currentEntity.getPatient_name());
             updatedEntity.setHospital_name(currentEntity.getHospital_name());
             ambulanceRepository.save(updatedEntity);
         }else {
             throw new RuntimeException();
         }
         return updatedEntity;
     }
}
