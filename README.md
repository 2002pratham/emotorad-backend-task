# Emotorad Backend Task: Identity Reconciliation

## 🛠️ About the Project
This project is part of an internship challenge to build a backend service for consolidating user contact information across multiple purchases. The service processes JSON payloads with email and phoneNumber fields and dynamically links them to maintain a unified customer identity.

## 🌟 Getting Started

### Fork and Clone
1. **Fork this Repository**: Click the Fork button on the top right.
2. **Clone Your Fork**:
   ```bash
   git clone https://github.com/<2002pratham>/emotorad-backend-task.git
   cd emotorad-backend-task
## 🛠️ Set Up Your Environment

Ensure you have the following installed:

- Java 21+
- Maven 3.9.9
- IDE (e.g., IntelliJ IDEA, Eclipse) or terminal for running the app.
## 🛠️ Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for backend development.
- **H2 Database**: In-memory database for quick testing.
- **JUnit and Mockito**: For unit testing.

## 🛠️ Build the Project:
```bash
mvn clean install
```
## 🛠️ Run the Application:
```bash
mvn spring-boot:run
```

## 📝 Features

- **Dynamic Contact Consolidation**: Handles multiple email and phone identifiers seamlessly.
- **Primary/Secondary Contacts**: Assigns primary or secondary precedence to contacts based on reconciliation logic.
- **Robust Error Handling**: Includes custom exceptions and validation mechanisms.
- **Testing and In-Memory DB**: Pre-configured H2 database for seamless development and testing.

## 🚀 Usage

### Test the Endpoint
Use tools like Postman or curl to interact with the `/contacts/identify` endpoint.

**Endpoint:**
```plaintext
POST http://localhost:8080/contacts/identify
```
### Sample Request:
```json
{
  "email": "example@email.com",
  "phoneNumber": "1234567890"
}
```
### Expected Response:
```json
{
  "primaryContactId": 1,
  "emails": ["example@email.com"],
  "phoneNumbers": ["1234567890"],
  "secondaryContactIds": []
}
```
## 📂 File Structure

- **Controller**: Manages API requests and responses.
- **DTOs**: Defines the structure for request/response objects.
- **Service**: Contains the business logic.
- **Model**: Defines the database schema.
- **Repository**: Interfaces for database interactions.
- **Exception Handling**: Global error handling classes.
