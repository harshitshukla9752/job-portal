CREATE DATABASE IF NOT EXISTS jobportal;

USE jobportal;

DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS jobs;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE jobs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    company_name VARCHAR(255),
    location VARCHAR(255),
    description TEXT,
    posted_by_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (posted_by_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE applications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    applicant_id BIGINT NOT NULL,
    applied_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (applicant_id) REFERENCES users(id) ON DELETE CASCADE
);
