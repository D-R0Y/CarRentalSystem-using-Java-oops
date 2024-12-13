import java.util.*;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        isAvailable = true;
    }

    public String getcarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentaldays) {
        return basePricePerDay * rentaldays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

}

class Customer {
    private String CustomerID;
    private String CustomerName;

    Customer(String CustomerID, String CustomerName) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
    }

    public String getName() {
        return CustomerName;
    }

    public String getCustomerId() {
        return CustomerID;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void rentCar(Car c, Customer cus, int days) {
        if (c.isAvailable()) {
            c.rent();
            rentals.add(new Rental(c, cus, days));
        } else {
            System.out.println("The car is not available for rent");
        }

    }

    public void retCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;

        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==== Car Rental System ====");
            System.out.println("1. Rent a Car ");
            System.out.println("2. Return  a Car ");
            System.out.println("3 .Exit");
            System.out.println("Enter your choice between 1 to 3");

            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.println("\n=== Rent a car === \n");
                System.out.println("Enter Your name : ");
                String customerName = sc.nextLine();
                System.out.println("\n Available Cars : \n");

                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getcarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.println("\n Enter the carid you want to rent : ");
                String carId = sc.nextLine();

                System.out.println("\n Enter the number of days for rental :");
                int days = sc.nextInt();
                sc.nextLine();
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getcarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(days);
                    System.out.println("\n==== Rental Information ===\n");
                    System.out.println("customer ID " + newCustomer.getCustomerId());
                    System.out.println("Customer Name " + newCustomer.getName());
                    System.out.println("selected car :" + selectedCar.getBrand());
                    System.out.println("RentalDays : " + days);
                    System.out.println("Total Price to be paid :" + totalPrice);

                    System.out.println("\n confirm Rental ?? (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, days);
                        System.out.println("\n car rented successfully.");
                    } else {
                        System.out.println("\nRental cancelled");
                    }
                } else {
                    System.out.println("Invalid car selection or car is not available for rent \n");
                }

            } else if (choice == 2) {
                System.out.println("\n ==== Return a car ==== \n");
                System.out.println("Enter carId you want to return : ");
                String carId = sc.nextLine();
                Car cartoreturn = null;

                for (Car car : cars) {
                    if (car.getcarId().equals(carId) && !car.isAvailable()) {
                        cartoreturn = car;
                        break;
                    }
                }
                if (cartoreturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == cartoreturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        retCar(cartoreturn);
                        System.out.println("Car returned successfully by " + customer.getName());

                    } else {
                        System.out.println("Invalid Car id or car is not rented\n");
                    }
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice ,please enter a valid choice\n");
            }

        }
        System.out.println("\n Thank you for using car rental system -_-");
    }

}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalsys = new CarRentalSystem();

        Car car1 = new Car("c001", "Tyota", "Camry", 60.0);
        Car car2 = new Car("c002", "Ford", "Mustang", 500.0);
        Car car3 = new Car("c003", "Lamborgini", "Urus", 600.0);

        rentalsys.addCar(car1);
        rentalsys.addCar(car2);
        rentalsys.addCar(car3);

        rentalsys.menu();
    }
}