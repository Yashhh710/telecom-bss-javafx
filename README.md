# 📡 Telecom BSS — SIM Activation & Billing System

A JavaFX-based Telecom Business Support System (BSS) developed as my Java Final Exam Project.

The application provides a complete desktop-based solution for managing telecom customers, SIM cards, plans, usage, billing, payments, and reports through a modern black & gold JavaFX interface.

[Click for live Web demo](https://yashhh710.github.io/telecom-bss-javafx/WEB-DEMO.html)

Click for Report

## Project Overview

Telecom operators need to manage large amounts of customer and billing information, including SIM activation, subscription plans, call/SMS/data usage, monthly bills, and payments.

Telecom BSS is designed as an academic implementation of these core telecom operations using Java, JavaFX, Object-Oriented Programming, and Java Collections Framework.

The project demonstrates how different Java concepts can be combined to build a functional telecom management application.

This project was developed as my Java Final Exam Project.

<img width="1293" height="836" alt="image" src="https://github.com/user-attachments/assets/7509a153-5296-422e-b7d9-f1d3710dc921" />

## Features

### Customer Management
- Add new customers
- View customer records
- Update customer information
- Delete customer records
- Search customers
- Search by SIM number
- Customer location management

### SIM Registration
- Register SIM cards
- Activate SIM cards
- Assign SIM to customers
- Assign telecom plans
- SIM number validation
- SIM-to-customer mapping

### Plan Management
- Create telecom plans
- Prepaid and postpaid plans
- Plan code management
- Monthly/rental pricing
- Call allowance
- Data allowance
- SMS allowance
- View available plans

### Usage Tracking
Track:
- Call usage
- SMS usage
- Data usage
- Usage date
- Customer/SIM association
- Usage history

### Bill Generation
- Generate customer bills
- Calculate plan charges
- Generate invoice-style bill details
- Bill preview
- Bill amount tracking
- Bills sorted using TreeMap

### Payment Recording
Supports:
- UPI
- Card
- Cash
- Net Banking

Payment records can be associated with generated bills.

### Search & Sorting
- Search customers
- Search SIM numbers
- Sort billing information
- Sort usage information
- View organized customer records

### Reports
- Customer records
- SIM records
- Usage records
- Billing information
- Payment information
- Plan information

## Java Concepts Demonstrated

This project specifically implements the concepts required by the case study.

| Java Concept | Implementation |
|---|---|
| Classes & Objects | Customer, SIMCard, Plan, Bill, Usage, Payment |
| Constructors | Model object initialization |
| Enum | PlanType, PaymentStatus |
| ArrayList | Customer, plan, SIM and payment records |
| LinkedList | Usage history |
| HashMap | SIM number → Customer mapping |
| TreeMap | Bills organized by date |
| StringBuilder | Invoice/bill generation |
| CRUD | Customer and plan management |
| Searching | Customer/SIM search |
| Sorting | Usage and billing records |
| Exception Handling | Invalid input and business validation |
| Validation | SIM, customer, plan and usage validation |
| JavaFX | Desktop GUI |

## Project Architecture

```
TelecomBillingSystem/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── telecom/
        │           │
        │           ├── Main.java
        │           │
        │           ├── model/
        │           │   ├── Customer.java
        │           │   ├── SIMCard.java
        │           │   ├── Plan.java
        │           │   ├── Bill.java
        │           │   ├── Usage.java
        │           │   ├── Payment.java
        │           │   ├── PlanType.java
        │           │   └── PaymentStatus.java
        │           │
        │           ├── service/
        │           │   ├── CustomerService.java
        │           │   ├── SIMService.java
        │           │   ├── UsageService.java
        │           │   ├── BillingService.java
        │           │   └── PaymentService.java
        │           │
        │           ├── repository/
        │           │   └── DataStore.java
        │           │
        │           ├── controller/
        │           │   └── DashboardController.java
        │           │
        │           └── util/
        │               ├── Validator.java
        │               ├── ValidationException.java
        │               └── BillGenerator.java
        │
        └── resources/
            └── css/
                └── style.css
```

## Technologies Used

| Category | Details |
|---|---|
| Programming Language | Java 21 |
| GUI Framework | JavaFX 21 |
| Build Tool | Apache Maven |
| Programming Concepts | Object-Oriented Programming, Collections Framework, Exception Handling, Data Validation, CRUD Operations, Searching, Sorting, Enums, StringBuilder |

## User Interface

The application uses a premium black and gold theme.

### UI Highlights
- Dark navigation sidebar
- Gold accent color
- Clean dashboard
- Customer tables
- Interactive forms
- Dropdown menus
- Hover effects
- Selected navigation effects
- Responsive JavaFX layouts
- Billing and payment interfaces
- Reports section

## Sample Data

The application includes sample data for demonstration purposes.

### Customers

The system is preloaded with **20 sample customers**, including customers from:
- Kharghar
- Panvel
- Navi Mumbai
- Vashi
- Belapur
- Kamothe
- Kalamboli
- Nerul
- Seawoods
- Airoli
- Ghansoli
- Taloja

### Plans

The application includes prepaid and postpaid plans such as:
- P149 - Starter 149
- P199 - Basic 199
- P299 - Smart 299
- P399 - Power 399
- P499 - Max 499
- P799 - Premium 799

### Usage

The demo dataset contains multiple:
- Call records
- SMS records
- Data usage records
- Usage dates

This makes the application suitable for demonstrating search, sorting, usage tracking and reporting features.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/telecom-bss-javafx.git
```

Go into the project directory:

```bash
cd telecom-bss-javafx
```

### 2. Requirements

Make sure the following are installed:
- Java JDK 21
- Apache Maven
- JavaFX 21 compatible environment
- IntelliJ IDEA / Eclipse / VS Code

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

### ▶️ Run the Application

From the project root:

```bash
mvn clean javafx:run
```

Maven will download the required JavaFX dependencies and launch the application.

## Application Modules

The application contains the following main modules:

```
Dashboard
│
├── Customers
│   ├── Add Customer
│   ├── Update Customer
│   ├── Delete Customer
│   └── Search Customer
│
├── SIM Registration
│   ├── Register SIM
│   ├── Activate SIM
│   └── Assign Plan
│
├── Plans
│   ├── Create Plan
│   ├── View Plans
│   └── Prepaid/Postpaid
│
├── Usage Tracking
│   ├── Calls
│   ├── SMS
│   └── Data
│
├── Bill Generation
│   ├── Generate Bill
│   └── Invoice Preview
│
├── Payments
│   ├── UPI
│   ├── Card
│   ├── Cash
│   └── Net Banking
│
└── Reports
    ├── Customer Reports
    ├── Usage Reports
    ├── Billing Reports
    └── Payment Reports
```

## Data Management

The current version uses an in-memory data store instead of an external database.

The main data structures are:
- `ArrayList<Customer>`
- `ArrayList<Plan>`
- `ArrayList<SIMCard>`
- `LinkedList<Usage>`
- `HashMap<String, Customer>`
- `TreeMap<LocalDate, Bill>`
- `ArrayList<Payment>`

This was intentionally implemented to demonstrate the Java Collections Framework required by the case study.

> **Note:** Data is reset to the predefined sample data when the application is restarted.

## Core Classes

### Customer
Stores customer information:
- Customer ID
- Name
- Email
- Phone
- Location
- SIM Card
- Plan

### SIMCard
Stores:
- SIM Number
- Activation Status
- Plan

Stores:
- Plan Code
- Plan Name
- Plan Type
- Price
- Validity
- Data Allowance
- SMS Allowance

### Usage
Stores:
- SIM Number
- Date
- Call Usage
- SMS Usage
- Data Usage

### Bill
Stores generated billing information.

### Payment
Stores payment details and payment status.

## Validation & Exception Handling

The application includes validation for important inputs such as:
- Customer name
- Phone number
- Email
- SIM number
- Plan code
- Usage values
- Recharge/plan values

Invalid operations are handled using custom exceptions.

Example: `ValidationException`

This prevents invalid telecom records from being added to the system.

## Bill Generation

The project uses Java's `StringBuilder` for generating formatted billing information.

Example structure:

```
================================
        TELECOM BSS
        CUSTOMER BILL
================================

Customer:
SIM Number:
Plan:
Plan Type:

--------------------------------
Call Usage:
SMS Usage:
Data Usage:
--------------------------------

Plan Charge:
Additional Usage:
Total Amount:

================================
```

## Project Objectives

The main objectives of this project are:
- Manage telecom customer information.
- Register and activate SIM cards.
- Manage prepaid and postpaid plans.
- Track call, SMS and data usage.
- Generate customer bills.
- Record customer payments.
- Search and sort telecom records.
- Generate billing reports.
- Demonstrate Java Collections Framework.
- Build a functional JavaFX desktop application.

## Academic Purpose

This project was developed as a Java Final Exam / Case Study Project to demonstrate practical implementation of Java programming concepts.

The project combines:

Java + OOP + Collections + Exception Handling + Validation + CRUD + Searching + Sorting + JavaFX

into one complete application.

## Future Improvements

Possible future improvements include:
- MySQL/PostgreSQL database integration
- User authentication
- Admin and employee roles
- Persistent customer data
- Online recharge system
- PDF bill generation
- Email bill delivery
- SMS notifications
- Advanced analytics dashboard
- Monthly revenue charts
- Real telecom API integration
- Cloud deployment
- Multi-user support

## Disclaimer

This is an academic/educational project created for a Java final examination and case study.

It is a simulated telecom billing system and is not affiliated with or operated by any real telecom company.

The customer, SIM, plan, usage, billing and payment data included in the application are sample/demo data.

## Developer

**Yash Tambade**

B.Tech Computer Science Student

Java | JavaFX | Frontend Development | Software Development

## Project

If you find this project useful or interesting, consider giving the repository a ⭐ on GitHub.

*Telecom BSS — SIM Activation & Billing System — Java Final Exam Project*
