BEGIN;


CREATE TABLE IF NOT EXISTS public.appointments
(
    appointment_id bigserial NOT NULL,
    appointment_date timestamp(6) without time zone NOT NULL,
    patient_id bigint,
    doctor_id bigint,
    CONSTRAINT appointments_pkey PRIMARY KEY (appointment_id)
);

CREATE TABLE IF NOT EXISTS public.departments
(
    department_id bigserial NOT NULL,
    department_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT departments_pkey PRIMARY KEY (department_id)
);

CREATE TABLE IF NOT EXISTS public.doctors
(
    doctor_id bigserial NOT NULL,
    doctor_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    department_id bigint,
    CONSTRAINT doctors_pkey PRIMARY KEY (doctor_id)
);

CREATE TABLE IF NOT EXISTS public.patients
(
    patient_id bigserial NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(255) COLLATE pg_catalog."default",
    admit_date date NOT NULL,
    doctor_id bigint,
    CONSTRAINT patients_pkey PRIMARY KEY (patient_id),
    CONSTRAINT patients_email_key UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.reports
(
    report_id bigserial NOT NULL,
    report_name character varying(255) COLLATE pg_catalog."default",
    patient_id bigint,
    CONSTRAINT reports_pkey PRIMARY KEY (report_id)
);

ALTER TABLE IF EXISTS public.appointments
    ADD CONSTRAINT appointments_doctor_id_fkey FOREIGN KEY (doctor_id)
    REFERENCES public.doctors (doctor_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.appointments
    ADD CONSTRAINT appointments_patient_id_fkey FOREIGN KEY (patient_id)
    REFERENCES public.patients (patient_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.doctors
    ADD CONSTRAINT doctors_department_id_fkey FOREIGN KEY (department_id)
    REFERENCES public.departments (department_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.patients
    ADD CONSTRAINT patients_doctor_id_fkey FOREIGN KEY (doctor_id)
    REFERENCES public.doctors (doctor_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.reports
    ADD CONSTRAINT reports_patient_id_fkey FOREIGN KEY (patient_id)
    REFERENCES public.patients (patient_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;