package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.service.AmbulanceService;
import org.apache.logging.log4j.message.Message;
import org.apache.tomcat.util.json.JSONParser;
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

@Autowired
    private AmbulanceService ambulanceService;

 @PostMapping("/bookAmbulance")
   public ResponseEntity<AmbulanceEntity>bookAmbulanceforUser(@RequestBody AmbulanceEntity ambulanceEntity){
     AmbulanceEntity bookedAmbulance=ambulanceService.bookAmbulance(ambulanceEntity);
     return new ResponseEntity<>(bookedAmbulance,HttpStatus.CREATED);
 }

 @GetMapping("/bookings")
    public List<AmbulanceEntity> getAllBookings(){
     return ambulanceService.seeAllBookings();
 }

 @GetMapping("/bookings/{id}")
    public Optional<AmbulanceEntity> getBookingById(@PathVariable Long id){
     return ambulanceService.seeAllBookingsByID(id);
 }

 @DeleteMapping("/delete/{id}")
 public String DeleteRequestforAmbulance(@PathVariable Long id){
     ambulanceService.deleteById(id);
     return "Your booking is cancelled";
 }

 @PutMapping("/updateBooking/{id}")
    public String updateBookingById(@PathVariable Long id,@RequestBody AmbulanceEntity ambulanceEntity){
    ambulanceService.updateambulanceentry(id, ambulanceEntity);
     return "Your booking is modified";
 }
}
