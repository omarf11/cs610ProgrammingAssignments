package com.cs610omarfaheem.classes;

import java.util.List;
import java.util.Queue;

public class MetricCalculator2 {
    

    public void calculateAndPrintMetrics(String option, List<ServiceStation> serviceStations, Queue<Passenger> remainingQueue, int serviceRate, int duration) {
        double totalWaitingTime = 0;
        int totalPassengersServed = 0;
        int maxPassergengerWaitTime = 0;

    
        for (ServiceStation station : serviceStations) {
            double stationTotalWaitingTime = 0;
            int passengersInService = 0;
    
            for (Passenger passenger : station.servicedPassengers) {
                stationTotalWaitingTime += passenger.waitingTime;

                maxPassergengerWaitTime = Math.max(maxPassergengerWaitTime, passenger.waitingTime);
                // Only count passengers that have completed service
                if (passenger.hasBeenServiced) {
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
            System.out.println("Station " + serviceStations.indexOf(station) + " - Max Queue length: " + station.maxQLength);

        }
    
        totalPassengersServed += remainingQueue.size();
    
        double averageWaitingTime = totalPassengersServed == 0 ? 0 : totalWaitingTime / totalPassengersServed;
    
        System.out.println("Metrics for " + option + ":");
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Max Waiting Time: " + maxPassergengerWaitTime);
        System.out.println("Total Passengers served: " + totalPassengersServed);
        System.out.println("Remaining Queue Size: " + remainingQueue.size());

    }
}
