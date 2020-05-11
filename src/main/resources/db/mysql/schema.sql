create table appointments (
    id integer not null auto_increment,
    date date,
    reason varchar(255),
    start_time time,
    status varchar(32) default 'PENDING',
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (client_id) REFEREBCES clients(id),
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (professional_id) REFERENCES professionals(id),
    FOREIGN KEY (receipt_id) REFERENCES receipts(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    FOREIGN KEY (type_id) REFERENCES types(id),
    primary key (id)
) engine=InnoDB;

create table authorities (
    username varchar(255) not null,
    authority varchar(255),
    primary key (username)
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
    FOREIGN KEY (receipt_id) REFERENCES receipts(id),
    primary key (id)
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
    FOREIGN KEY (username) REFERENCES users(username),
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

create table diagnosis_deseases (
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (desease_id) REFERENCES deseases(id),
    primary key (diagnosis_id, desease_id)
) engine=InnoDB;

create table diagnosis_medicines (
    FOREIGN KEY (diagnosis_id) REFERENCES diagnosis(id),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id),
) engine=InnoDB;

create table medicines (
    id integer not null auto_increment,
    name varchar(200),
    price double precision not null,
    primary key (id)
) engine=InnoDB;

create table payment_methods (
    id integer not null auto_increment,
    brand varchar(255),
    token varchar(255),
    FOREIGN KEY (client_id) REFERENCES clients(id),
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
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    FOREIGN KEY (username) REFERENCE users(username),
    primary key (id)
) engine=InnoDB;

create table receipts (
    id integer not null auto_increment,
    price double precision not null,
    status integer,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    primary key (id)
) engine=InnoDB;

create table schedule (
    id integer not null auto_increment,
    date date,
    end_time time,
    start_time time,
    FOREIGN KEY (center_id) REFERENCES centers(id),
    FOREIGN KEY (professional_id) REFERENCES professionals(id),
    primary key (id)
) engine=InnoDB;

create table specialties (
    id integer not null auto_increment,
    name varchar(200),
    primary key (id)
) engine=InnoDB;

create table transactions (
    id integer not null auto_increment,
    amount double precision not null,
    status varchar(255),
    success bit,
    token varchar(255),
    type integer,
    FOREIGN KEY (receipt_id) REFERENCES receipts(id),
    primary key (id)
) engine=InnoDB;

create table types (
    id integer not null auto_increment,
    name varchar(200),
    primary key (id)
) engine=InnoDB;

create table users (
    username varchar(255) not null,
    enabled bit not null,
    password varchar(60),
    primary key (username)
) engine=InnoDB;