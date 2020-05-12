SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE if exists authorities;
DROP TABLE if exists users;
DROP TABLE if exists centers;
DROP TABLE if exists clients;
DROP TABLE if exists deseases;
DROP TABLE if exists diagnosis;
DROP TABLE if exists medicines;
DROP TABLE if exists diagnosis_deseases;
DROP TABLE if exists diagnosis_medicines;
DROP TABLE if exists payment_methods;
DROP TABLE if exists specialties;
DROP TABLE if exists professionals;
DROP TABLE if exists types;
DROP TABLE if exists schedule;
DROP TABLE if exists appointments;
DROP TABLE if exists receipts;
DROP TABLE if exists bills;
DROP TABLE if exists transactions;
SET FOREIGN_KEY_CHECKS = 1;
create table users (
    username varchar(255) not null,
    enabled bit not null,
    password varchar(60),
    primary key (username)
) engine=InnoDB;

create table authorities (
    username varchar(255) not null,
    authority varchar(255),
    primary key (username)
) engine=InnoDB;


create table centers (
    id integer not null auto_increment,
    name varchar(200),
    address varchar(255),
    primary key (id)
) engine=InnoDB;


create table clients (
    id integer not null auto_increment,
    birth_date datetime,
    document varchar(255),
    document_type integer not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    registration_date datetime,
    health_card_number varchar(255),
    health_insurance varchar(255),
    stripe_id varchar(255),
    username varchar(255) not null,
    FOREIGN KEY (username) REFERENCES users (username),
    primary key (id)
) engine=InnoDB;

create table deseases (
    id integer not null auto_increment,
    name varchar(200),
    primary key (id)
) engine=InnoDB;

create table diagnosis (
    id integer not null auto_increment,
    date date,
    description varchar(255),
    primary key (id)
) engine=InnoDB;

create table medicines (
    id integer not null auto_increment,
    name varchar(200),
    price double precision not null,
    primary key (id)
) engine=InnoDB;


create table diagnosis_deseases (
	diagnosis_id integer not null,
	desease_id integer not null,
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (desease_id) REFERENCES deseases(id),
    primary key (diagnosis_id, desease_id)
) engine=InnoDB;

create table diagnosis_medicines (
	diagnosis_id integer not null,
	medicine_id integer not null,
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id)
) engine=InnoDB;


create table payment_methods (
    id integer not null auto_increment,
    brand varchar(255),
    token varchar(255),
    client_id integer not null,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    primary key (id)
) engine=InnoDB;


create table specialties (
    id integer not null auto_increment,
    name varchar(200),
    primary key (id)
) engine=InnoDB;

create table professionals (
    id integer not null auto_increment,
    birth_date datetime,
    document varchar(255),
    document_type integer not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    registration_date datetime,
    collegiate_number varchar(255),
    center_id integer not null,
    specialty_id integer not null,
    username varchar(255) not null,
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    FOREIGN KEY (username) REFERENCES users(username),
    primary key (id)
) engine=InnoDB;


create table schedule (
    id integer not null auto_increment,
    date date,
    end_time time,
    start_time time,
    center_id integer not null,
    professional_id integer not null,
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (professional_id) REFERENCES professionals(id),
    primary key (id)
) engine=InnoDB;



create table types (
    id integer not null auto_increment,
    name varchar(200),
    primary key (id)
) engine=InnoDB;


create table appointments (
    id integer not null auto_increment,
    date date,
    reason varchar(255),
    start_time time,
    status varchar(32) default 'PENDING',
    center_id integer not null,
    client_id integer not null,
    diagnosis_id integer not null,
    professional_id integer not null,
    receipt_id integer not null,
    specialty_id integer not null,
    type_id integer not null,
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (professional_id) REFERENCES professionals(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    FOREIGN KEY (type_id) REFERENCES types(id),
    primary key (id)
) engine=InnoDB;

create table receipts (
    id integer not null auto_increment,
    price double precision not null,
    status integer,
    appointment_id integer not null,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    primary key (id)
) engine=InnoDB;


create table transactions (
    id integer not null auto_increment,
    amount double precision not null,
    status varchar(255),
    success bit,
    token varchar(255),
    type integer,
    receipt_id integer not null,
    FOREIGN KEY (receipt_id) REFERENCES receipts(id),
    primary key (id)
) engine=InnoDB;


create table bills (
    id integer not null auto_increment,
    name varchar(200),
    document varchar(255),
    final_price double precision not null,
    iva double precision not null,
    last_name varchar(255),
    price double precision not null,
    type_document varchar(255),
    receipt_id integer not null,
    FOREIGN KEY (receipt_id) REFERENCES receipts(id),
    primary key (id)
) engine=InnoDB;

ALTER TABLE appointments ADD CONSTRAINT fk_appointments_receipts FOREIGN KEY (receipt_id) REFERENCES receipts(id);