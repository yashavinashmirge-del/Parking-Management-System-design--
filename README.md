# 🅿️ Parking Management System 

A console-based **Parking Lot Automation System** written in **Java**, built to demonstrate clean Object-Oriented Design and classic **Design Patterns** in a real-world, easy-to-follow scenario.

---

## 📌 Overview

The system manages a multi-floor parking lot that supports bikes, cars and trucks. It handles the complete vehicle lifecycle: **entry → spot allocation → ticket generation → exit → billing → payment → spot release**, while keeping live availability boards updated automatically.

---

## ✨ Features

- Multi-floor parking lot with typed spots (Bike, Car, Truck)
- Automatic parking spot allocation
- Unique parking ticket generation with entry time
- Duplicate parking prevention (same vehicle cannot be parked twice)
- Hourly billing (rounded up, minimum 1 hour) with vehicle-based pricing
- Multiple payment methods: **Cash, UPI, Card**
- Real-time **display board** updates on every park / exit
- Search a parked vehicle by its number
- Menu-driven console interface with exception handling

---

## 🧱 Design Patterns Used

| Pattern | Where it is used | Purpose |
|---|---|---|
| **Singleton** | `ParkingLot` | Guarantees a single central controller of the system |
| **Factory** | `VehicleFactory` | Centralises creation of `Bike`, `Car`, `Truck` objects |
| **Strategy** | `ParkingStrategy`, `PricingStrategy`, `PaymentStrategy` | Swap spot-selection, pricing and payment algorithms without changing core logic |
| **Observer** | `ParkingFloor` (subject) → `ParkingDispalyBoard` (observer) | Display boards refresh automatically when availability changes |

### OOP Concepts Demonstrated
Abstraction, Encapsulation, Inheritance, Polymorphism, Composition.

---

## 🗂️ Class Structure

```
Enums
 ├── VehicleType      (BIKE, CAR, TRUCK)
 ├── SpotType         (BIKE, CAR, TRUCK)
 └── TicketStatus     (ACTIVE, CLOSED)

Vehicle (abstract)
 ├── Bike
 ├── Car
 └── Truck

VehicleFactory                         → Factory Pattern

ParkingSpot (abstract)
 ├── BikeSpot
 ├── CarSpot
 └── TruckSpot

ParkingObserver (interface)
 └── ParkingDispalyBoard               → Observer Pattern

ParkingFloor                           → Subject, owns spots & observers

ParkingStrategy (interface)
 └── FirstAvialableParkingStrategy     → Strategy Pattern

PricingStrategy (interface)
 ├── NormalPricingStrategy
 └── WeekendPricingStrategy

PaymentStrategy (interface)
 ├── CashPayment
 ├── UPIPayment
 └── CardPayment

ParkingTicket                          → One complete parking transaction
EntryGate / ExitGate                   → Ticket generation / billing & payment
ParkingLot                             → Singleton controller
program1017                            → Main class (menu-driven driver)
```

---

## 🔄 Workflow

**Parking a vehicle**

```
Check duplicate vehicle
        ↓
Find available spot (ParkingStrategy)
        ↓
Identify the floor
        ↓
Occupy spot  →  Notify display boards
        ↓
Generate ticket (EntryGate)
        ↓
Store ticket records
```

**Exiting a vehicle**

```
Find active ticket
        ↓
Close ticket & record exit time
        ↓
Calculate duration & charges (PricingStrategy)
        ↓
Process payment (PaymentStrategy)
        ↓
Release spot  →  Notify display boards
        ↓
Remove active records
```

---

## 💰 Pricing

Charges are per hour. Partial hours are rounded up and the minimum billed duration is 1 hour.

| Vehicle | Normal (₹/hr) | Weekend (₹/hr) |
|---|---|---|
| Bike | 20 | 40 |
| Car | 50 | 100 |
| Truck | 100 | 200 |

`NormalPricingStrategy` is the default. Switch with `parkingLot.setPricingStrategy(new WeekendPricingStrategy());`

---

## 🏗️ Default Setup

| Floor | Bike spots | Car spots | Truck spots |
|---|---|---|---|
| 1 | 101, 102 | 103, 104 | 105, 106 |
| 2 | 201, 202 | 203, 204 | 205, 206 |

Each floor has its own display board registered as an observer.

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher

### Clone the repository
```bash
git clone https://github.com/yashavinashmirge-del/Parking-Management-System-design--.git
cd Parking-Management-System-design--
```

### Compile and run
The entry point is the `program1017` class.

```bash
javac program1017.java
java program1017
```

> If your source file has a different name, compile that file instead; the class to run is still `program1017`.

---

## 🖥️ Usage

```
---------------------------------
---- Marvellous ParkEngine ------
---------------------------------
1 : Park Vechile
2 : Exit Vechile
3 : Search Vechile
4 : Display Parking Lot
5 : Exit
```

1. **Park Vehicle**: choose the vehicle type, enter the vehicle number, and a ticket is printed.
2. **Exit Vehicle**: enter the ticket number and choose Cash / UPI / Card to pay the bill.
3. **Search Vehicle**: look up an active ticket by vehicle number.
4. **Display Parking Lot**: view every floor and the status of each spot.
5. **Exit**: close the application.

### Sample Display Board
```
--------- Display Board ---------
Floor : 1
Available Bike spots : 1
Available Car spots : 2
Available Truck spots : 2
---------------------------------
```

---

## 🔧 Extending the System

The design makes common changes simple:

- **New vehicle type**: add an enum value, a `Vehicle` subclass, a `ParkingSpot` subclass and a case in `VehicleFactory`.
- **New parking logic** (e.g. nearest spot): implement `ParkingStrategy` and call `setParkingStrategy(...)`.
- **New pricing rule** (e.g. peak hours): implement `PricingStrategy`.
- **New payment method**: implement `PaymentStrategy`.
- **New observer** (e.g. website or mobile app): implement `ParkingObserver` and register it with a `ParkingFloor`.

---

## 🛣️ Future Improvements

- Persistent storage using a database (MySQL / MongoDB)
- Multiple entry and exit gates
- Thread-safe operations for concurrent entry/exit
- Reservation and monthly pass support
- Automatic weekend pricing based on the current date
- Unit tests with JUnit

---

## 👤 Author

**Yash Avinash Mirge**
Java Backend Developer | Pune, Maharashtra
GitHub: [yashavinashmirge-del](https://github.com/yashavinashmirge-del)

---

⭐ If you found this project useful, consider giving it a star!
