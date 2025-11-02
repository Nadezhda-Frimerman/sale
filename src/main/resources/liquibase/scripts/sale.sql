-- liquibase formatted sql

-- changeset dlok:1
CREATE TYPE ROLE AS ENUM ('USER','ADMIN');

CREATE TABLE user_data (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    role ROLE NOT NULL DEFAULT 'USER',
    image VARCHAR(500) NOT NULL
);

CREATE TABLE ads(
    id SERIAL PRIMARY KEY,
    title VARCHAR(32) NOT NULL,
    price INT NOT NULL CHECK (price >= 0),
    description VARCHAR(64) NOT NULL,
    image VARCHAR(500) NOT NULL,
    user_id INT NOT NULL REFERENCES user_data (id)
);

CREATE TABLE comments(
    id SERIAL PRIMARY KEY,
    text VARCHAR(64) NOT NULL,
    created_at BIGINT NOT NULL,
    user_id INT NOT NULL REFERENCES user_data (id),
    ad_id INT NOT NULL REFERENCES ads (id)
);

-- changeset dlok:2
CREATE TABLE pictures(
    id SERIAL PRIMARY KEY,
    file_path TEXT NOT NULL,
    file_size BIGINT NOT NULL,
    media_type VARCHAR(100) NOT NULL,
    data OID NOT NULL
);