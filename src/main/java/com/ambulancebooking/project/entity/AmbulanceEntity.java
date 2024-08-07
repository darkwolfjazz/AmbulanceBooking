package com.ambulancebooking.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Ambulancetable")
@AllArgsConstructor
@NoArgsConstructor
public class AmbulanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;
    private String patient_name;
    private String address;
    private String hospital_name;
    private int cost;
    @Column(name = "available", columnDefinition = "TINYINT(1)")
    private boolean available;
    private String status;

    public Long getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Long booking_id) {
        this.booking_id = booking_id;
    }

    @Override
    public String toString() {
        return "AmbulanceEntity{" +
                "booking_id=" + booking_id +
                ", patient_name='" + patient_name + '\'' +
                ", address='" + address + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", cost=" + cost +
                ", available=" + available +
                ", status='" + status + '\'' +
                '}';
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
