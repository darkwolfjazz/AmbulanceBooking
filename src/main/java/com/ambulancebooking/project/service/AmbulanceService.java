package com.ambulancebooking.project.service;

import com.ambulancebooking.project.Config.HospitalResponse;
import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.repository.AmbulanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    Logger logger= LoggerFactory.getLogger(AmbulanceService.class);

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Value("${google.places.api.url}")
    private String googlePlacesApiUrl;

    @Value("${google.places.api.key}")
    private String googlePlacesApiKey;

    public List<String>getNearbyHospitals(String location){
        RestTemplate restTemplate=new RestTemplate();
        String url=UriComponentsBuilder.fromHttpUrl(googlePlacesApiUrl)
                .queryParam("location", location )
                .queryParam("radius" , 5000)
                .queryParam("type","hospital")
                .queryParam("keyword","hospital")
                .queryParam("key",googlePlacesApiKey)
                .toUriString();

        HospitalResponse response=restTemplate.getForObject(url, HospitalResponse.class);
        if(response!=null && "OK".equals(response.getStatus())){
            return response.getResults().stream()
                    .map(hospital -> hospital.getName() + " - " + hospital.getVicinity())
                    .toList();
        }else {
            logger.error("Error fetching hospitals : " + response!=null? response.getStatus() : "No response");
            return List.of("No hospitals found or an error occurred.");
        }
    }

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
    @Cacheable(value = "bookings")
     public List<AmbulanceEntity> seeAllBookings(){
        return ambulanceRepository.findByAvailableTrue();
     }
    @Cacheable(value = "bookings", key = "#id")
     public Optional<AmbulanceEntity> seeAllBookingsByID(Long id){
         logger.info("AmbulanceService - seeAllBookingsById - starts" );
         return ambulanceRepository.findById(id);

     }

    @CacheEvict(value = "bookings", key = "#id")
    public void deleteById(Long id){
         logger.info("AmbulanceService - deleteById - starts" );
         ambulanceRepository.deleteById(id);
     }

    @CachePut(value = "bookings", key = "#id")
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
