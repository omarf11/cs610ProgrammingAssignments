package com.cs610omarfaheem;

import com.cs610omarfaheem.classes.Simulators.Simulator;
import com.cs610omarfaheem.classes.Simulators.Simulator2;
import com.cs610omarfaheem.classes.Simulators.Simulator3;
import com.cs610omarfaheem.classes.Simulators.Simulator4;

public class App {
    private static final int NUM_SERVICE_STATIONS = 3;

    public static void main(String[] args) {
        int duration = 2000; // Duration of simulation in minutes
        int arrivalRate = 2; // Average arrival rate in minutes
        int serviceRate = 10;

        Simulator simulator = new Simulator();
        Simulator2 simulator2 = new Simulator2();
        Simulator3 simulator2B = new Simulator3();
        Simulator4 simulator2C = new Simulator4();

        simulator.simulateOption1(duration, arrivalRate, serviceRate, NUM_SERVICE_STATIONS);
        System.out.println();
        simulator2.simulateOption2A(duration, arrivalRate, serviceRate, NUM_SERVICE_STATIONS);
        System.out.println();
        simulator2B.simulateOption2B(duration, arrivalRate, serviceRate, serviceRate);
        System.out.println();
        simulator2C.simulateOption2C(duration, arrivalRate, serviceRate, serviceRate);
    }
}
