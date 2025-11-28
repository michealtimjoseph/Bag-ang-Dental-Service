-- =========================
-- PATIENT TABLE
-- =========================
create table patient (
  patient_id serial primary key,
  patient_fname varchar(50) not null,
  patient_lname varchar(50) not null,
  patient_age integer not null,
  patient_dob date not null,
  patient_email varchar(100),
  patient_phone varchar(15) not null
);

-- =========================
-- DENTIST TABLE
-- =========================
create table dentist (
  dentist_id serial primary key,
  dentist_fname varchar(50) not null,
  dentist_lname varchar(50) not null,
  dentist_specialty varchar(50),
  dentist_phone varchar(15),
  dentist_email varchar(100)
);

-- =========================
-- SERVICES TABLE
-- =========================
create table services (
  service_id serial primary key,
  service_name varchar(100) not null,
  service_cost numeric(10,2) not null
);

-- =========================
-- PATIENT ↔ SERVICES (Many-to-Many)
-- =========================
create table patient_services (
  patient_id integer not null,
  service_id integer not null,
  primary key (patient_id, service_id),
  constraint fk_patient foreign key (patient_id) references patient(patient_id) on update cascade on delete cascade,
  constraint fk_service foreign key (service_id) references services(service_id) on update cascade on delete cascade
);

-- =========================
-- APPOINTMENT TABLE
-- =========================
create table appointment (
  app_id serial primary key,
  app_date date not null,
  app_timestart timestamp with time zone not null,
  app_timeend timestamp with time zone not null,
  patient_id integer not null,
  dentist_id integer not null,
  service_id integer not null,
  app_status varchar(20) default 'Scheduled',
  constraint fk_patient_id foreign key (patient_id) references patient(patient_id) on update cascade on delete restrict,
  constraint fk_dentist_id foreign key (dentist_id) references dentist(dentist_id) on update cascade on delete restrict,
  constraint fk_service_id foreign key (service_id) references services(service_id) on update cascade on delete restrict
);

-- =========================
-- APPOINTMENT INVOICE TABLE
-- =========================
create table appointment_invoice (
  invoice_id serial primary key,
  service_cost numeric(10,2) not null,
  patient_paid numeric(10,2) default 0.00,
  total_due numeric(10,2) not null,
  payment_date date,
  is_paid boolean default false,
  app_id integer not null,
  constraint fk_invoice_app foreign key (app_id) references appointment(app_id) on update cascade on delete restrict
);

-- =========================
-- DENTIST SCHEDULE TABLE
-- =========================
create table schedule (
  schedule_id serial primary key,
  dentist_id integer not null,
  day_of_week varchar(10) not null,
  start_time time not null,
  end_time time not null,
  constraint fk_schedule_dentist foreign key (dentist_id) references dentist(dentist_id) on update cascade on delete cascade
);
