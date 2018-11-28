CREATE TABLE Car
(
  CarId INT NOT NULL,
  model CHAR,
  color CHAR,
  numberOfplace INT NOT NULL,
  PRIMARY KEY (CarId)
);

CREATE TABLE City
(
  name CHAR NOT NULL
);

CREATE TABLE University
(
  name CHAR NOT NULL
);

CREATE TABLE Driver
(
  DriverId INT NOT NULL,
  phone INT NOT NULL,
  CarId INT NOT NULL,
  PRIMARY KEY (DriverId),
  FOREIGN KEY (CarId) REFERENCES Car(CarId),
  UNIQUE (phone),
  UNIQUE (CarId)
);

CREATE TABLE User
(
  UserId INT NOT NULL,
  password CHAR NOT NULL,
  name CHAR NOT NULL,
  year_of_birth INT,
  mail CHAR NOT NULL,
  userName CHAR NOT NULL,
  city CHAR,
  university CHAR,
  DriverId INT NOT NULL,
  PRIMARY KEY (UserId),
  FOREIGN KEY (DriverId) REFERENCES Driver(DriverId),
  UNIQUE (password),
  UNIQUE (mail),
  UNIQUE (DriverId)
);

CREATE TABLE Drive
(
  startingPoint CHAR NOT NULL,
  destination CHAR NOT NULL,
  date DATE NOT NULL,
  note CHAR,
  price INT,
  freePlace INT NOT NULL,
  DriverId INT NOT NULL,
  FOREIGN KEY (DriverId) REFERENCES Driver(DriverId),
  UNIQUE (DriverId)
);

CREATE TABLE Ride
(
  date DATE NOT NULL,
  startingPoint CHAR NOT NULL,
  destination CHAR NOT NULL,
  UserId INT NOT NULL,
  DriverId INT NOT NULL,
  FOREIGN KEY (UserId) REFERENCES User(UserId),
  FOREIGN KEY (DriverId) REFERENCES Driver(DriverId),
  UNIQUE (UserId),
  UNIQUE (DriverId)
);
