package com.cs610omarfaheem.classes;

import java.util.List;
import java.util.Queue;

public class MetricCalculator {
    

    public void calculateAndPrintMetrics(String option, List<ServiceStation> serviceStations, Queue<Passenger> remainingQueue, int serviceRate, int duration) {
        int maxQueueLength = 0;
        double totalWaitingTime = 0;
        int totalPassengersServed = 0;
    
        for (ServiceStation station : serviceStations) {
            int queueLength = station.queue.size();
            maxQueueLength = Math.max(maxQueueLength, queueLength);
    
            double stationTotalWaitingTime = 0;
            int passengersInService = 0;
    
            for (Passenger passenger : station.queue) {
                stationTotalWaitingTime += passenger.waitingTime;
    
                // Only count passengers that have completed service
                if (passenger.serviceTime <= 0) {
                    passengersInService++;
                }
            }
    
            totalWaitingTime += stationTotalWaitingTime;
            totalPassengersServed += passengersInService;
    
            // Calculate and print average waiting time for each queue
            double averageWaitingTime = passengersInService == 0 ? 0 : stationTotalWaitingTime / passengersInService;
            System.out.println("Station " + serviceStations.indexOf(station) + " - Average Waiting Time: " + averageWaitingTime);
    
            // Calculate and print rate of occupancy for each service station
            double occupancyRate = (double) station.totalTime / (duration * serviceStations.size()) * 100;
            System.out.println("Station " + serviceStations.indexOf(station) + " - Occupancy Rate: " + occupancyRate + "%");
        }
    
        // Include passengers remaining in the queue after simulation ends
        maxQueueLength = Math.max(maxQueueLength, remainingQueue.size());
        totalPassengersServed += remainingQueue.size();
    
        double averageWaitingTime = totalPassengersServed == 0 ? 0 : totalWaitingTime / totalPassengersServed;
    
        System.out.println("Metrics for " + option + ":");
        System.out.println("Max Queue Length: " + maxQueueLength);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        // You can print other metrics like service station occupancy here
        System.out.println();
    }
}
