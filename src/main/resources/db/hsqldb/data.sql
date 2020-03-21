-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('user','password',TRUE);
INSERT INTO authorities VALUES ('user','client');
-- Cliente 1
INSERT INTO users(username,password,enabled) VALUES ('pepegotera','pepegotera',TRUE);
INSERT INTO authorities VALUES ('pepegotera','client');
INSERT INTO "PUBLIC"."CLIENTS"("ID","BIRTH_DATE","DOCUMENT","DOCUMENT_TYPE","EMAIL","FIRST_NAME","LAST_NAME","REGISTRATION_DATE","HEALTH_CARD_NUMBER","HEALTH_INSURANCE","USERNAME")
VALUES (1, '1992-02-02', '28334456', 1, 'pepegotera@gmail.com', 'Pepe', 'Gotera', '2020-03-12', '00001', 'Adeslas', 'pepegotera');

-- Cliente 2
INSERT INTO users(username,password,enabled) VALUES ('elenanito','elenanito',TRUE);
INSERT INTO authorities VALUES ('elenanito','client');
INSERT INTO "PUBLIC"."CLIENTS"("ID","BIRTH_DATE","DOCUMENT","DOCUMENT_TYPE","EMAIL","FIRST_NAME","LAST_NAME","REGISTRATION_DATE","HEALTH_CARD_NUMBER","HEALTH_INSURANCE","USERNAME")
VALUES (2, '1982-03-02', '27334465', 1, 'elenanito@gmail.com', 'Elena', 'Nito', '2020-01-03', '00002', 'Santander', 'elenanito');
 
-- Professional 1
INSERT INTO users(username,password,enabled) VALUES ('professional1','professional1',TRUE);
INSERT INTO authorities VALUES ('professional1','professional');
-- Professional 2
INSERT INTO users(username,password,enabled) VALUES ('professional2','professional2',TRUE);
INSERT INTO authorities VALUES ('professional2','professional');
-- Professional 3
INSERT INTO users(username,password,enabled) VALUES ('professional3','professional3',TRUE);
INSERT INTO authorities VALUES ('professional3','professional');

-- Specialties
INSERT INTO specialties VALUES (1, 'dermatology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');
-- Centers
INSERT INTO centers(address) VALUES ('Sevilla');
INSERT INTO centers(address) VALUES ('Cadiz');


INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    1, 1, 'professional1', 'Guillermo', 'Díaz', 'guillermodiaz@gmail.com', '13232123M', 1, '123123122-F'
);
INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    2, 1, 'professional2', 'John', 'Wick', 'john.wick@gmail.com', '23232121M', 1, '233123122-F'
);
INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    1, 3, 'professional3', 'Julio', 'Maldonado', 'julio.maldonado@gmail.com', '43232412J', 1, '413123122-K'
);

-- Para probar citas cogidas 2020-12-12
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason) VALUES (1, 1, 1, 1, '2020-12-12', '08:00', 'test'); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason) VALUES (1, 1, 1, 3, '2020-12-12', '08:15', 'test'); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason) VALUES (2, 1, 3, 3, '2020-12-12', '08:30', 'test'); 
--INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (2, 1, 3, 3, '2020-12-12', '08:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '10:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '10:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '10:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '10:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '11:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '11:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '11:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '12:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '12:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '12:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '12:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '13:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '13:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '13:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '14:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '14:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '14:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '15:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '15:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '15:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '15:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '16:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '16:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '16:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '16:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '17:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '17:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '17:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '17:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '18:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '18:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '18:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '19:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '19:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '19:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '19:45'); 

-- INSERT INTO vets VALUES (1, 'James', 'Carter');
-- INSERT INTO vets VALUES (2, 'Helen', 'Leary');
-- INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
-- INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
-- INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
-- INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

-- INSERT INTO specialties VALUES (1, 'radiology');
-- INSERT INTO specialties VALUES (2, 'surgery');
-- INSERT INTO specialties VALUES (3, 'dentistry');

-- INSERT INTO vet_specialties VALUES (2, 1);
-- INSERT INTO vet_specialties VALUES (3, 2);
-- INSERT INTO vet_specialties VALUES (3, 3);
-- INSERT INTO vet_specialties VALUES (4, 2);
-- INSERT INTO vet_specialties VALUES (5, 1);

-- INSERT INTO types VALUES (1, 'cat');
-- INSERT INTO types VALUES (2, 'dog');
-- INSERT INTO types VALUES (3, 'lizard');
-- INSERT INTO types VALUES (4, 'snake');
-- INSERT INTO types VALUES (5, 'bird');
-- INSERT INTO types VALUES (6, 'hamster');

-- INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
-- INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
-- INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
-- INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
-- INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
-- INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
-- INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
-- INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
-- INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
-- INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
-- INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
-- INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

