# Faunex – Wildlife Conservation Management System

Faunex is a full-stack Spring Boot application designed to manage wildlife populations, habitats, incidents, and visitor records.

## 🚀 Setup Instructions

### 1. Prerequisites
*   **Java Development Kit (JDK) 17** or higher.
*   **Apache Maven** installed.
*   **MySQL Server** running.
*   **IDE:** IntelliJ IDEA (Recommended) or VS Code with "Extension Pack for Java".

### 2. Database Setup
1.  Open **MySQL Workbench**.
2.  Run the following command to create the database:
    ```sql
    CREATE DATABASE faunex_db;
    ```
3.  The application is configured to automatically create tables via JPA (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Application Configuration
1.  Open `src/main/resources/application.properties`.
2.  Update the MySQL credentials if different:
    ```properties
    spring.datasource.username=root
    spring.datasource.password=your_password
    ```

### 4. How to Run
1.  **Using Terminal:**
    ```bash
    mvn spring-boot:run
    ```
2.  **Using IDE:**
    *   Find `FaunexApplication.java` in `src/main/java/com/faunex/faunex/`.
    *   Right-click and select **Run 'FaunexApplication'**.

### 5. Sample Login Credentials
The system initializes with these users (see `DataInitializer.java`):
*   **Admin:** `admin` / `admin123`
*   **Ranger:** `ranger` / `ranger123`
*   **Visitor:** `visitor` / `visitor123`

## 📂 Project Structure
*   **`model`**: JPA Entities (Animal, Species, Habitat, etc.).
*   **`repository`**: Spring Data JPA Interfaces.
*   **`service`**: Business logic and Security services.
*   **`controller`**: Web controllers for routing and CRUD.
*   **`config`**: Security and Data initialization.
*   **`templates`**: Thymeleaf HTML files for the frontend.

## 🛠 Features
*   **Authentication:** Secure Login/Register with BCrypt.
*   **Role-Based Access:** 
    *   `ADMIN`: Full access to all modules.
    *   `RANGER`: Manage incidents and view animals.
    *   `VISITOR`: View habitats and book sessions.
*   **Responsive UI:** Styled with Tailwind CSS (Mobile-friendly).
*   **Relational Mapping:** Clean Many-to-One and One-to-Many mappings as per ER diagram.
