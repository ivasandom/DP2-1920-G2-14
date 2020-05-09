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
INSERT INTO "PUBLIC"."CLIENTS"("ID","BIRTH_DATE","DOCUMENT","DOCUMENT_TYPE","EMAIL","FIRST_NAME","LAST_NAME","REGISTRATION_DATE","HEALTH_CARD_NUMBER","HEALTH_INSURANCE","USERNAME", "STRIPE_ID")
VALUES (1, '1992-02-02', '28334456', 1, 'pepegotera@gmail.com', 'Pepe', 'Gotera', '2020-03-12', '00001', 'Adeslas', 'pepegotera', 'cus_HFLSuDf4wEoVn7');

-- Cliente 2
INSERT INTO users(username,password,enabled) VALUES ('elenanito','elenanito',TRUE);
INSERT INTO authorities VALUES ('elenanito','client');
INSERT INTO "PUBLIC"."CLIENTS"("ID","BIRTH_DATE","DOCUMENT","DOCUMENT_TYPE","EMAIL","FIRST_NAME","LAST_NAME","REGISTRATION_DATE","HEALTH_CARD_NUMBER","HEALTH_INSURANCE","USERNAME", "STRIPE_ID")
VALUES (2, '1982-03-02', '27334465', 1, 'elenanito@gmail.com', 'Elena', 'Nito', '2020-01-03', '00002', 'Santander', 'elenanito', 'cus_HEy02hbtV90LuK');

-- Cliente 3
INSERT INTO users(username,password,enabled) VALUES ('miguelperez','miguelperez',TRUE);
INSERT INTO authorities VALUES ('miguelperez','client');
INSERT INTO "PUBLIC"."CLIENTS"("ID","BIRTH_DATE","DOCUMENT","DOCUMENT_TYPE","EMAIL","FIRST_NAME","LAST_NAME","REGISTRATION_DATE","HEALTH_CARD_NUMBER","HEALTH_INSURANCE","USERNAME", "STRIPE_ID")
VALUES (3, '1993-05-07', '45334465', 1, 'migper@gmail.com', 'Miguel', 'Perez', '2020-02-23', '00003', 'Santander', 'miguelperez', 'cus_HFLDYXSMwBp20d');

 
--INSERT INTO payment_methods(id, token, client_id) VALUES (1,'test_tok', 1);
 
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
INSERT INTO centers(id, address) VALUES (1, 'Sevilla');
INSERT INTO centers(id, address) VALUES (2, 'Cadiz');
-- Types
INSERT INTO types VALUES (1, 'checking');
INSERT INTO types VALUES (2, 'analisis');
INSERT INTO types VALUES (3, 'illness consultation');
INSERT INTO types VALUES (4, 'consultation for prescription issuance');
INSERT INTO types VALUES (5, 'vaccination');
INSERT INTO types VALUES (6, 'periodic consultation');
INSERT INTO types VALUES (7, 'another case');

INSERT INTO diagnosis (id, date, description) VALUES (1, '2020-02-02', 'description 1');
INSERT INTO diagnosis (id, date, description) VALUES (2, '2020-02-12', 'description 2');
INSERT INTO diagnosis (id, date, description) VALUES (3, '2020-02-20', 'description 3');
INSERT INTO diagnosis (id, date, description) VALUES (4, '2020-01-03', 'description 4');
INSERT INTO diagnosis (id, date, description) VALUES (5, '2020-02-07', 'description 5');
INSERT INTO diagnosis (id, date, description) VALUES (6, '2020-03-05', 'description 6');
INSERT INTO diagnosis (id, date, description) VALUES (7, '2020-03-14', 'description 7');

INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    1, 1, 'professional1', 'Guillermo', 'Díaz', 'guillermodiaz@gmail.com', '13232123M', 1, '123123122-F'
);
INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    2, 1, 'professional2', 'John', 'Wick', 'john.wick@gmail.com', '23232121M', 1, '233123122-F'
);
INSERT INTO professionals (center_id, specialty_id, username, first_name, last_name, email, document, document_type, collegiate_number) VALUES (
    1, 3, 'professional3', 'Julio', 'Maldonado', 'julio.maldonado@gmail.com', '43232412J', 1, '413123122-K'
);

-- Medicines
INSERT INTO medicines (id, name, price) VALUES (1, 'ibuprofeno', '10.0');
INSERT INTO medicines (id, name, price) VALUES (2, 'paracetamol', '9.0');
INSERT INTO medicines (id, name, price) VALUES (3, 'AAS-500-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (4, 'AAS-100-100-Mg-30-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (5, 'ABACAT-1000-4-10-Mg-10-Sobres-Granulado-Solucion-Oral', '20.0');
INSERT INTO medicines (id, name, price) VALUES (6, 'ABACAVIR-LAMIVUDINA-ACCORD-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (7, 'ABACAVIR-LAMIVUDINA-AMNEAL-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (8, 'ABACAVIR-LAMIVUDINA-AUROVITAS-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (9, 'ABACAVIR-LAMIVUDINA-DR-REDDYS-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (10, 'ABACAVIR-LAMIVUDINA-GLENMARK-EFG-600-300-Mg-30-Compr-Rec-blister-Pvd-pvdc-al-', '20.0');
INSERT INTO medicines (id, name, price) VALUES (11, 'BACTIL-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (12, 'BACTIL-FORTE-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (13, 'BACTROBAN-20-Mg-g-Pomada-15-G', '20.0');
INSERT INTO medicines (id, name, price) VALUES (14, 'BACTROBAN-20-Mg-g-Pomada-30-G', '20.0');
INSERT INTO medicines (id, name, price) VALUES (15, 'BACTROBAN-20-Mg-g-Pomada-Nasal-3-G', '20.0');
INSERT INTO medicines (id, name, price) VALUES (16, 'BACTROBAN-NASAL-20-MG-G-POMADA-NASAL-3-G', '20.0');
INSERT INTO medicines (id, name, price) VALUES (17, 'BALANCE-1-5%-GLUCOSA-1-25-MM-CALCIO-Sol-Intraperitoneal-4-Bolsas-3-L-sleep-Safe-', '20.0');
INSERT INTO medicines (id, name, price) VALUES (18, 'BALANCE-1-5%-GLUCOSA-1-25-MM-CALCIO-Sol-Intraperitoneal-4-Bolsas-2-5-L-stay-Safe-', '20.0');
INSERT INTO medicines (id, name, price) VALUES (19, 'CABERGOLINA-TEVA-EFG-2-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (20, 'CABERGOLINA-TEVA-EFG-1-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (21, 'CABERGOLINA-TEVA-EFG-0-5-Mg-2-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (22, 'CABERGOLINA-TEVA-EFG-0-5-Mg-8-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (23, 'CABERGOLINA-TEVA-EFG-0-5-MG-0-5-MG-2-COMPRIMIDOS', '20.0');
INSERT INTO medicines (id, name, price) VALUES (24, 'CABERGOLINA-TEVA-EFG-0-5-MG-0-5-MG-8-COMPRIMIDOS', '20.0');
INSERT INTO medicines (id, name, price) VALUES (25, 'CABOMETYX-20-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (26, 'CABOMETYX-40-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (27, 'DABONAL-20-Mg-28-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (28, 'DABONAL-5-Mg-60-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (29, 'DABONAL-PLUS-20-12-5-Mg-28-Comprimidos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (30, 'DACARBAZINA-MEDAC-1000-Mg-1-Vial-Polvo', '20.0');
INSERT INTO medicines (id, name, price) VALUES (31, 'DACARBAZINA-MEDAC-500-Mg-1-Vial-Polvo', '20.0');
INSERT INTO medicines (id, name, price) VALUES (32, 'DACEPTON-EFG-5-Mg-ml-5-Viales-20-Ml', '20.0');
INSERT INTO medicines (id, name, price) VALUES (33, 'DACEPTON-EFG-10-Mg-ml-10-Ampollas-5-Ml', '20.0');
INSERT INTO medicines (id, name, price) VALUES (34, 'DACEPTON-EFG-5-MG-ML-1-VIAL-20-ML', '20.0');
INSERT INTO medicines (id, name, price) VALUES (35, 'EBASTEL-1-Mg-ml-Solucion-Oral-120-Ml', '20.0');
INSERT INTO medicines (id, name, price) VALUES (36, 'EBASTEL-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (37, 'EBASTEL-FLAS-10-Mg-20-Liofilizados-Orales', '20.0');
INSERT INTO medicines (id, name, price) VALUES (38, 'EBASTEL-FORTE-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (39, 'EBASTEL-FORTE-FLAS-20-Mg-20-Liofilizados-Orales', '20.0');
INSERT INTO medicines (id, name, price) VALUES (40, 'EBASTINA-ALPROFARMA-EFG-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (41, 'EBASTINA-ALTER-EFG-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (id, name, price) VALUES (42, 'EBASTINA-ALTER-EFG-20-Mg-20-Comprimidos-Recubiertos', '20.0');

-- Deseases
INSERT INTO deseases (id, name) VALUES (1, 'Acidez de estómago');
INSERT INTO deseases (id, name) VALUES (2, 'Acné');
INSERT INTO deseases (id, name) VALUES (3, 'Adenoma hipofisiario');
INSERT INTO deseases (id, name) VALUES (4, 'Aerofagia');
INSERT INTO deseases (id, name) VALUES (5, 'Aftas bucales');
INSERT INTO deseases (id, name) VALUES (6, 'Agorafobia');
INSERT INTO deseases (id, name) VALUES (7, 'Alergia');
INSERT INTO deseases (id, name) VALUES (8, 'Alergia al látex');
INSERT INTO deseases (id, name) VALUES (9, 'Balanitis');
INSERT INTO deseases (id, name) VALUES (10, 'Bartolinitis');
INSERT INTO deseases (id, name) VALUES (11, 'Botulismo');
INSERT INTO deseases (id, name) VALUES (12, 'Bronquiectasias');
INSERT INTO deseases (id, name) VALUES (13, 'Bronquitis');
INSERT INTO deseases (id, name) VALUES (14, 'Bruxismo');
INSERT INTO deseases (id, name) VALUES (15, 'Bulimia');
INSERT INTO deseases (id, name) VALUES (16, 'Bullying');
INSERT INTO deseases (id, name) VALUES (17, 'Callos');
INSERT INTO deseases (id, name) VALUES (18, 'Cáncer');
INSERT INTO deseases (id, name) VALUES (19, 'Cáncer de cabeza y cuello');
INSERT INTO deseases (id, name) VALUES (20, 'Cáncer de colon');
INSERT INTO deseases (id, name) VALUES (21, 'Cáncer de cuello de útero');
INSERT INTO deseases (id, name) VALUES (22, 'Cáncer de estómago');
INSERT INTO deseases (id, name) VALUES (23, 'Cáncer de faringe');
INSERT INTO deseases (id, name) VALUES (24, 'Cáncer de hígado');
INSERT INTO deseases (id, name) VALUES (25, 'Demencia');
INSERT INTO deseases (id, name) VALUES (26, 'Dengue');
INSERT INTO deseases (id, name) VALUES (27, 'Depresión');
INSERT INTO deseases (id, name) VALUES (28, 'Dermatitis atópica');
INSERT INTO deseases (id, name) VALUES (29, 'Dermatitis del pañal');
INSERT INTO deseases (id, name) VALUES (30, 'Dermatológicas');
INSERT INTO deseases (id, name) VALUES (31, 'Derrame pleural');
INSERT INTO deseases (id, name) VALUES (32, 'Desprendimiento de retina');
INSERT INTO deseases (id, name) VALUES (33, 'Ébola');
INSERT INTO deseases (id, name) VALUES (34, 'Eccema');
INSERT INTO deseases (id, name) VALUES (35, 'Edema Pulmonar');
INSERT INTO deseases (id, name) VALUES (36, 'ELA (esclerosis lateral amiotrófica)');
INSERT INTO deseases (id, name) VALUES (37, 'Embolia pulmonar');
INSERT INTO deseases (id, name) VALUES (38, 'Encefalitis');
INSERT INTO deseases (id, name) VALUES (39, 'Encefalopatía hepática');
INSERT INTO deseases (id, name) VALUES (40, 'Endocarditis');
INSERT INTO deseases (id, name) VALUES (41, 'Factores de riesgo cardiovascular');
INSERT INTO deseases (id, name) VALUES (42, 'Faringitis');
INSERT INTO deseases (id, name) VALUES (43, 'Faringoamigdalitis');
INSERT INTO deseases (id, name) VALUES (44, 'Fascitis plantar');
INSERT INTO deseases (id, name) VALUES (45, 'Fenilcetonuria');
INSERT INTO deseases (id, name) VALUES (46, 'Fibromialgia');
INSERT INTO deseases (id, name) VALUES (47, 'Fibrosis pulmonar');
INSERT INTO deseases (id, name) VALUES (48, 'Fibrosis pulmonar idiopática');
INSERT INTO deseases (id, name) VALUES (49, 'Gases y flatulencia');
INSERT INTO deseases (id, name) VALUES (50, 'Gastritis');
INSERT INTO deseases (id, name) VALUES (51, 'Gastroenteritis ');
INSERT INTO deseases (id, name) VALUES (52, 'Ginecológicas');
INSERT INTO deseases (id, name) VALUES (53, 'Glaucoma');
INSERT INTO deseases (id, name) VALUES (54, 'Golpe de calor ');
INSERT INTO deseases (id, name) VALUES (55, 'Gonorrea');
INSERT INTO deseases (id, name) VALUES (56, 'Gota');
INSERT INTO deseases (id, name) VALUES (57, 'Hemocromatosis');
INSERT INTO deseases (id, name) VALUES (58, 'Hemofilia');
INSERT INTO deseases (id, name) VALUES (59, 'Hemorragias ginecológicas');
INSERT INTO deseases (id, name) VALUES (60, 'Hemorroides');
INSERT INTO deseases (id, name) VALUES (61, 'Hepatitis A');
INSERT INTO deseases (id, name) VALUES (62, 'Hepatitis B');
INSERT INTO deseases (id, name) VALUES (63, 'Hepatitis C');
INSERT INTO deseases (id, name) VALUES (64, 'Hernia discal');
INSERT INTO deseases (id, name) VALUES (65, 'Ictus');
INSERT INTO deseases (id, name) VALUES (66, 'Impétigo');
INSERT INTO deseases (id, name) VALUES (67, 'Impotencia/ disfunción eréctil');
INSERT INTO deseases (id, name) VALUES (68, 'Incontinencia urinaria');
INSERT INTO deseases (id, name) VALUES (69, 'Infarto de miocardio');
INSERT INTO deseases (id, name) VALUES (70, 'Infección urinaria o cistitis');
INSERT INTO deseases (id, name) VALUES (71, 'Insomnio');
INSERT INTO deseases (id, name) VALUES (72, 'Insuficiencia cardiaca');
INSERT INTO deseases (id, name) VALUES (73, 'Juanetes');
INSERT INTO deseases (id, name) VALUES (74, 'Legionella');
INSERT INTO deseases (id, name) VALUES (75, 'Leishmaniasis');
INSERT INTO deseases (id, name) VALUES (76, 'Lepra');
INSERT INTO deseases (id, name) VALUES (77, 'Leucemia');
INSERT INTO deseases (id, name) VALUES (78, 'Linfoma');
INSERT INTO deseases (id, name) VALUES (79, 'Lipotimia');
INSERT INTO deseases (id, name) VALUES (80, 'Listeriosis');
INSERT INTO deseases (id, name) VALUES (81, 'Litiasis renal');
INSERT INTO deseases (id, name) VALUES (82, 'Malaria');
INSERT INTO deseases (id, name) VALUES (83, 'Medicina Interna');
INSERT INTO deseases (id, name) VALUES (84, 'Melanoma');
INSERT INTO deseases (id, name) VALUES (85, 'Melanoma metastásico');
INSERT INTO deseases (id, name) VALUES (86, 'Melasma');
INSERT INTO deseases (id, name) VALUES (87, 'Meningitis');
INSERT INTO deseases (id, name) VALUES (88, 'Mieloma múltiple');
INSERT INTO deseases (id, name) VALUES (89, 'Migrañas');
INSERT INTO deseases (id, name) VALUES (90, 'Narcolepsia');
INSERT INTO deseases (id, name) VALUES (91, 'Neumonía');
INSERT INTO deseases (id, name) VALUES (92, 'Neumotórax');
INSERT INTO deseases (id, name) VALUES (93, 'Obesidad');
INSERT INTO deseases (id, name) VALUES (94, 'Oftalmológicas');
INSERT INTO deseases (id, name) VALUES (95, 'Ojo seco');
INSERT INTO deseases (id, name) VALUES (96, 'Ojo vago');
INSERT INTO deseases (id, name) VALUES (97, 'Orquitis');
INSERT INTO deseases (id, name) VALUES (98, 'Ortorexia');
INSERT INTO deseases (id, name) VALUES (99, 'Orzuelo');
INSERT INTO deseases (id, name) VALUES (100, 'Osteoporosis');
INSERT INTO deseases (id, name) VALUES (101, 'Pancreatitis');
INSERT INTO deseases (id, name) VALUES (102, 'Paperas (parotiditis)');
INSERT INTO deseases (id, name) VALUES (103, 'Parkinson');
INSERT INTO deseases (id, name) VALUES (104, 'Pericarditis');
INSERT INTO deseases (id, name) VALUES (105, 'Pie de atleta');
INSERT INTO deseases (id, name) VALUES (106, 'Pies cavos');
INSERT INTO deseases (id, name) VALUES (107, 'Pies planos');
INSERT INTO deseases (id, name) VALUES (108, 'Pies zambos');
INSERT INTO deseases (id, name) VALUES (109, 'Rabia');
INSERT INTO deseases (id, name) VALUES (110, 'Rectocele');
INSERT INTO deseases (id, name) VALUES (111, 'Retinoblastoma');
INSERT INTO deseases (id, name) VALUES (112, 'Retinopatía diabética');
INSERT INTO deseases (id, name) VALUES (113, 'Rinitis');
INSERT INTO deseases (id, name) VALUES (114, 'Rosácea');
INSERT INTO deseases (id, name) VALUES (115, 'Rotavirus');
INSERT INTO deseases (id, name) VALUES (116, 'Rubéola');
INSERT INTO deseases (id, name) VALUES (117, 'Salmonelosis');
INSERT INTO deseases (id, name) VALUES (118, 'Sarampión');
INSERT INTO deseases (id, name) VALUES (119, 'Sarcoidosis');
INSERT INTO deseases (id, name) VALUES (120, 'Sarcoma');
INSERT INTO deseases (id, name) VALUES (121, 'Sepsis');
INSERT INTO deseases (id, name) VALUES (122, 'Sífilis');
INSERT INTO deseases (id, name) VALUES (123, 'Silicosis');
INSERT INTO deseases (id, name) VALUES (124, 'Síndrome de Burnout');
INSERT INTO deseases (id, name) VALUES (125, 'Tendinitis');
INSERT INTO deseases (id, name) VALUES (126, 'Tétanos');
INSERT INTO deseases (id, name) VALUES (127, 'Tortícolis');
INSERT INTO deseases (id, name) VALUES (128, 'Tos ferina');
INSERT INTO deseases (id, name) VALUES (129, 'Toxoplasmosis');
INSERT INTO deseases (id, name) VALUES (130, 'Trastorno bipolar');
INSERT INTO deseases (id, name) VALUES (131, 'Trastorno de conducta del sueño en fase REM');
INSERT INTO deseases (id, name) VALUES (132, 'Trastorno de menstruación');
INSERT INTO deseases (id, name) VALUES (133, 'Úlcera gastroduodenal');
INSERT INTO deseases (id, name) VALUES (134, 'Uñas encarnadas');
INSERT INTO deseases (id, name) VALUES (135, 'Urológicas');
INSERT INTO deseases (id, name) VALUES (136, 'Urticaria');
INSERT INTO deseases (id, name) VALUES (137, 'Vaginitis o vulvovaginitis');
INSERT INTO deseases (id, name) VALUES (138, 'Vaginosis bacteriana');
INSERT INTO deseases (id, name) VALUES (139, 'Varicela');
INSERT INTO deseases (id, name) VALUES (140, 'Varices');
INSERT INTO deseases (id, name) VALUES (141, 'Vasculitis');
INSERT INTO deseases (id, name) VALUES (142, 'Vegetaciones');
INSERT INTO deseases (id, name) VALUES (143, 'Vértigo');
INSERT INTO deseases (id, name) VALUES (144, 'Vigorexia');

-- Diagnosis -> Deseases/Medicines
INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (1,2);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (1,1);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (2,2);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (2,1);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (3,5);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (3,1);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (4,6);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (4,7);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (5,21);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (5,100);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (6,34);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (6,136);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (7,40);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (7,78);


-- Para probar citas cogidas 2020-12-12
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-04', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-04', '08:15', 'test',2); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-12-12', '08:30', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 1, 3, '2020-12-12', '08:45', 'test',1); 

-- Citas futuras para 4 meses
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-05', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-06', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-07', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-08', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-09', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-10', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-11', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-12', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-13', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-14', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-15', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-16', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-17', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-18', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-19', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-20', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-21', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-22', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-23', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-24', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-25', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-26', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-27', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-28', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-29', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-30', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-05-31', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-01', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-02', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-03', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-04', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-05', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-06', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-07', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-08', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-09', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-10', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-11', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-12', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-13', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-14', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-15', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-16', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-17', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-18', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-19', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-20', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-21', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-22', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-23', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-24', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-25', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-26', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-27', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-28', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-29', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-06-30', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-01', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-02', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-03', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-04', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-05', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-06', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-07', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-08', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-09', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-10', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-11', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-12', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-13', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-14', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-15', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-16', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-17', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-18', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-19', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-20', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-21', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-22', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-23', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-24', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-25', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-26', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-27', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-28', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-29', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-30', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-07-31', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-01', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-02', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-03', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-04', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-05', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-06', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-07', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-08', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-09', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-10', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-11', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-12', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-13', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-14', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-15', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-16', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-17', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-18', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-19', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-20', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-21', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-22', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-23', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-24', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-25', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-26', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-27', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-28', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-29', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-30', '08:00', 'test',1);
INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, '2020-08-31', '08:00', 'test',1);

-- Para las citas antiguas (pasadas, historial)
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, 1, '2020-02-02', '08:00', 'consultation',1); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 2, 1, 3, '2020-02-12', '08:15', 'consultation',2); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 3, 1, 1, '2020-02-20', '08:30', 'consultation',1); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 4, 1, 3, '2020-01-03', '08:45', 'consultation',1); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 5, 1, 3, '2020-02-07', '09:00', 'consultation',1);
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 6, 1, 3, '2020-03-05', '08:30', 'consultation',1);
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 7, 1, 3, '2020-04-09', '08:30', 'consultation',1);

--INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (2, 1, 3, 3, '2020-12-12', '08:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:00'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:15'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:30'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '09:45'); 
-- INSERT INTO appointments (client_id, center_id, specialty_id, professional_id, date, start_time) VALUES (1, 1, 1, 1, '2020-12-12', '10:00'); 
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

