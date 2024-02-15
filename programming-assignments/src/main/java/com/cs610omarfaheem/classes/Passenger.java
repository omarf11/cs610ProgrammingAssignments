package com.cs610omarfaheem.classes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Passenger {
    public int arrivalTime;
    public int serviceTime;
    public int waitingTime;

    public Passenger(int arrivalTime, int serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
    }
}
