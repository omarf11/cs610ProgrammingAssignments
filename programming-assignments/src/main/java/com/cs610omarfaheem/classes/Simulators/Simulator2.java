package com.cs610omarfaheem.classes.Simulators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.cs610omarfaheem.classes.MetricCalculator;
import com.cs610omarfaheem.classes.Passenger;
import com.cs610omarfaheem.classes.ServiceStation;



public class Simulator2 {
    


    public void simulateOption2A(int duration, int arrivalRate, int serviceRate, int NUM_SERVICE_STATIONS) {
        System.out.println("Option 2A: Round Robin");

        List<ServiceStation> serviceStations = new ArrayList<>();
        for (int i = 0; i < NUM_SERVICE_STATIONS; i++) {
            serviceStations.add(new ServiceStation());
        }

        Queue<Passenger> roundRobinQueue = new LinkedList<>();
        Random random = new Random();
        int currentTime = 0;

        while (currentTime < duration || !roundRobinQueue.isEmpty()) {
            // Generate random arrivals
            if (random.nextDouble() < 1.0 / arrivalRate) {
                int serviceTime = (int) (serviceRate * (1 + random.nextDouble()));
                Passenger newPassenger = new Passenger(currentTime, serviceTime);
                roundRobinQueue.add(newPassenger);
            }

            // Distribute passengers in round-robin fashion
            for (ServiceStation station : serviceStations) {
                if (!roundRobinQueue.isEmpty()) {
                    Passenger nextPassenger = roundRobinQueue.poll();
                    nextPassenger.waitingTime = currentTime - nextPassenger.arrivalTime;
                    station.totalTime += nextPassenger.serviceTime;
                    station.queue.add(nextPassenger);
                }
            }

            // Update service station queues
            for (ServiceStation station : serviceStations) {
                if (!station.queue.isEmpty()) {
                    Passenger currentPassenger = station.queue.peek();
                    currentPassenger.serviceTime--;

                    if (currentPassenger.serviceTime <= 0) {
                        station.queue.poll();
                    }
                }
            }

            currentTime++;
        }

        // Calculate and print metrics
        MetricCalculator metricCalculator = new MetricCalculator();

        metricCalculator.calculateAndPrintMetrics("Option 2A", serviceStations, roundRobinQueue , serviceRate,duration);
    }
}
