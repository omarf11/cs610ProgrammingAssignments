package com.cs610omarfaheem.classes.Simulators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.cs610omarfaheem.classes.MetricCalculator;
import com.cs610omarfaheem.classes.Passenger;
import com.cs610omarfaheem.classes.ServiceStation;



public class Simulator {

    public void simulateOption1(int duration, int arrivalRate, int serviceRate, int NUM_SERVICE_STATIONS) {
        System.out.println("Option 1: Single Queue");

        List<ServiceStation> serviceStations = new ArrayList<>();
        for (int i = 0; i < NUM_SERVICE_STATIONS; i++) {
            serviceStations.add(new ServiceStation());
        }

        Queue<Passenger> singleQueue = new LinkedList<>();
        Random random = new Random();
        int currentTime = 0;
        int maxQLenght = 0;

        while (currentTime <= duration) {
            
            // Generate random arrivals
            if (random.nextDouble() < (1.0 / arrivalRate)) {
                int serviceTime = (int) (serviceRate * (1 + random.nextDouble()));
                Passenger newPassenger = new Passenger(currentTime, serviceTime);

                singleQueue.add(newPassenger);
            }
            maxQLenght = Math.max(maxQLenght , singleQueue.size());


            // Process passengers in the single queue
            for (ServiceStation station : serviceStations) {
                if (!singleQueue.isEmpty() && station.currentPassenger == null ) {
                    Passenger nextPassenger = singleQueue.poll();
                    nextPassenger.waitingTime = currentTime - nextPassenger.arrivalTime;
                    
                    station.totalTime += nextPassenger.serviceTime;
                    station.setCurrentPassenger(station.servicePassenger(nextPassenger));
                }
            }

            // Update service station
            for (ServiceStation station : serviceStations) {
                if (station.currentPassenger != null) {
                    Passenger currentPassenger = station.getCurrentPassenger();
                    currentPassenger.serviceTime--;

                    if (currentPassenger.serviceTime <= 0 && currentPassenger != null) {
                        station.servicedPassengers.add(station.finishPassenger(currentPassenger));
                        station.setCurrentPassenger(null);
                    }
                }
            }
            currentTime++;
        }
        
        // Calculate and print metrics
        MetricCalculator metricCalculator = new MetricCalculator();
        System.out.println();

        metricCalculator.calculateAndPrintMetrics("Option 1", serviceStations, singleQueue, serviceRate ,duration);
        System.out.println("Total Duration: " + currentTime);
        System.out.println("Max Queue length " + maxQLenght);
    }
}
