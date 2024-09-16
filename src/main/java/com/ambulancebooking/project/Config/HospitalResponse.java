package com.ambulancebooking.project.Config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HospitalResponse {

 private String status;

 @JsonProperty("results")
 private List<Hospital> results;

    public String getStatus() {
        return status;
    }

    public List<Hospital> getResults() {
        return results;
    }

    public static class Hospital {
        private String name;
        private String vicinity;
        private double rating;

        public String getName() {
            return name;
        }

        public String getVicinity() {
            return vicinity;
        }

        public double getRating() {
            return rating;
        }
    }
}
