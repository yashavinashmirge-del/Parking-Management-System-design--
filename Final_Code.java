/*
    ParkingLot Autoamation System

    Step 1 : Create required enums
    Step 2 : Vehicle Hierarchy creation
    Step 3 : VehicleFactory creation (Factory Pattern)
    Step 4 : ParkingSpot Hirrarchy
    Step 5 : ParkingObserver class
    Step 6 : ParkingFloor class 
    Step 7 : ParkingDispalyBoard (Observer Pattern)
    Step 8 : ParkingStrategy Class (Strategy Pattern)
    Step 9 : PricingStrategy Class (Strategy Pattern)
    Step 10 : PaymentStrategy Class
    Step 11 : ParkingTicket Class
    Step 12 : EntryGate Class
    Step 13 : ExitGate Class
    Step 14 : ParkingLot Class (Singleton Pattern)
    Step 15 : Main class (Controller)

*/

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

/////////////////////////////////////////////////////////
// Step 1 : Create Enums
// It is used to create fixed constants which are required
// throughout the project
/////////////////////////////////////////////////////////

// Represents the diffrent types of vehicles supported by the project
enum VehicleType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents diffrent types of parking spots
enum SpotType
{
    BIKE,
    CAR,
    TRUCK
}

// Represents the current state of parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}

/////////////////////////////////////////////////////////
// Step 2 : Create Vehicle Class hierarchy
// It is used to create multiple types of classes which r
// represnets the types of vehicles
// Concepts : Abstraction , Inheritance, Polymorphism, Encapsulation
/////////////////////////////////////////////////////////

// Class which represnts a generic vehicle type
abstract class Vehicle
{
    // Abstracted (Hidden) characteristics of class

    private String vehicleNumber;

    private VehicleType vehicleType;

    // Parametrised constructor
    public Vehicle(String vehicleNumber, VehicleType vehicleType)
    {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    // Concrete getter method
    public VehicleType getVehicleType()
    {
        return this.vehicleType;
    }

    // Concrete getter method
    public String getVehicleNumber()
    {
        return this.vehicleNumber;
    }

    // Every concrete class will provide its own defination
    public abstract void display();
} 

// Class which represnets the Vechile type as Bike
class Bike extends Vehicle
{
    // Parametrised constructor
    public Bike(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.BIKE);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Bike : "+getVehicleNumber());
    }
}

// Class which represnets the Vechile type as Car
class Car extends Vehicle
{
    // Parametrised constructor
    public Car(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.CAR);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Car : "+getVehicleNumber());
    }
}

// Class which represnets the Vechile type as Truck
class Truck extends Vehicle
{
    // Parametrised constructor
    public Truck(String vehicleNumber)
    {
        // Calls Vechile class constructor
        super(vehicleNumber, VehicleType.TRUCK);
    }

    // Method overriding
    @Override 
    public void display()
    {
        System.out.println("Truck : "+getVehicleNumber());
    }
}

/////////////////////////////////////////////////////////
// Step 3 : Create VehicleFActory Class
// It is used to centralsied the creation of vechile objects
// Concepts : Factory Design Pattern
/////////////////////////////////////////////////////////

class VehicleFactory
{
    // Creates and return the desired class object

    public static Vehicle creatVehicle(VehicleType type, String number)
    {
        switch(type)
        {
            case BIKE:
                return new Bike(number);

            case CAR:
                return new Car(number);

            case TRUCK:
                return new Truck(number);

            default:
                throw new IllegalArgumentException("Invalid Vehicle type");
        }
    }
}

/////////////////////////////////////////////////////////
// Step 4 : Create ParkingSpot Hierarchy
// It is used to create hierarchy of Parking Spots
// Concepts : Encapsulation, Abstraction, Inheritance, Polymorphism
/////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique number for parking spot (Primary Key)
    private int spotNumber;

    // Type of parking spot
    private SpotType spotType;

    // Indaicates wheteher spot is currently occupied of not
    private boolean occupied;

    // Stores information about the vechicle
    private Vehicle vehicle;

    // Parametrised constructor
    public ParkingSpot(int spotNumber, SpotType spotType)
    {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        // Initialsed with default values
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSpotNumber()
    {
        return this.spotNumber;
    }

    public SpotType getSpotType()
    {
        return this.spotType;
    }

    public boolean isOccupied()
    {
        return this.occupied;
    }

    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // It is used to park the vechicle
    public void parkVehicle(Vehicle vehicle)
    {
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking spot is already occupied");
        }
        else
        {
            this.vehicle = vehicle;
            this.occupied = true;
        }
    }

    public Vehicle removeVehicle()
    {
        if(this.occupied == true)
        {
            Vehicle temp = vehicle;

            this.vehicle = null;
            this.occupied = false;

            return temp;
        }
        else
        {
            throw new RuntimeException("Parking spot is already empty");
        }
    }

    // This method decides whether we can park it in the spot or not
    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display()
    {
        System.out.println("Spot : "+spotNumber+ "["+ spotType+"]");
    
        if(this.occupied == true)
        {
            System.out.println("Occupied by : "+vehicle.getVehicleNumber());
        }
        else
        {
            System.out.println("Spot is available");
        }
    }
} // End of ParkingSpot class

class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber, SpotType.BIKE);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.BIKE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class CarSpot extends ParkingSpot
{
    public CarSpot(int spotNumber)
    {
        super(spotNumber, SpotType.CAR);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.CAR)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class TruckSpot extends ParkingSpot
{
    public TruckSpot(int spotNumber)
    {
        super(spotNumber, SpotType.TRUCK);
    }

    @Override 
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.TRUCK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

/////////////////////////////////////////////////////////
// Step 5 : ParkingObserver class
// It is used to automatically update display board when
// the parking availablity changes
// Concepts : Observer 
/////////////////////////////////////////////////////////

interface ParkingObserver
{
    void update();
}

/////////////////////////////////////////////////////////
// Step 6 : ParkingFloor class
// It is used to manage parking floor
// Concepts : Composition, ArrayList, Object Management
/////////////////////////////////////////////////////////

class ParkingFloor
{
    // Unique floor number
    private int floorNumber;

    // Collection of all parking spots
    private List<ParkingSpot> parkingSpots;

    // Collection of observers registered for the floor
    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();

        this.observers = new ArrayList<>();
    }

    public int getFloorNumber()
    {
        return this.floorNumber;
    }

    public void addParkingSpot(ParkingSpot spot)
    {
        parkingSpots.add(spot);
    }

    public void addObserver(ParkingObserver observer)
    {
        observers.add(observer);
    }

    private void notifyObservers()
    {
        for(ParkingObserver observer : observers)
        {
            observer.update();
        }
    }

    // Method is going to search parking spot for speific type of vechile
    public ParkingSpot findAvailabSpot(Vehicle vehicle)
    {
        for(ParkingSpot spot : parkingSpots)
        {
            if(!spot.isOccupied() && spot.canFitVehicle(vehicle))
            {
                return spot;
            }
        } 

        return null;
    }

    // Called when new vechile gets parked
    public void occupySpot(ParkingSpot spot, Vehicle vehicle)
    {
        // allocate sopt for the vechile
        spot.parkVehicle(vehicle);

        // Notify all observers about the availablity of spots
        notifyObservers();
    }

    public void releaseSpot(ParkingSpot spot)
    {
        // relase the already allocated spot
        spot.removeVehicle();

        // Notify all observers about the availablity of spots
        notifyObservers();
    }

    public int getAvailableCount(SpotType type)
    {
        int count = 0;

        for(ParkingSpot spot : parkingSpots)
        {
            if(spot.getSpotType() == type && !spot.isOccupied())
            {
                count++;
            }
        }

        return count;
    }

    // Display all parking spots on specific floor
    public void displayFloor()
    {
        System.out.println();

        System.out.println("Floor : "+floorNumber);

        for(ParkingSpot spot : parkingSpots)
        {
            spot.display();
        }
    }
}

/////////////////////////////////////////////////////////
// Step 7 : Create ParkingDispalyBoard Class
// It is used to create a class which displays the parking
// status

// Subject  ->  ParkingFloor 
// Observer ->  ParkingDispalyBoard

// Note : Any observer is going to observe the subject
// There will be multiple observers for one subject

// Concepts : Observer Design pattern
/////////////////////////////////////////////////////////

class ParkingDispalyBoard implements ParkingObserver
{
    // Floor whose availablity is displayed by this board
    private ParkingFloor floor;

    // Constructor
    public ParkingDispalyBoard(ParkingFloor floor)
    {
        this.floor = floor;
    }

    // Automatically called whenever floor availablity changes
    @Override 
    public void update()
    {
        System.out.println();
        System.out.println("--------- Display Board ---------");
        
        System.out.println("Floor : "+floor.getFloorNumber());

        System.out.println("Available Bike spots : "+floor.getAvailableCount(SpotType.BIKE));
       
        System.out.println("Available Car spots : "+floor.getAvailableCount(SpotType.CAR));
        
        System.out.println("Available Truck spots : "+floor.getAvailableCount(SpotType.TRUCK));
        
        System.out.println("---------------------------------");
        System.out.println();
    }
}

// We can create new observers for the same subject
/*
    class ParkingWebsite implements ParkingObserver
    {
        public void update()
        {   
        
        }
    }
*/

/////////////////////////////////////////////////////////
// Step 8 : Create ParkingStrategy Class

// It is used to create a class ParkingStrategy which is
// responsible to decide the parking spot selection

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

// Defines a common concepts for parking spot selection algorithm
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}

// Selects the first available parking spot 
class FirstAvialableParkingStrategy implements ParkingStrategy
{
    @Override 
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle)
    {
        // Iterate over all available floors
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvailabSpot(vehicle);

            if(spot != null)
            {
                return spot;
            }
        }

        return null;
    }
}

/*
    class NearestAvialableParkingStrategy implements ParkingStrategy
    {

    }
*/

/////////////////////////////////////////////////////////
// Step 9 : Create PricingStrategy Class

// It is used to create a class PricingStrategy
// It keeps the pricing algorithm idependent of exit logic

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

interface PricingStrategy
{
    double calculatePrice(Vehicle vehicle, long hours);
} 

class NormalPricingStrategy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 20;

            case CAR:
                return hours * 50;

            case TRUCK:
                return hours * 100;

            default:
                return 0;
        }
    }
}

class WeekendPricingStrategy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 40;

            case CAR:
                return hours * 100;

            case TRUCK:
                return hours * 200;

            default:
                return 0;
        }
    }
}

/////////////////////////////////////////////////////////
// Step 10 : Create PaymentStrategy Class

// It is used to create a class PaymentStrategy
// It supports diffrent types of payment methods

// Concepts : Strategy Design pattern
/////////////////////////////////////////////////////////

// Common contract for all payment methods
interface PaymentStrategy
{
    void pay(double amount);
}

class UPIPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("UPI payment succesful : Rs. "+amount);
    }
}

class CardPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("Card payment succesful : Rs. "+amount);
    }
}

class CashPayment implements PaymentStrategy
{
    @Override 
    public void pay(double amount)
    {
        System.out.println("Cash payment succesful : Rs. "+amount);
    }
}

/////////////////////////////////////////////////////////
// Step 11 : Create ParkingTicket Class

// It is used to represent one complete parking transaction

/////////////////////////////////////////////////////////

class ParkingTicket
{
    // Used for generating unique tickets
    private static int counter = 1000;

    // Ticket number for unique tcket
    private int ticketNumber;

    // Vechile associated with that ticket
    private Vehicle vehicle;

    // Floor on which the vechile is parked
    private ParkingFloor floor;

    // Actual spot on which the vechile is parked
    private ParkingSpot spot;

    // Time at which vechile arrives
    private LocalDateTime entryTime;

    // Time at which vechile exited from parking floor
    private LocalDateTime exitTime;

    // It maintains the status of the ticket
    private TicketStatus status;

    // Paramerised constructor
    public ParkingTicket(
                            Vehicle vehicle,
                            ParkingFloor floor,
                            ParkingSpot spot
                        )
    {
        this.ticketNumber = ++counter;
        this.vehicle = vehicle;
        this.floor = floor;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }

    // Getter method for ticket number
    public int getTicketNumber()
    {
        return this.ticketNumber;
    }

    // Getter method for vechile
    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // Getter method for floor
    public ParkingFloor getFloor()
    {
        return this.floor;
    }

    // Getter method for spot
    public ParkingSpot getSpot()
    {
        return this.spot;
    }

    // Getter method for entrytime
    public LocalDateTime getEntryTime()
    {
        return this.entryTime;
    }

    // Getter method for exittime
    public LocalDateTime getExitTime()
    {
        return this.exitTime;
    }

    // Getter method for status
    public TicketStatus getStatus()
    {
        return this.status;
    }

    // Method gets called when vechile is going out
    public void closeTicket()
    {
        this.exitTime = LocalDateTime.now();

        this.status = TicketStatus.CLOSED;
    }

    // Calculate the total number of hours the vechile is parked
    public long calculateHours()
    {
        LocalDateTime endtime;

        if(exitTime == null)
        {
            endtime = LocalDateTime.now();
        }
        else
        {
            endtime = exitTime;
        }

        // Calculate the actual time
        long minutes = Duration.between(entryTime, endtime).toMinutes();
        
        // Converts minutes to hours
        long hours = minutes / 60;

        if(minutes % 60 != 0)
        {
            hours++;
        }
        
        if(hours == 0)
        {
            hours = 1;
        }

        return hours;
    }

    // It will display complete ticket on screen
    public void displayTicket()
    {
        System.out.println();

        System.out.println("---------------------------------");
        System.out.println("---------- Parking Ticket -------");
        System.out.println("---------------------------------");

        System.out.println("Ticket Number : "+this.ticketNumber);

        System.out.println("Vechile Number : "+this.vehicle.getVehicleNumber());
        
        System.out.println("Vechile Type : "+this.vehicle.getVehicleType());

        System.out.println("Floor Number : "+this.floor.getFloorNumber());

        System.out.println("Spot Number : "+this.spot.getSpotNumber());

        System.out.println("Entry Time : "+this.entryTime);

        System.out.println("Ticket Status : "+this.status);

        System.out.println("---------------------------------");

        System.out.println();
    }
}

/////////////////////////////////////////////////////////
// Step 12 : Create EntryGate Class

// It is used to handle entry of a vechile and its ticket
// generation

/////////////////////////////////////////////////////////

class EntryGate
{
    private int gateNumber;

    public EntryGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // it generates the new parking ticket when
    // veichle enters

    public ParkingTicket generateTicket(Vehicle vehicle, ParkingFloor floor, ParkingSpot sopt)
    {
        System.out.println("Vehile entering from gate : "+this.gateNumber);
        
        // New parking ticket gets generated for the vechile
        return new ParkingTicket(vehicle, floor, sopt);
    }
}

/////////////////////////////////////////////////////////
// Step 13 : Create ExitGate Class

// It is used to handle billing and payment during the 
// vechile exit

/////////////////////////////////////////////////////////

class ExitGate
{
    private int gateNumber;

    public ExitGate(int gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber()
    {
        return this.gateNumber;
    }

    // This performs complete exit operations

    public void processExit(
                                ParkingTicket ticket,
                                PricingStrategy pricingStrategy,
                                PaymentStrategy paymentStrategy
                            )
    {
        // Step 1 : Close the ticket and record the exit time
        ticket.closeTicket();

        // Step 2 : Calculate the parking duration
        long hours = ticket.calculateHours();

        // Step 3 : Calculate the parking chargers
        double amount = pricingStrategy.calculatePrice(ticket.getVehicle(), hours);

        System.out.println();

        System.out.println("Vechile exiting from gate : "+gateNumber);
        
        System.out.println("PArking Duration : "+hours);

        System.out.println("Parking charges : "+amount);
        
        // Step 4 : Process the payment using selected payment strategy

        paymentStrategy.pay(amount);
    }
}

/////////////////////////////////////////////////////////
// Step 14 : Create ParkingLot Class

// Pattern : Singleton pattern

// This class is the main controller of complete parking system

/////////////////////////////////////////////////////////

// Sigleton class

class ParkingLot
{
    // instance of class
    
    private static ParkingLot instance;

    // Store the parking Lot name
    
    private String parkingLotName;

    // Store all floors of the parking lot
    
    private List<ParkingFloor> floors;

    // Maps the ticket number with active parking slot
    
    private Map<Integer, ParkingTicket> activeTickets;

    // Maps vechile number with active tickets
    // Used for searching vehicle
    // It prevents duplicate parking

    private Map<String, ParkingTicket> vehicleTicketMap;   

    // Algorithm used for selecting parking spot
    
    private ParkingStrategy parkingStrategy;

    // Algorithm used for calculating parking charges
    
    private PricingStrategy pricingStrategy;

    // Private constructor for singleton class
    
    private ParkingLot()
    {
        floors = new ArrayList<>();

        activeTickets = new HashMap<>();

        vehicleTicketMap = new HashMap<>();

        // Default parking strategy

        parkingStrategy = new FirstAvialableParkingStrategy();

        // Default pricing strategy

        pricingStrategy = new NormalPricingStrategy();

    }

    // Method to return the singleton class object
    public static synchronized ParkingLot getInstance()
    {
        if(instance == null)
        {
            instance = new ParkingLot();
        }

        return instance;
    } 

    // Used to set name for complete parking lot

    public void setParkingLotName(String parkingLotName)
    {
        this.parkingLotName = parkingLotName;
    }

    // Used to add new parking floor

    public void addFloor(ParkingFloor floor)
    {
        // Insert in arraylist

        floors.add(floor);
    }

    // This method returns list of all floors

    public List<ParkingFloor> getFloors()
    {
        return floors;
    }

    // This method can be used to change the default parking strategy

    public void setParkingStrategy(ParkingStrategy strategy)
    {
        this.parkingStrategy = strategy;
    }

    // This method can be used to change the default priscing strategy

    public void setPricingStrategy(PricingStrategy strategy)
    {
        this.pricingStrategy = strategy;
    }

    /*
        Algorithm for parking the vechile

        Check Duplicate Vechile
                |
        Find available spot
                |
        Identify floor for vechile
                |
        Occupy Spot for vechile
                |
        Generate ticket for vechile
                |
        Store the finale ticket
    
    */
    public ParkingTicket parkVehicle(
                                        Vehicle vechile,
                                        EntryGate entryGate
    )
    {
        // Step 1 : Prevent the same vechile for being parked multiple times
        
        if(vehicleTicketMap.containsKey(vechile.getVehicleNumber()))
        {
            System.out.println("This vechile is already parked");

            throw new RuntimeException("This vechile is already parked");
        }

        // Step 2 : Find the available parking spot

        ParkingSpot spot = parkingStrategy.findSpot(floors, vechile);

        // If there is no empty spot

        if(spot == null)
        {
            throw new RuntimeException("Parking is full");
        }

        // Step 3 : Identiy the exact floor for the vechile

        ParkingFloor selectedFloor = null;

        for(ParkingFloor floor : floors)
        {
            ParkingSpot temp = floor.findAvailabSpot(vechile);
            
            if(temp == spot)
            {
                selectedFloor = floor;
                break;
            }
        }

        if(selectedFloor == null)
        {
            throw new RuntimeException("Unabl to identify floor");
        }

        // Step 4 : Occupy the spot

        selectedFloor.occupySpot(spot, vechile);

        // Step 5 : Generate parking ticket from entry gate

        ParkingTicket ticket = entryGate.generateTicket(vechile, selectedFloor, spot);

        // Step 6 : Store the tickct using ticket number
        activeTickets.put(ticket.getTicketNumber(),ticket);

        // Step 7 : Store the ticket using vechile number
        vehicleTicketMap.put(vechile.getVehicleNumber(), ticket);

        return ticket;
    }

    /*
        Find ticket
            |
        Process Exit
            |
        Calculate Charges
            |
        Payment
            |
        Release Spot
            |
        Remove active records 
    */

    public void removeVehicle(
                                int ticketNumber,
                                ExitGate exitGate, 
                                PaymentStrategy paymentStrategy
    )
    {

        // Step 1 : Find active ticket using ticket number
        ParkingTicket ticket = activeTickets.get(ticketNumber);

        if(ticket == null)
        {
            throw new RuntimeException("There is no such ticket");
        }

        // Step 2 : Perform billing and payment
        exitGate.processExit(ticket, pricingStrategy, paymentStrategy);

        // Step 3 : Relaease the occupied spot
        ticket.getFloor().releaseSpot(ticket.getSpot());

        // Step 4 : Remove ticket
        activeTickets.remove(ticketNumber);

        // Step 5 : Remove vechile from active vechile
        vehicleTicketMap.remove(ticket.getVehicle().getVehicleNumber());

        System.out.println("Vechile removed succesfully");
    }

    // Search the specified method
    public ParkingTicket searchVehicle(String vechileNumber)
    {
        return vehicleTicketMap.get(vechileNumber);
    }

    // Display complete parking lot information
    public void displayParkingLot()
    {
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println("----- Parking Lot Details -----");
        System.out.println("-------------------------------");

        for(ParkingFloor floor : floors)
        {
            floor.displayFloor();
        }
    }
} // End of ParkingLot Class

/////////////////////////////////////////////////////////

// Step 15 : Controller of the project

/////////////////////////////////////////////////////////

/*
    1 : Create ParkingLot class object

    2 : Create floors

    3 : Add parking spots

    4 : Create Display board

    5 : Register observers

    6 : Add floor to parkingLot

    7 : Create Entry Exit Gates

    8 : Display Menu

*/

class Final_Code
{
    public static void main(String A[]) throws Exception
    {
        Scanner sobj = new Scanner(System.in);

        //////////////////////////////////////////
        /// 
        ///  1 : Create single Parking Lot object
        /// 
        /////////////////////////////////////////
        
        ParkingLot parkingLot = ParkingLot.getInstance();

        parkingLot.setParkingLotName("Marvellous ParkEngine");

        //////////////////////////////////////////
        /// 
        ///  2 : Create multiple floors
        /// 
        /////////////////////////////////////////
        
        // Add first floor

        ParkingFloor floor1 = new ParkingFloor(1);

        //////////////////////////////////////////
        /// 
        ///  3 : Create multiple spots
        /// 
        /////////////////////////////////////////
        
        floor1.addParkingSpot(new BikeSpot(101));
        floor1.addParkingSpot(new BikeSpot(102));

        floor1.addParkingSpot(new CarSpot(103));
        floor1.addParkingSpot(new CarSpot(104));

        floor1.addParkingSpot(new TruckSpot(105));
        floor1.addParkingSpot(new TruckSpot(106));

        //////////////////////////////////////////
        /// 
        ///  3 : Create Display Board
        /// 
        /////////////////////////////////////////
        
        ParkingDispalyBoard board1 = new ParkingDispalyBoard(floor1);

        // Register the display board with observer
        floor1.addObserver(board1);

        // Add second floor

        ParkingFloor floor2 = new ParkingFloor(2);

        //////////////////////////////////////////
        /// 
        ///  3 : Create multiple spots
        /// 
        /////////////////////////////////////////
        
        floor2.addParkingSpot(new BikeSpot(201));
        floor2.addParkingSpot(new BikeSpot(202));

        floor2.addParkingSpot(new CarSpot(203));
        floor2.addParkingSpot(new CarSpot(204));

        floor2.addParkingSpot(new TruckSpot(205));
        floor2.addParkingSpot(new TruckSpot(206));

        //////////////////////////////////////////
        /// 
        ///  3 : Create Display Board
        /// 
        /////////////////////////////////////////
        
        ParkingDispalyBoard board2 = new ParkingDispalyBoard(floor2);

        // Register the display board with observer
        floor2.addObserver(board2);
        
        //////////////////////////////////////////
        /// 
        ///  6 : Add floors to ParkingLot
        /// 
        /////////////////////////////////////////
        
        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);
        
        //////////////////////////////////////////
        /// 
        ///  7 : Create EntryGate and ExitGate
        /// 
        /////////////////////////////////////////
        
        EntryGate entryGate = new EntryGate(1);

        ExitGate exitGate = new ExitGate(1);

        //////////////////////////////////////////
        /// 
        ///  8 : Display Menu
        /// 
        /////////////////////////////////////////
        int choice = 0;

        while(true)
        {
            System.out.println("---------------------------------");
            System.out.println("---- Marvellous ParkEngine ------");
            System.out.println("---------------------------------");

            System.out.println("1 : Park Vechile");
            System.out.println("2 : Exit Vechile");
            System.out.println("3 : Search Vechile");
            System.out.println("4 : Display Parking Lot");
            System.out.println("5 : Exit");

            System.out.println("Enter your choice : ");

            choice = sobj.nextInt();

            try
            {
                switch(choice)
                {
                    case 1: // Park Vechile
                    {
                        System.out.println();

                        System.out.println("Select vachile type : ");
                        
                        System.out.println("1 : Bike");
                        System.out.println("2 : Car");
                        System.out.println("3 : Truck");
                        
                        int type = sobj.nextInt();
                        
                        System.out.println("Enter vechile number : ");

                        String number = sobj.next();

                        Vehicle vehicle;

                        // Factory pattern is used

                        switch(type)
                        {
                            case 1 :  // BIKE
                                vehicle = VehicleFactory.creatVehicle(VehicleType.BIKE, number);
                                
                                break;

                            case 2 :  // CAR
                                vehicle = VehicleFactory.creatVehicle(VehicleType.CAR, number);
                                
                                break;

                            case 3 : // TRUCK
                                vehicle = VehicleFactory.creatVehicle(VehicleType.TRUCK, number);
                                
                                break;

                            default:
                                System.out.println("Invalid type of vechile");
                                
                                continue;
                        }

                        // Park the the vechile and genearte the ticket

                        ParkingTicket ticket = parkingLot.parkVehicle(vehicle, entryGate);

                        // Display generated ticket
                        ticket.displayTicket();

                        break;

                    } // End of Case 1
                    
                    case 2 :  // Exit vechile
                    {

                        System.out.println("Enter ticket number : ");

                        int ticketNumber = sobj.nextInt();

                        System.out.println();
                        
                        System.out.println("Enter the payment option : ");
                        
                        System.out.println("1 : Cash");
                        System.out.println("2 : UPI");
                        System.out.println("3 : Card");
                        
                        int paymentType = sobj.nextInt();

                        PaymentStrategy paymentStrategy;

                        switch(paymentType)
                        {
                            case 1 : // Cash
                                paymentStrategy = new CashPayment();
                                break;
                            
                            case 2 : // UPI
                                paymentStrategy = new UPIPayment();
                                break;
                        
                            case 3 : // CARD
                                paymentStrategy = new CardPayment();
                                break;
                            
                            default: 
                                System.out.println("Invalid payment option");
                                continue;
                        } // End of payment switch

                        parkingLot.removeVehicle(ticketNumber, exitGate, paymentStrategy);
                        
                        break;
                    }

                    case 3 : // Search Vechile
                    {
                        System.out.println("Enter vechile number : ");

                        String vechileNumber = sobj.next();

                        ParkingTicket ticket = parkingLot.searchVehicle(vechileNumber);

                        if(ticket == null)
                        {
                            System.out.println("This vechile is not parked");
                        }
                        else
                        {
                            ticket.displayTicket();
                        }

                        break;
                    }

                    case 4 : // Display Parking Lot
                    {
                        parkingLot.displayParkingLot();

                        break;
                    }

                    case 5 : 
                    {
                        System.out.println("Thank you for using Marvellous ParkEngine");

                        sobj.close();

                        return ;
                    }

                    default:
                    {
                        System.out.println("Invalid option");
                    }

                } // End of switch
            
            } // End of try
            
            catch(Exception eobj)
            {
                System.out.println("Exception occured : "+eobj);
            }
        } // End of while

    } // End of main

} // End of main class