package com.cs610omarfaheem.classes.Simulators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.cs610omarfaheem.classes.MetricCalculator2;
import com.cs610omarfaheem.classes.Passenger;
import com.cs610omarfaheem.classes.ServiceStation;

public class Simulator3 {
    
    public void simulateOption2B(int duration, int arrivalRate, int serviceRate, int NUM_SERVICE_STATIONS) {
    System.out.println("Option 2B: Shortest Queue");

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

            // Find the station with the shortest queue
            ServiceStation shortestQueueStation = findShortestQueue(serviceStations);
            shortestQueueStation.totalTime += newPassenger.serviceTime;
            shortestQueueStation.queue.add(newPassenger);
        }

        for (ServiceStation station : serviceStations) {
            if (!station.queue.isEmpty() && station.currentPassenger == null ) {
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
    final Queue<Passenger> remainingQueue = new LinkedList<>();
    serviceStations.forEach(station -> remainingQueue.addAll(station.queue));

    // Calculate and print metrics
    MetricCalculator2 metricCalculator = new MetricCalculator2();
    metricCalculator.calculateAndPrintMetrics("Option 2B" , serviceStations ,remainingQueue ,serviceRate,duration );
}

// Helper method to find the service station with the shortest queue
private static ServiceStation findShortestQueue(List<ServiceStation> serviceStations) {
    ServiceStation shortestQueueStation = serviceStations.get(0);

    for (ServiceStation station : serviceStations) {
        if (station.queue.size() < shortestQueueStation.queue.size()) {
            shortestQueueStation = station;
        }
    }

    return shortestQueueStation;
}
}
