🍕 Food Ordering System - Backend
A modern, full-stack food ordering system built with Spring Boot, React, and MySQL. This repository contains the complete backend implementation with robust APIs for user management, restaurant operations, food ordering, and payment processing.

🚀 Features
User Authentication & Authorization - JWT-based secure authentication

Restaurant Management - Complete CRUD operations for restaurants

Food Catalog - Menu management with categories and ingredients

Shopping Cart - Full cart functionality with item management

Order Processing - Complete order lifecycle management

Payment Integration - Stripe payment processing ready

Security - Spring Security with role-based access control

🛠 Tech Stack
Backend Framework: Spring Boot 3.x

Security: Spring Security + JWT

Database: MySQL

ORM: Spring Data JPA

Build Tool: Maven

Java Version: 17+

Documentation: Springdoc OpenAPI (Swagger)

📋 Prerequisites
Before running this application, ensure you have the following installed:

Java 17 or higher

Maven 3.6+

MySQL 8.0+

Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)

🗂 Project Structure
```
src/main/java/com/xuwei/
├── config/          # Security and JWT configuration
├── controller/      # REST API controllers
├── service/         # Business logic layer
├── repository/      # Data access layer
├── model/           # Entity classes
├── dto/            # Data Transfer Objects
├── request/         # Request payload classes
├── response/        # Response payload classes
└── exception/       # Custom exception handling
```

🔐 API Endpoints
```

Authentication
Method	Endpoint	Description
POST	/api/auth/signup	User registration
POST	/api/auth/login	User login
User Management
Method	Endpoint	Description
GET	/api/user/profile	Get user profile
Restaurant Operations
Method	Endpoint	Description
GET	/api/restaurants	Get all restaurants
GET	/api/restaurants/{id}	Get restaurant by ID
POST	/api/admin/restaurants	Create restaurant (Admin)
PUT	/api/admin/restaurants/{id}	Update restaurant (Admin)
Food Management
Method	Endpoint	Description
GET	/api/foods	Get all foods
GET	/api/foods/{id}	Get food by ID
GET	/api/foods/restaurant/{restaurantId}	Get foods by restaurant
POST	/api/admin/foods	Create food (Admin)
Shopping Cart
Method	Endpoint	Description
GET	/api/cart	Get user cart
PUT	/api/cart/add	Add item to cart
PUT	/api/cart/cart-item/update	Update cart item quantity
DELETE	/api/cart/cart-item/remove/{cartItemId}	Remove item from cart
PUT	/api/cart/clear	Clear entire cart
Order Management
Method	Endpoint	Description
POST	/api/orders	Create new order
GET	/api/orders/user	Get user orders
GET	/api/orders/{orderId}	Get order by ID
PUT	/api/orders/{orderId}	Update order status
PUT	/api/orders/{orderId}/cancel	Cancel order
GET	/api/orders/restaurant/{restaurantId}	Get restaurant orders

```

