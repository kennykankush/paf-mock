CREATE DATABASE IF NOT EXISTS mybnb;

USE mybnb;

CREATE TABLE IF NOT EXISTS acc_occupancy(
    acc_id VARCHAR(10),
    vacancy INT,
    CONSTRAINT pk_acc_id PRIMARY KEY (acc_id)
)

CREATE TABLE IF NOT EXISTS reservations(
    resv_id VARCHAR(8) NOT NULL,
    name VARCHAR(128),
    email VARCHAR(128),
    acc_id VARCHAR(10),
    arrival_date DATE,
    duration INT,
    CONSTRAINT pk_resv_id PRIMARY KEY (resv_id),
    CONSTRAINT fk_acc_id FOREIGN KEY (acc_id) REFERENCES acc_occupancy(acc_id)
    
)