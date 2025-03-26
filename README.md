# Hotel Booking System - Spring Boot Application

## A RESTful API for hotel bookings, built with Spring Boot
This project is a Hotel Booking System built using Spring Boot, Spring Security, and JWT for secure user authentication. It allows users to manage hotel room bookings, user roles (ADMIN and USER), and perform CRUD operations on bookings and users


## 📂 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── org/hotelbooking/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers (Booking, Hotel, Room)
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── models/          # Entity classes
│   │       ├── repository/      # JPA repositories
│   │       ├── service/         # Business logic
│   │       ├── response/        # Custom response handler
│   │       └── HotelBookingApplication.java
│   └── resources/
│       ├── application.properties
│       └── static/              # (Future frontend assets)
└── test/                        # Test cases
```

## 🌟 Features
- Hotel Search: Find hotels by city, rating, or amenities
- Room Booking: Reserve rooms with check-in/check-out dates
- Booking Management: Create, update, cancel, and view bookings
- Admin Dashboard: Manage hotels, rooms, and bookings (CRUD operations)
- Pagination & Sorting: Efficient data retrieval for large datasets
- Response Handling: Consistent JSON responses with status codes
- Cache: Implemented using Redis

## 🛠 Tech Stack
- **Backend:** Spring Boot
- **Database:** MySQL
- **Cache Server:** Redis
- **API Documentation:** Swagger
- **Build Tool:** Maven
- **Other Libraries:** Lombok, ModelMapper, Hibernate/JPA

## 📌 Requirements
- Java 17 or later
- Spring Boot 3.x
- MySQL Database (or any relational database)
- Maven for dependency management

## ⚙️ Setup and Installation
### 1. Clone the repository
```
git clone https://github.com/Kumar-anurag23/hotel-booking-system.git
cd hotel-booking-system
```

### 2. Setup MySQL Database
Create a MySQL database named **hotel_booking_db** and configure the database connection in `application.properties`.

### 3. Install Dependencies
Use Maven to install the necessary dependencies.

### 4. Run the Application
Use Maven to start the application.

## 🚀 API Endpoints
### Booking Controller (/booking)
- **POST** `/create` - Create new booking
- **PUT** `/update/{id}` - Update booking by ID
- **GET** `/getall` - Get all bookings (paginated)
- **GET** `/bookingId/{id}` - Get booking by ID
- **POST** `/{id}/cancel` - Cancel booking
- **GET** `/hotel/{hotelId}/status/{status}/date/{checkInDate}` - Get bookings by status & date
- **DELETE** `/deletebyid/{id}` - Delete booking

### Hotel Controller (/hotel)
- **POST** `/create` - Add new hotel
- **GET** `/find/{city}` - Find hotels by city
- **GET** `/getall` - Get all hotels (paginated & sorted)
- **GET** `/findbyid/{id}` - Get hotel by ID
- **PUT** `/update/{id}` - Update hotel details
- **DELETE** `/delete/{id}` - Delete hotel

### Room Controller (/room)
- **POST** `/create` - Add new room
- **PUT** `/update/{id}` - Update room details
- **DELETE** `/delete/{id}` - Delete room
- **GET** `/getid/{id}` - Get room by ID
- **GET** `/roomgetbyhid/{hotelId}` - Get all rooms in a hotel

## 📧 Contact
Email: akggupta543216789@gmail.com
GitHub: [Kumar-anurag23](https://github.com/Kumar-anurag23)

## 📝 Sample Requests
### Create Booking (POST /booking/create)
```json
{
  "userId": 1,
  "hotelId": 1,
  "roomId": 101,
  "checkInDate": "2023-12-15",
  "checkOutDate": "2023-12-20",
  "totalPrice": 500.00,
  "status": "CONFIRMED"
}
```

### Add Hotel (POST /hotel/create)
```json
{
  "name": "Grand Hotel",
  "address": "123 Main St",
  "city": "New York",
  "description": "Luxury 5-star hotel",
  "rating": 4.5,
  "amenities": ["Pool", "Spa", "Free WiFi"]
}
```

## 🤝 Contributing
- Fork the repository
- Create a new branch (`git checkout -b feature/your-feature`)
- Commit changes (`git commit -m 'Add some feature'`)
- Push to branch (`git push origin feature/your-feature`)
- Open a Pull Request
