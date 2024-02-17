package com.cs610omarfaheem.classes.Simulators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.cs610omarfaheem.classes.MetricCalculator2;
import com.cs610omarfaheem.classes.Passenger;
import com.cs610omarfaheem.classes.ServiceStation;

public class Simulator4 {

    public void simulateOption2C(int duration, int arrivalRate, int serviceRate, int NUM_SERVICE_STATIONS) {
        System.out.println("Option 2C: Random Queue");

        List<ServiceStation> serviceStations = new ArrayList<>();
        for (int i = 0; i < NUM_SERVICE_STATIONS; i++) {
            serviceStations.add(new ServiceStation());
        }

        Random random = new Random();
        int currentTime = 0;

        while (currentTime < duration) {
            // Generate random arrivals
            if (random.nextDouble() < 1.0 / arrivalRate) {
                int serviceTime = (int) (serviceRate * (1 + random.nextDouble()));
                Passenger newPassenger = new Passenger(currentTime, serviceTime);

                // Choose a random service station
                int randomStationIndex = random.nextInt(NUM_SERVICE_STATIONS);
                ServiceStation randomStation = serviceStations.get(randomStationIndex);
                int tempMaxQlength = serviceStations.get(randomStationIndex).maxQLength;
                randomStation.totalTime += newPassenger.serviceTime;
                randomStation.queue.add(newPassenger);
                randomStation.maxQLength = Math.max(tempMaxQlength, randomStation.queue.size());
            }

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
        final Queue<Passenger> remainingQueue = new LinkedList<>();
        serviceStations.forEach(station -> remainingQueue.addAll(station.queue));

        MetricCalculator2 metricCalculator = new MetricCalculator2();
        metricCalculator.calculateAndPrintMetrics("Option 2B", serviceStations, remainingQueue, serviceRate, duration);
    }
}
