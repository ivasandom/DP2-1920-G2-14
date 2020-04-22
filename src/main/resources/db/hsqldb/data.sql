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
-- Types
INSERT INTO types VALUES (1, 'checking');
INSERT INTO types VALUES (2, 'analisis');
INSERT INTO types VALUES (3, 'illness consultation');
INSERT INTO types VALUES (4, 'consultation for prescription issuance');
INSERT INTO types VALUES (5, 'vaccination');
INSERT INTO types VALUES (6, 'periodic consultation');
INSERT INTO types VALUES (7, 'another case');

INSERT INTO diagnosis (id, date, description) VALUES (1, '2020-02-02', 'description 1');
INSERT INTO diagnosis (id, date, description) VALUES (2, '2020-02-02', 'description 2');
INSERT INTO diagnosis (id, date, description) VALUES (3, '2020-02-02', 'description 3');
INSERT INTO diagnosis (id, date, description) VALUES (4, '2020-02-02', 'description 4');

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
INSERT INTO medicines (name, price) VALUES ('AAS-500-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('AAS-100-100-Mg-30-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAT-1000-4-10-Mg-10-Sobres-Granulado-Solucion-Oral', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAVIR-LAMIVUDINA-ACCORD-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAVIR-LAMIVUDINA-AMNEAL-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAVIR-LAMIVUDINA-AUROVITAS-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAVIR-LAMIVUDINA-DR-REDDYS-EFG-600-300-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('ABACAVIR-LAMIVUDINA-GLENMARK-EFG-600-300-Mg-30-Compr-Rec-blister-Pvd-pvdc-al-', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTIL-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTIL-FORTE-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTROBAN-20-Mg-g-Pomada-15-G', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTROBAN-20-Mg-g-Pomada-30-G', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTROBAN-20-Mg-g-Pomada-Nasal-3-G', '20.0');
INSERT INTO medicines (name, price) VALUES ('BACTROBAN-NASAL-20-MG-G-POMADA-NASAL-3-G', '20.0');
INSERT INTO medicines (name, price) VALUES ('BALANCE-1-5%-GLUCOSA-1-25-MM-CALCIO-Sol-Intraperitoneal-4-Bolsas-3-L-sleep-Safe-', '20.0');
INSERT INTO medicines (name, price) VALUES ('BALANCE-1-5%-GLUCOSA-1-25-MM-CALCIO-Sol-Intraperitoneal-4-Bolsas-2-5-L-stay-Safe-', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-2-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-1-Mg-20-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-0-5-Mg-2-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-0-5-Mg-8-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-0-5-MG-0-5-MG-2-COMPRIMIDOS', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABERGOLINA-TEVA-EFG-0-5-MG-0-5-MG-8-COMPRIMIDOS', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABOMETYX-20-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('CABOMETYX-40-Mg-30-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('DABONAL-20-Mg-28-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('DABONAL-5-Mg-60-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('DABONAL-PLUS-20-12-5-Mg-28-Comprimidos', '20.0');
INSERT INTO medicines (name, price) VALUES ('DACARBAZINA-MEDAC-1000-Mg-1-Vial-Polvo', '20.0');
INSERT INTO medicines (name, price) VALUES ('DACARBAZINA-MEDAC-500-Mg-1-Vial-Polvo', '20.0');
INSERT INTO medicines (name, price) VALUES ('DACEPTON-EFG-5-Mg-ml-5-Viales-20-Ml', '20.0');
INSERT INTO medicines (name, price) VALUES ('DACEPTON-EFG-10-Mg-ml-10-Ampollas-5-Ml', '20.0');
INSERT INTO medicines (name, price) VALUES ('DACEPTON-EFG-5-MG-ML-1-VIAL-20-ML', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTEL-1-Mg-ml-Solucion-Oral-120-Ml', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTEL-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTEL-FLAS-10-Mg-20-Liofilizados-Orales', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTEL-FORTE-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTEL-FORTE-FLAS-20-Mg-20-Liofilizados-Orales', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTINA-ALPROFARMA-EFG-20-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTINA-ALTER-EFG-10-Mg-20-Comprimidos-Recubiertos', '20.0');
INSERT INTO medicines (name, price) VALUES ('EBASTINA-ALTER-EFG-20-Mg-20-Comprimidos-Recubiertos', '20.0');

-- Deseases
INSERT INTO deseases (id, name) VALUES (1, 'Acidez de estómago');
INSERT INTO deseases (id, name) VALUES (2, 'Acné');
INSERT INTO deseases (name) VALUES ('Adenoma hipofisiario');
INSERT INTO deseases (name) VALUES ('Aerofagia');
INSERT INTO deseases (name) VALUES ('Aftas bucales');
INSERT INTO deseases (name) VALUES ('Agorafobia');
INSERT INTO deseases (name) VALUES ('Alergia');
INSERT INTO deseases (name) VALUES ('Alergia al látex');
INSERT INTO deseases (name) VALUES ('Balanitis');
INSERT INTO deseases (name) VALUES ('Bartolinitis');
INSERT INTO deseases (name) VALUES ('Botulismo');
INSERT INTO deseases (name) VALUES ('Bronquiectasias');
INSERT INTO deseases (name) VALUES ('Bronquitis');
INSERT INTO deseases (name) VALUES ('Bruxismo');
INSERT INTO deseases (name) VALUES ('Bulimia');
INSERT INTO deseases (name) VALUES ('Bullying');
INSERT INTO deseases (name) VALUES ('Callos');
INSERT INTO deseases (name) VALUES ('Cáncer');
INSERT INTO deseases (name) VALUES ('Cáncer de cabeza y cuello');
INSERT INTO deseases (name) VALUES ('Cáncer de colon');
INSERT INTO deseases (name) VALUES ('Cáncer de cuello de útero');
INSERT INTO deseases (name) VALUES ('Cáncer de estómago');
INSERT INTO deseases (name) VALUES ('Cáncer de faringe');
INSERT INTO deseases (name) VALUES ('Cáncer de hígado');
INSERT INTO deseases (name) VALUES ('Demencia');
INSERT INTO deseases (name) VALUES ('Dengue');
INSERT INTO deseases (name) VALUES ('Depresión');
INSERT INTO deseases (name) VALUES ('Dermatitis atópica');
INSERT INTO deseases (name) VALUES ('Dermatitis del pañal');
INSERT INTO deseases (name) VALUES ('Dermatológicas');
INSERT INTO deseases (name) VALUES ('Derrame pleural');
INSERT INTO deseases (name) VALUES ('Desprendimiento de retina');
INSERT INTO deseases (name) VALUES ('Ébola');
INSERT INTO deseases (name) VALUES ('Eccema');
INSERT INTO deseases (name) VALUES ('Edema Pulmonar');
INSERT INTO deseases (name) VALUES ('ELA (esclerosis lateral amiotrófica)');
INSERT INTO deseases (name) VALUES ('Embolia pulmonar');
INSERT INTO deseases (name) VALUES ('Encefalitis');
INSERT INTO deseases (name) VALUES ('Encefalopatía hepática');
INSERT INTO deseases (name) VALUES ('Endocarditis');
INSERT INTO deseases (name) VALUES ('Factores de riesgo cardiovascular');
INSERT INTO deseases (name) VALUES ('Faringitis');
INSERT INTO deseases (name) VALUES ('Faringoamigdalitis');
INSERT INTO deseases (name) VALUES ('Fascitis plantar');
INSERT INTO deseases (name) VALUES ('Fenilcetonuria');
INSERT INTO deseases (name) VALUES ('Fibromialgia');
INSERT INTO deseases (name) VALUES ('Fibrosis pulmonar');
INSERT INTO deseases (name) VALUES ('Fibrosis pulmonar idiopática');
INSERT INTO deseases (name) VALUES ('Gases y flatulencia');
INSERT INTO deseases (name) VALUES ('Gastritis');
INSERT INTO deseases (name) VALUES ('Gastroenteritis ');
INSERT INTO deseases (name) VALUES ('Ginecológicas');
INSERT INTO deseases (name) VALUES ('Glaucoma');
INSERT INTO deseases (name) VALUES ('Golpe de calor ');
INSERT INTO deseases (name) VALUES ('Gonorrea');
INSERT INTO deseases (name) VALUES ('Gota');
INSERT INTO deseases (name) VALUES ('Hemocromatosis');
INSERT INTO deseases (name) VALUES ('Hemofilia');
INSERT INTO deseases (name) VALUES ('Hemorragias ginecológicas');
INSERT INTO deseases (name) VALUES ('Hemorroides');
INSERT INTO deseases (name) VALUES ('Hepatitis A');
INSERT INTO deseases (name) VALUES ('Hepatitis B');
INSERT INTO deseases (name) VALUES ('Hepatitis C');
INSERT INTO deseases (name) VALUES ('Hernia discal');
INSERT INTO deseases (name) VALUES ('Ictus');
INSERT INTO deseases (name) VALUES ('Impétigo');
INSERT INTO deseases (name) VALUES ('Impotencia/ disfunción eréctil');
INSERT INTO deseases (name) VALUES ('Incontinencia urinaria');
INSERT INTO deseases (name) VALUES ('Infarto de miocardio');
INSERT INTO deseases (name) VALUES ('Infección urinaria o cistitis');
INSERT INTO deseases (name) VALUES ('Insomnio');
INSERT INTO deseases (name) VALUES ('Insuficiencia cardiaca');
INSERT INTO deseases (name) VALUES ('Juanetes');
INSERT INTO deseases (name) VALUES ('Legionella');
INSERT INTO deseases (name) VALUES ('Leishmaniasis');
INSERT INTO deseases (name) VALUES ('Lepra');
INSERT INTO deseases (name) VALUES ('Leucemia');
INSERT INTO deseases (name) VALUES ('Linfoma');
INSERT INTO deseases (name) VALUES ('Lipotimia');
INSERT INTO deseases (name) VALUES ('Listeriosis');
INSERT INTO deseases (name) VALUES ('Litiasis renal');
INSERT INTO deseases (name) VALUES ('Malaria');
INSERT INTO deseases (name) VALUES ('Medicina Interna');
INSERT INTO deseases (name) VALUES ('Melanoma');
INSERT INTO deseases (name) VALUES ('Melanoma metastásico');
INSERT INTO deseases (name) VALUES ('Melasma');
INSERT INTO deseases (name) VALUES ('Meningitis');
INSERT INTO deseases (name) VALUES ('Mieloma múltiple');
INSERT INTO deseases (name) VALUES ('Migrañas');
INSERT INTO deseases (name) VALUES ('Narcolepsia');
INSERT INTO deseases (name) VALUES ('Neumonía');
INSERT INTO deseases (name) VALUES ('Neumotórax');
INSERT INTO deseases (name) VALUES ('Obesidad');
INSERT INTO deseases (name) VALUES ('Oftalmológicas');
INSERT INTO deseases (name) VALUES ('Ojo seco');
INSERT INTO deseases (name) VALUES ('Ojo vago');
INSERT INTO deseases (name) VALUES ('Orquitis');
INSERT INTO deseases (name) VALUES ('Ortorexia');
INSERT INTO deseases (name) VALUES ('Orzuelo');
INSERT INTO deseases (name) VALUES ('Osteoporosis');
INSERT INTO deseases (name) VALUES ('Pancreatitis');
INSERT INTO deseases (name) VALUES ('Paperas (parotiditis)');
INSERT INTO deseases (name) VALUES ('Parkinson');
INSERT INTO deseases (name) VALUES ('Pericarditis');
INSERT INTO deseases (name) VALUES ('Pie de atleta');
INSERT INTO deseases (name) VALUES ('Pies cavos');
INSERT INTO deseases (name) VALUES ('Pies planos');
INSERT INTO deseases (name) VALUES ('Pies zambos');
INSERT INTO deseases (name) VALUES ('Rabia');
INSERT INTO deseases (name) VALUES ('Rectocele');
INSERT INTO deseases (name) VALUES ('Retinoblastoma');
INSERT INTO deseases (name) VALUES ('Retinopatía diabética');
INSERT INTO deseases (name) VALUES ('Rinitis');
INSERT INTO deseases (name) VALUES ('Rosácea');
INSERT INTO deseases (name) VALUES ('Rotavirus');
INSERT INTO deseases (name) VALUES ('Rubéola');
INSERT INTO deseases (name) VALUES ('Salmonelosis');
INSERT INTO deseases (name) VALUES ('Sarampión');
INSERT INTO deseases (name) VALUES ('Sarcoidosis');
INSERT INTO deseases (name) VALUES ('Sarcoma');
INSERT INTO deseases (name) VALUES ('Sepsis');
INSERT INTO deseases (name) VALUES ('Sífilis');
INSERT INTO deseases (name) VALUES ('Silicosis');
INSERT INTO deseases (name) VALUES ('Síndrome de Burnout');
INSERT INTO deseases (name) VALUES ('Tendinitis');
INSERT INTO deseases (name) VALUES ('Tétanos');
INSERT INTO deseases (name) VALUES ('Tortícolis');
INSERT INTO deseases (name) VALUES ('Tos ferina');
INSERT INTO deseases (name) VALUES ('Toxoplasmosis');
INSERT INTO deseases (name) VALUES ('Trastorno bipolar');
INSERT INTO deseases (name) VALUES ('Trastorno de conducta del sueño en fase REM');
INSERT INTO deseases (name) VALUES ('Trastorno de menstruación');
INSERT INTO deseases (name) VALUES ('Úlcera gastroduodenal');
INSERT INTO deseases (name) VALUES ('Uñas encarnadas');
INSERT INTO deseases (name) VALUES ('Urológicas');
INSERT INTO deseases (name) VALUES ('Urticaria');
INSERT INTO deseases (name) VALUES ('Vaginitis o vulvovaginitis');
INSERT INTO deseases (name) VALUES ('Vaginosis bacteriana');
INSERT INTO deseases (name) VALUES ('Varicela');
INSERT INTO deseases (name) VALUES ('Varices');
INSERT INTO deseases (name) VALUES ('Vasculitis');
INSERT INTO deseases (name) VALUES ('Vegetaciones');
INSERT INTO deseases (name) VALUES ('Vértigo');
INSERT INTO deseases (name) VALUES ('Vigorexia');

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (1,2);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (1,1);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (2,1);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (2,2);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (3,1);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (3,2);

INSERT INTO diagnosis_medicines (diagnosis_id, medicine_id) VALUES (4,2);
INSERT INTO diagnosis_deseases (diagnosis_id, desease_id) VALUES (4,1);


-- Para probar citas cogidas 2020-12-12
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 1, 1, 1, '2020-12-12', '08:00', 'test',1); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (1, 1, 2, 1, 3, '2020-12-12', '08:15', 'test',2); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 3, 3, 3, '2020-12-12', '08:30', 'test',1); 
INSERT INTO appointments (client_id, center_id, diagnosis_id, specialty_id, professional_id, date, start_time,reason,type_id) VALUES (2, 1, 4, 3, 3, '2020-12-12', '08:30', 'test',1); 

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

