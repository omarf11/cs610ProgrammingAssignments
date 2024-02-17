package com.cs610omarfaheem.classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.Data;

@Data
public class ServiceStation {
    public int totalTime = 0; // Total time the station has been busy
    public Passenger currentPassenger;
    public List<Passenger> servicedPassengers = new ArrayList<>();
    public Queue<Passenger> queue = new LinkedList<>();
    public int maxQLength = 0;

    public Passenger servicePassenger(Passenger passenger){
        passenger.isServicing = true;
        return passenger;
    }
    public Passenger finishPassenger(Passenger passenger){
        passenger.isServicing = false;
        passenger.hasBeenServiced = true;
        return passenger;
    }
}
