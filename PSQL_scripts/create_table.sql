CREATE TABLE cars
(
    id           SERIAL PRIMARY KEY,
    brand         VARCHAR(100) NOT NULL,
    model      VARCHAR(100) NOT NULL,
    release_year          INTEGER      NOT NULL,
    license_plate      VARCHAR(10),
    owner_phone VARCHAR(15),
    image TEXT 
);