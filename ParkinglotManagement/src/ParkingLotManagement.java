import java.util.*;

/**
 * UseCase3StreetParkingApp
 * Simulates a street parking system using open addressing with linear probing.
 */

class StreetParkingSpot {

    String licensePlate;
    long entryTime;

    public StreetParkingSpot(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis();
    }
}

public class ParkingLotManagement{

    private StreetParkingSpot[] parkingLot;
    private int capacity;

    public ParkingLotManagement(int capacity) {
        this.capacity = capacity;
        parkingLot = new StreetParkingSpot[capacity];
    }

    // hash function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    // park vehicle
    public void parkVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (parkingLot[index] != null) {
            index = (index + 1) % capacity;
            probes++;
        }

        parkingLot[index] = new StreetParkingSpot(licensePlate);

        System.out.println("Vehicle " + licensePlate +
                " parked at street spot #" + index +
                " (" + probes + " probes)");
    }

    // exit vehicle
    public void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);

        while (parkingLot[index] != null) {

            if (parkingLot[index].licensePlate.equals(licensePlate)) {

                long duration =
                        (System.currentTimeMillis() - parkingLot[index].entryTime) / 1000;

                parkingLot[index] = null;

                System.out.println("Vehicle " + licensePlate +
                        " left spot #" + index +
                        " | Duration: " + duration + " seconds");

                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found in street parking.");
    }

    // show parking usage
    public void getStatistics() {

        int occupied = 0;

        for (StreetParkingSpot spot : parkingLot) {
            if (spot != null) {
                occupied++;
            }
        }

        double occupancy = ((double) occupied / capacity) * 100;

        System.out.println("\nStreet Parking Statistics:");
        System.out.println("Total Spots: " + capacity);
        System.out.println("Occupied Spots: " + occupied);
        System.out.println("Occupancy Rate: " + occupancy + "%");
    }

    public static void main(String[] args) {

        ParkingLotManagement parking = new ParkingLotManagement(100);

        parking.parkVehicle("TN01AB1234");
        parking.parkVehicle("TN02CD5678");
        parking.parkVehicle("TN03XY9999");

        parking.exitVehicle("TN01AB1234");

        parking.getStatistics();
    }
}
