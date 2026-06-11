# 🛒 E-Commerce Backend Application

A complete Spring Boot based E-Commerce Backend Application that provides authentication, product management, cart management, wishlist, order processing, payment integration, review management, and admin functionalities.

---

## 🚀 Features

### Authentication & Authorization

* User Registration
* User Login with JWT Authentication
* Role Based Access Control (ADMIN / USER)
* Secure API Access using Spring Security

### Product Management

* Add Product
* Update Product
* Delete Product
* Get Product Details
* Search Products
* Category Based Product Listing

### Cart Management

* Add Product to Cart
* Update Quantity
* Remove Product from Cart
* View Cart Items

### Wishlist Management

* Add to Wishlist
* Remove from Wishlist
* View Wishlist

### Order Management

* Checkout Order
* View User Orders
* Get Order Details
* Cancel Order
* Update Order Status
* Ship Order
* Deliver Order

### Address Management

* Add Address
* Update Address
* Delete Address
* View Addresses

### Payment Integration

* Razorpay Payment Gateway Integration
* Payment Verification
* Payment Status Tracking

### Review System

* Add Product Review
* View Product Reviews

### Email Notifications

* Order Confirmation Email
* Order Delivery Notification

### API Documentation

* Swagger UI Integration

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* MySQL

### Authentication

* JWT (JSON Web Token)

### Payment

* Razorpay

### Build Tool

* Maven

### Testing

* JUnit 5
* Mockito

### Documentation

* Swagger / OpenAPI

### Containerization

* Docker

### CI/CD

* GitHub Actions

---

## 📂 Project Structure

```text
src
├── controller
├── service
├── repository
├── entities
├── dtos
├── config
├── exception
├── utils
└── test
```

---

## ⚙️ Setup Instructions

### Clone Repository

```bash
git clone https://github.com/mdsanau/New-Ecommerce-Application.git
cd New-Ecommerce-Application
```

### Configure Properties

Create:

```properties
application.properties
```

Example:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

jwt.secret=

razorpay.key.id=
razorpay.key.secret=

spring.mail.username=
spring.mail.password=
```

---

## ▶️ Run Application

### Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🐳 Run with Docker

### Build Image

```bash
docker build -t ecommerce-app .
```

### Run Container

```bash
docker run -p 8081:8080 ecommerce-app
```

Swagger:

```text
http://localhost:8081/swagger-ui/index.html
```

---

## 🧪 Run Tests

```bash
mvn test
```

---

## 🔄 GitHub Actions

This project includes a GitHub Actions workflow that automatically:

* Builds the project
* Runs unit tests
* Validates code changes on every push

---

## 📖 API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 👨‍💻 Author

Md Sanaullah

Java Backend Developer

Skills:

* Java
* Spring Boot
* Microservices
* REST APIs
* JWT Security
* Docker
* MySQL
* AWS (Learning)
* CI/CD
* GitHub Actions

---

## 📄 License

This project is created for learning, portfolio, and demonstration purposes.
