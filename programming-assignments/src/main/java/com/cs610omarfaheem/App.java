package com.cs610omarfaheem;

import com.cs610omarfaheem.classes.Simulators.Simulator;
// import com.cs610omarfaheem.classes.Simulators.Simulator2;

public class App {
    private static final int NUM_SERVICE_STATIONS = 3;

    public static void main(String[] args) {
        int duration = 100; // Duration of simulation in minutes
        int arrivalRate = 2; // Average arrival rate in minutes
        int serviceRate = 4 ;

        Simulator simulator = new Simulator();
        // Simulator2 simulator2 = new Simulator2();

        simulator.simulateOption1(duration, arrivalRate, serviceRate, NUM_SERVICE_STATIONS);
        // simulator2.simulateOption2A(duration, arrivalRate, serviceRate, NUM_SERVICE_STATIONS);

    }
}
