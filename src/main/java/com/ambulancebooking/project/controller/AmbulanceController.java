package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.service.AmbulanceService;
import org.apache.logging.log4j.message.Message;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AmbulanceController {


    Logger logger= LoggerFactory.getLogger(AmbulanceController.class);



@Autowired
    private AmbulanceService ambulanceService;

 @PostMapping("/bookAmbulance")
   public ResponseEntity<AmbulanceEntity>bookAmbulanceforUser(@RequestBody AmbulanceEntity ambulanceEntity){
     logger.info("Inside a post request");
     AmbulanceEntity bookedAmbulance=ambulanceService.bookAmbulance(ambulanceEntity);
     return new ResponseEntity<>(bookedAmbulance,HttpStatus.CREATED);
 }

 @GetMapping("/bookings")
    public List<AmbulanceEntity> getAllBookings(){
     logger.info("Inside a get request, returns all bookings");
     return ambulanceService.seeAllBookings();
 }

 @GetMapping("/bookings/{id}")
    public Optional<AmbulanceEntity> getBookingById(@PathVariable Long id){
     logger.info("Returning bookings by id");
     return ambulanceService.seeAllBookingsByID(id);
 }

 @DeleteMapping("/delete/{id}")
 public String DeleteRequestforAmbulance(@PathVariable Long id){
     logger.info("deletes booking by id");
     ambulanceService.deleteById(id);
     return "Your booking is cancelled";
 }

 @PutMapping("/updateBooking/{id}")
    public String updateBookingById(@PathVariable Long id,@RequestBody AmbulanceEntity ambulanceEntity){
     logger.info("Inside a put request");
    ambulanceService.updateambulanceentry(id, ambulanceEntity);
     return "Your booking is modified";
 }
}
