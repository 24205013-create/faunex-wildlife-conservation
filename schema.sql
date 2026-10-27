-- Faunex Wildlife Conservation Schema
-- Created for experiential learning course

-- 1. Users Table (Security)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255)
);

-- 2. Species Table
CREATE TABLE species (
    species_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    common_name VARCHAR(255) NOT NULL,
    scientific_name VARCHAR(255),
    conservation_status VARCHAR(100),
    avg_lifespan INT,
    diet_status VARCHAR(100)
);

-- 3. Habitats Table
CREATE TABLE habitats (
    habitat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    habitat_name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    area_size DOUBLE,
    climate_type VARCHAR(100),
    protection_level VARCHAR(100)
);

-- 4. Animals Table
CREATE TABLE animals (
    animal_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tag_number VARCHAR(100),
    gender VARCHAR(20),
    age INT,
    health_status VARCHAR(100),
    species_id BIGINT,
    habitat_id BIGINT,
    FOREIGN KEY (species_id) REFERENCES species(species_id),
    FOREIGN KEY (habitat_id) REFERENCES habitats(habitat_id)
);

-- 5. Rangers Table
CREATE TABLE rangers (
    ranger_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    `rank` VARCHAR(100),
    join_date DATE,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 6. Visitors Table
CREATE TABLE visitors (
    visitor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact VARCHAR(255),
    purpose VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 7. Visit Records Table
CREATE TABLE visit_records (
    visit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visit_date DATETIME,
    remarks TEXT,
    visitor_id BIGINT,
    habitat_id BIGINT,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id),
    FOREIGN KEY (habitat_id) REFERENCES habitats(habitat_id)
);

-- 8. Incidents Table
CREATE TABLE incidents (
    incident_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    incident_type VARCHAR(255),
    `incident_date` DATETIME,
    `incident_status` VARCHAR(100),
    location VARCHAR(255),
    description TEXT,
    animal_id BIGINT,
    ranger_id BIGINT,
    habitat_id BIGINT,
    FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
    FOREIGN KEY (ranger_id) REFERENCES rangers(ranger_id),
    FOREIGN KEY (habitat_id) REFERENCES habitats(habitat_id)
);
