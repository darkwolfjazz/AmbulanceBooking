package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.service.AmbulanceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AmbulanceController {


    Logger logger= LoggerFactory.getLogger(AmbulanceController.class);



@Autowired
    private AmbulanceService ambulanceService;

 @PostMapping("/bookAmbulance")
   public ResponseEntity<AmbulanceEntity>bookAmbulanceforUser(@Valid @RequestBody AmbulanceEntity ambulanceEntity){
     logger.info("Inside a post request");
     AmbulanceEntity bookedAmbulance=ambulanceService.bookAmbulance(ambulanceEntity);
     return new ResponseEntity<>(bookedAmbulance,HttpStatus.CREATED);
 }

 @GetMapping("/bookings")
    public List<AmbulanceEntity> getAllBookings(){
     return ambulanceService.seeAllBookings();
 }

 @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id){
     logger.info("Returning bookings by id");
     Optional<AmbulanceEntity>booking=ambulanceService.seeAllBookingsByID(id);
     if(booking.isPresent()){
         return ResponseEntity.ok(booking.get());
     }else{
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with " + id);
                  // Custom message for not found
     }
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<?> DeleteRequestforAmbulance(@PathVariable Long id) {
     logger.info("deletes booking by id");
     Optional<AmbulanceEntity> deletebooking = ambulanceService.seeAllBookingsByID(id);
     if (deletebooking.isPresent()) {
         ambulanceService.deleteById(id);
         return ResponseEntity.ok("Your booking is cancelled");
     } else {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot delete booking with id" + id);
     }
 }

 @PutMapping("/updateBooking/{id}")
    public ResponseEntity<?> updateBookingById(@PathVariable Long id,@Valid @RequestBody AmbulanceEntity ambulanceEntity){
     logger.info("Inside a put request");
    Optional<AmbulanceEntity>updateBooking=ambulanceService.seeAllBookingsByID(id);
    if(updateBooking.isPresent()) {
        ambulanceService.updateambulanceentry(id, ambulanceEntity);
        return ResponseEntity.ok("Your booking is modified");
    }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking with " + id + "Cannot be modified");
    }
 }

    @GetMapping("/nearby")
    public ResponseEntity<List<String>> getNearbyHospitals(@RequestParam String location) {
        List<String> hospitals = ambulanceService.getNearbyHospitals(location);
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

}
