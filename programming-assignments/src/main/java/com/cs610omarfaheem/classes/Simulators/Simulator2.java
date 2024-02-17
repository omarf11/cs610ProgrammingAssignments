package com.cs610omarfaheem.classes.Simulators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.cs610omarfaheem.classes.MetricCalculator2;
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
        int stationIndex = 0;

        while (currentTime < duration) {
            // Generate random arrivals
            if (random.nextDouble() < 1.0 / arrivalRate) {
                int serviceTime = (int) (serviceRate * (1 + random.nextDouble()));
                Passenger newPassenger = new Passenger(currentTime, serviceTime);
                roundRobinQueue.add(newPassenger);
            }

            // Distribute passengers in round-robin fashion
            while (!roundRobinQueue.isEmpty()) {
                int tempMaxQlength = serviceStations.get(stationIndex).maxQLength;
                Passenger nextPassenger = roundRobinQueue.poll();
                serviceStations.get(stationIndex).queue.add(nextPassenger);
                // setting max q length after we add a passenger to a stations queue
                serviceStations.get(stationIndex).maxQLength = Math.max(tempMaxQlength,
                        serviceStations.get(stationIndex).queue.size());
                stationIndex++;
                if (stationIndex == NUM_SERVICE_STATIONS) {
                    stationIndex = 0;
                }
            }
            // Process passengers in the each queue
            for (ServiceStation station : serviceStations) {
                if (!station.queue.isEmpty() && station.currentPassenger == null) {
                    Passenger nextPassenger = station.queue.poll();
                    nextPassenger.waitingTime = currentTime - nextPassenger.arrivalTime;

                    station.totalTime += nextPassenger.serviceTime;
                    station.setCurrentPassenger(station.servicePassenger(nextPassenger));
                }
            }

            // Update service station queues
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
        MetricCalculator2 metricCalculator = new MetricCalculator2();
        final Queue<Passenger> remainingQueue = new LinkedList<>();
        serviceStations.forEach(station -> remainingQueue.addAll(station.queue));

        metricCalculator.calculateAndPrintMetrics("Option 2A", serviceStations, remainingQueue, serviceRate, duration);
        System.out.println("Total Duration: " + currentTime);

    }
}
