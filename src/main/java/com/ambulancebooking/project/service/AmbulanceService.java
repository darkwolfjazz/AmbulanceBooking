package com.ambulancebooking.project.service;

import com.ambulancebooking.project.controller.AmbulanceController;
import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.repository.AmbulanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {


    Logger logger= LoggerFactory.getLogger(AmbulanceService.class);

  @Autowired
    private AmbulanceRepository ambulanceRepository;

     public AmbulanceEntity bookAmbulance(AmbulanceEntity ambulanceEntity){

         logger.info("AmbulanceService - bookAmbulance - starts" );

        if(ambulanceEntity.isAvailable()){
            ambulanceEntity.setStatus("Confirmed");
        }else{
            ambulanceEntity.setStatus("Pending as their is no availability");
        }
         ambulanceRepository.save(ambulanceEntity);
         logger.info("AmbulanceService - bookAmbulance - ends" );
        return ambulanceEntity;
     }

     public List<AmbulanceEntity> seeAllBookings(){
         logger.info("AmbulanceService - seeAllBookings " );
        return ambulanceRepository.findByAvailableTrue();
     }

     public Optional<AmbulanceEntity> seeAllBookingsByID(Long id){
         logger.info("AmbulanceService - seeAllBookingsById - starts" );
         return ambulanceRepository.findById(id);

     }
     public void deleteById(Long id){
         logger.info("AmbulanceService - deleteById - starts" );
         ambulanceRepository.deleteById(id);
     }
     public AmbulanceEntity updateambulanceentry(Long id, AmbulanceEntity updatedEntity){
         logger.info("AmbulanceService - updateambulanceentry - starts" );
         Optional<AmbulanceEntity> existingEntity= ambulanceRepository.findById(id);
         if(existingEntity.isPresent()){
             AmbulanceEntity currentEntity=existingEntity.get();
             currentEntity.setPatient_name(updatedEntity.getPatient_name());
             currentEntity.setCost(updatedEntity.getCost());
             currentEntity.setAvailable(updatedEntity.isAvailable());
             currentEntity.setHospital_name(updatedEntity.getHospital_name());
             currentEntity.setAddress(updatedEntity.getAddress());
             if(currentEntity.isAvailable()){
                 currentEntity.setStatus("Confirmed");
             }else{
                 currentEntity.setStatus("Pending");
             }
             ambulanceRepository.save(currentEntity);
         }else {
             throw new RuntimeException("Booking with this id not Found!");
         }
         logger.info("AmbulanceService - updateambulanceentry - ends" );
         return updatedEntity;
     }
}
