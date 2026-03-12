import java.util.*;

/**
 * UseCase1AirportParkingSystem
 * Parking lot management using Open Addressing (Linear Probing).
 */

class ParkingSpot {

    String licensePlate;
    long entryTime;

    public ParkingSpot(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis();
    }
}

public class ParkingLotManagement {

    private ParkingSpot[] parkingLot;
    private int capacity;

    public ParkingLotManagement(int capacity) {
        this.capacity = capacity;
        parkingLot = new ParkingSpot[capacity];
    }

    // hash function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    // park vehicle using linear probing
    public void parkVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (parkingLot[index] != null) {
            index = (index + 1) % capacity;
            probes++;
        }

        parkingLot[index] = new ParkingSpot(licensePlate);

        System.out.println("Vehicle " + licensePlate +
                " parked at spot #" + index +
                " (" + probes + " probes)");
    }

    // vehicle exit
    public void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);

        while (parkingLot[index] != null) {

            if (parkingLot[index].licensePlate.equals(licensePlate)) {

                long duration =
                        (System.currentTimeMillis() - parkingLot[index].entryTime) / 1000;

                parkingLot[index] = null;

                System.out.println("Vehicle " + licensePlate +
                        " exited from spot #" + index +
                        " | Duration: " + duration + " seconds");

                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found.");
    }

    // statistics
    public void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : parkingLot) {
            if (spot != null) {
                occupied++;
            }
        }

        double occupancy = ((double) occupied / capacity) * 100;

        System.out.println("\nParking Statistics:");
        System.out.println("Total Spots: " + capacity);
        System.out.println("Occupied Spots: " + occupied);
        System.out.println("Occupancy Rate: " + occupancy + "%");
    }

    public static void main(String[] args) {

        ParkingLotManagement parking = new ParkingLotManagement(500);

        parking.parkVehicle("ABC-1234");
        parking.parkVehicle("ABC-1235");
        parking.parkVehicle("XYZ-9999");

        parking.exitVehicle("ABC-1234");

        parking.getStatistics();
    }
}
