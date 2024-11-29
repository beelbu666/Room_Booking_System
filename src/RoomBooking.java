import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerDay;
    private boolean availability;

    public Room(int roomNumber, String roomType, double pricePerDay, boolean availability) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        String availabilityStatus = isAvailable() ? "Available" : "Booked";
        return String.format("| %-12d | %-15s | %-17.2f | %-14s |", roomNumber, roomType, pricePerDay, availabilityStatus);
    }
}

class Customer {
    private Room room;
    private String customerName;
    private int nights;
    private LocalDate checkInDate;

    public Customer(Room room, String customerName, int nights) {
        this.room = room;
        this.customerName = customerName;
        this.nights = nights;
        this.checkInDate = LocalDate.now();
        room.setAvailable(false);
    }

    public String getCustomerName() {
        return customerName;
    }

    public double calculateTotalCost() {
        return room.getPricePerDay() * nights;
    }

    public double calculateVAT() {
        return calculateTotalCost() * 0.13;
    }

    public double calculateTourismFund() {
        return calculateTotalCost() * 0.05;
    }

    public void generateBill() {
        double roomCharges = calculateTotalCost();
        double vat = calculateVAT();
        double tourismFund = calculateTourismFund();
        double totalPayment = roomCharges + vat;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String checkInDateStr = checkInDate.format(formatter);
        String checkOutDateStr = checkInDate.plusDays(nights).format(formatter);

        System.out.println("-----------------------------------------------------");
        System.out.println("|                         BILL                      |");
        System.out.println("-----------------------------------------------------");
        System.out.printf("| %-17s : %-30s|\n", "Customer Name", customerName);
        System.out.printf("| %-17s : %-30d|\n", "Room Number", room.getRoomNumber());
        System.out.printf("| %-17s : %-30s|\n", "Room Type", room.getRoomType());
        System.out.printf("| %-17s : %-30s|\n", "Check-in Date", checkInDateStr);
        System.out.printf("| %-17s : %-30s|\n", "Check-out Date", checkOutDateStr);
        System.out.println("-----------------------------------------------------");
        System.out.printf("| %-17s : %-30.2f|\n", "Room Charges", roomCharges);
        System.out.printf("| %-17s : %-30.2f|\n", "VAT (13%)", vat);
        System.out.printf("| %-17s : %-30.2f|\n", "Tourism Fund (5%)", tourismFund);
        System.out.println("-----------------------------------------------------");
        System.out.printf("| %-17s : %-30.2f|\n", "Total Payment Due", totalPayment);
        System.out.println("-----------------------------------------------------");
    }
}

class Booking {
    private List<Room> rooms;
    private List<Customer> customers;

    public Booking() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Double bed", 1000, true));
        rooms.add(new Room(102, "Triple bed", 1500, true));
        rooms.add(new Room(103, "Double bed", 1200, true));
        rooms.add(new Room(104, "Triple bed", 1500, true));
        rooms.add(new Room(105, "Double bed", 1200, true));
        rooms.add(new Room(106, "Triple bed", 1700, true));
        rooms.add(new Room(107, "Double bed", 1100, true));
        rooms.add(new Room(108, "Double bed", 1300, true));
        rooms.add(new Room(109, "Triple bed", 1600, true));
        rooms.add(new Room(110, "Double bed", 1200, true));
    }

    public void displayAllRooms() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("| Room Number |    Room Type     |   Price per day   |   Availability |");
        System.out.println("-----------------------------------------------------------------------");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    public void displayAvailableRooms() {
        System.out.println("------------------------------------------------------");
        System.out.println("|              Available Rooms                       |");
        System.out.println("------------------------------------------------------");
        System.out.println("| Room Number |    Room Type     |   Price per day   |");
        System.out.println("------------------------------------------------------");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.printf("| %-12d | %-15s | %-17.2f |\n", room.getRoomNumber(), room.getRoomType(), room.getPricePerDay());
            }
        }
        System.out.println("------------------------------------------------------");
    }

    public Customer bookRoom(int roomNumber, String customerName, int nights) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                Customer customer = new Customer(room, customerName, nights);
                customers.add(customer);
                return customer;
            }
        }
        return null;
    }

    public boolean hasAvailableRooms() {
        for (Room room : rooms) {
            if (room.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public void displayAllBookings() {
        System.out.println("\nAll Bookings:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getCustomerName());
        }
    }

    public void displayBill(int index) {
        if (index >= 0 && index < customers.size()) {
            customers.get(index).generateBill();
        } else {
            System.out.println("Invalid booking index.");
        }
    }

    public int getBookingsCount() {
        return customers.size();
    }
}

public class RoomBooking {
    public static void main(String[] args) {
        Booking booking = new Booking();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---------------------------------------------------------------------");
            System.out.println("|                         Main Menu                                 |");
            System.out.println("---------------------------------------------------------------------");
            System.out.println("| 1. Display Rooms                                                  |");
            System.out.println("| 2. Add Customer and Book Rooms                                    |");
            System.out.println("| 3. Generate Bill for a Customer                                   |");
            System.out.println("| 4. Exit                                                           |");
            System.out.println("---------------------------------------------------------------------");
            System.out.print("\n\nChoose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    booking.displayAllRooms();
                    break;
                case 2:
                    if (booking.hasAvailableRooms() && booking.getBookingsCount() < 5) {
                        makeBooking(booking, scanner);
                    } else {
                        System.out.println("No available rooms or maximum bookings reached.");
                    }
                    break;
                case 3:
                    viewExistingBills(booking, scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using our booking system!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void makeBooking(Booking booking, Scanner scanner) {
        booking.displayAvailableRooms();

        System.out.print("\nEnter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter room number to book: ");
        int roomNumber = scanner.nextInt();

        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer customer = booking.bookRoom(roomNumber, customerName, nights);
        if (customer != null) {
            customer.generateBill();
        } else {
            System.out.println("Booking failed. Room not available or invalid room number.");
        }
    }

    private static void viewExistingBills(Booking booking, Scanner scanner) {

        booking.displayAllBookings();
        System.out.print("Enter the number of the booking to view (0 to cancel): ");
        int bookingIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (bookingIndex > 0) {
            booking.displayBill(bookingIndex - 1);
        }
    }
}
