-- 1. Patients (10 Records)
INSERT INTO patient (f_name, l_name, email, phone_number, gender, blood_type, allergy, date_of_birth, created_at, updated_at) VALUES
                                                                                                                                  ('Alice', 'Smith', 'alice.s@example.com', '1234567890', 'F', 'A+', 'Peanuts', '1995-05-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Bob', 'Jones', 'bob.j@example.com', '0987654321', 'M', 'O-', 'None', '1988-11-22', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Charlie', 'Brown', 'charlie.b@test.com', '1112223333', 'M', 'B+', 'Dust', '1992-02-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Diana', 'Prince', 'diana.p@test.com', '4445556666', 'F', 'AB-', 'Pollen', '1985-07-30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Edward', 'Norton', 'ed.n@test.com', '7778889999', 'M', 'O+', 'Penicillin', '1975-12-05', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Fiona', 'Gallagher', 'fiona.g@test.com', '2223334444', 'F', 'A-', 'Latex', '1990-03-21', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('George', 'Miller', 'george.m@test.com', '5556667777', 'M', 'B-', 'Shellfish', '1982-08-14', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Hannah', 'Abbott', 'hannah.a@test.com', '8889990000', 'F', 'O+', 'None', '1998-01-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Ian', 'Wright', 'ian.w@test.com', '3334445555', 'M', 'A+', 'Dairy', '1968-06-18', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                  ('Jenny', 'Sloane', 'jenny.s@test.com', '6667778888', 'F', 'AB+', 'None', '2000-10-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. Receptionists (2 Records)
INSERT INTO receptionist (f_name, l_name, email, phone_number, gender, username, password, role, desk_number, created_at, updated_at) VALUES
                                                                                                                                          ('Sarah', 'Miller', 'sarah.m@clinic.com', '5551112222', 'F', 'sarah_admin', '{noop}pass123', 'RECEPTIONIST', 'Desk-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                          ('Mark', 'Wilson', 'mark.w@clinic.com', '5552223333', 'M', 'mark_admin', '{noop}pass123', 'RECEPTIONIST', 'Desk-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. Doctors (10 Records)
INSERT INTO doctor (f_name, l_name, email, phone_number, gender, username, password, role, specialization, license_number, start_working_hour, ending_working_hour, created_at, updated_at) VALUES
                                                                                                                                                                                                ('John', 'Doe', 'dr.john@clinic.com', '55500001', 'M', 'dr_john', '{noop}docpass', 'DOCTOR', 'Cardiology', 'LIC-001', '08:00:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Emily', 'White', 'dr.emily@clinic.com', '55500002', 'F', 'dr_emily', '{noop}docpass', 'DOCTOR', 'Pediatrics', 'LIC-002', '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Robert', 'Black', 'dr.rob@clinic.com', '55500003', 'M', 'dr_rob', '{noop}docpass', 'DOCTOR', 'Orthopedics', 'LIC-003', '08:00:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Lisa', 'Green', 'dr.lisa@clinic.com', '55500004', 'F', 'dr_lisa', '{noop}docpass', 'DOCTOR', 'Dermatology', 'LIC-004', '10:00:00', '18:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Kevin', 'Spacey', 'dr.kevin@clinic.com', '55500005', 'M', 'dr_kevin', '{noop}docpass', 'DOCTOR', 'Neurology', 'LIC-005', '07:00:00', '15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Angela', 'Bassett', 'dr.angela@clinic.com', '55500006', 'F', 'dr_angela', '{noop}docpass', 'DOCTOR', 'General', 'LIC-006', '08:00:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Tom', 'Hardy', 'dr.tom@clinic.com', '55500007', 'M', 'dr_tom', '{noop}docpass', 'DOCTOR', 'Psychiatry', 'LIC-007', '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Sandra', 'Bullock', 'dr.sandra@clinic.com', '55500008', 'F', 'dr_sandra', '{noop}docpass', 'DOCTOR', 'Radiology', 'LIC-008', '08:00:00', '16:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Victor', 'Garber', 'dr.victor@clinic.com', '55500009', 'M', 'dr_victor', '{noop}docpass', 'DOCTOR', 'Oncology', 'LIC-009', '09:00:00', '17:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                                                ('Monica', 'Geller', 'dr.monica@clinic.com', '55500010', 'F', 'dr_monica', '{noop}docpass', 'DOCTOR', 'Surgery', 'LIC-010', '07:00:00', '15:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Drugs (10 Records)
INSERT INTO drug (name, description, price, category, quantity, created_at, updated_at) VALUES
                                                                                            ('Amoxicillin', 'Antibiotic', 15.00, 'Antibiotics', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Paracetamol', 'Painkiller', 5.00, 'Analgesics', 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Ibuprofen', 'Anti-inflammatory', 8.00, 'NSAID', 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Metformin', 'Diabetes medication', 12.00, 'Antidiabetic', 300, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Lisinopril', 'Blood pressure', 20.00, 'ACE Inhibitor', 120, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Atorvastatin', 'Cholesterol', 25.00, 'Statin', 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Albuterol', 'Inhaler for asthma', 30.00, 'Bronchodilator', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Omeprazole', 'Heartburn', 10.00, 'PPI', 180, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Sertraline', 'Antidepressant', 22.00, 'SSRI', 90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                            ('Azithromycin', 'Antibiotic', 18.00, 'Antibiotics', 110, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Medical Records (10 Records)
INSERT INTO medical_record (diagnosis, note, issue_date, patient_id, created_at, updated_at) VALUES
                                                                                                 ('Hypertension', 'Monitor BP weekly', '2023-01-10', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Type 2 Diabetes', 'Low sugar diet recommended', '2023-02-15', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Seasonal Allergy', 'Prescribed antihistamines', '2023-03-20', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Anxiety', 'Referred to therapy', '2023-04-05', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Asthma', 'Emergency inhaler provided', '2023-05-12', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Migraine', 'Keep headache diary', '2023-06-18', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Back Pain', 'Physical therapy session 1', '2023-07-22', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Flu', 'Bed rest and fluids', '2023-08-30', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Vitamin D Deficiency', 'Supplements suggested', '2023-09-14', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                 ('Gastritis', 'Avoid spicy food', '2023-10-01', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 6. Appointments (10 Records)
INSERT INTO appointment (appointment_date, appointment_start, appointment_end, status, note, patient_id, doctor_id, created_by_id, created_at, updated_at) VALUES
                                                                                                                                                               ('2023-11-01', '09:00:00', '09:30:00', 3, 'Initial Check', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- COMPLETED
                                                                                                                                                               ('2023-11-01', '10:00:00', '10:30:00', 3, 'Follow up', 2, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    -- COMPLETED
                                                                                                                                                               ('2023-11-01', '11:00:00', '11:30:00', 1, 'Checkup', 3, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),      -- APPROVED
                                                                                                                                                               ('2023-11-02', '09:00:00', '09:30:00', 1, 'Consultation', 4, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- APPROVED
                                                                                                                                                               ('2023-11-02', '10:00:00', '10:30:00', 0, 'Urgent', 5, 5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),       -- PENDING
                                                                                                                                                               ('2023-11-02', '11:00:00', '11:30:00', 3, 'Checkup', 6, 6, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),      -- COMPLETED
                                                                                                                                                               ('2023-11-03', '09:00:00', '09:30:00', 1, 'Skin exam', 7, 7, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),    -- APPROVED
                                                                                                                                                               ('2023-11-03', '10:00:00', '10:30:00', 2, 'Heart rate check', 8, 8, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- CANCELLED
                                                                                                                                                               ('2023-11-03', '11:00:00', '11:30:00', 1, 'Consultation', 9, 9, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- APPROVED
                                                                                                                                                               ('2023-11-04', '09:00:00', '09:30:00', 3, 'Post-op', 10, 10, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);   -- COMPLETED

-- 7. Prescriptions (10 Records)
INSERT INTO prescription (appointment_id, created_at, updated_at) VALUES
                                                                      (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                      (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                      (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                      (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                      (9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Update Appointments to link back to Prescriptions
UPDATE appointment SET prescription_id = 1 WHERE id = 1;
UPDATE appointment SET prescription_id = 2 WHERE id = 2;
UPDATE appointment SET prescription_id = 3 WHERE id = 3;
UPDATE appointment SET prescription_id = 4 WHERE id = 4;
UPDATE appointment SET prescription_id = 5 WHERE id = 5;
UPDATE appointment SET prescription_id = 6 WHERE id = 6;
UPDATE appointment SET prescription_id = 7 WHERE id = 7;
UPDATE appointment SET prescription_id = 8 WHERE id = 8;
UPDATE appointment SET prescription_id = 9 WHERE id = 9;
UPDATE appointment SET prescription_id = 10 WHERE id = 10;

-- 8. Drug Intakes (10 Records)
INSERT INTO drug_intake (drug_id, prescription_id, quantity, dosage, instruction, created_at, updated_at) VALUES
                                                                                                              (1, 1, 1, '500mg', 'After meals', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (2, 2, 2, '1000mg', 'Before sleep', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (3, 3, 1, '400mg', 'As needed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (4, 4, 1, '500mg', 'With breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (5, 5, 1, '10mg', 'Morning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (6, 6, 1, '20mg', 'Night', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (7, 7, 2, '2 puffs', 'Every 4 hours', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (8, 8, 1, '20mg', 'Before breakfast', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (9, 9, 1, '50mg', 'Morning', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                              (10, 10, 3, '250mg', '3 days course', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 9. Invoices (10 Records)
-- PaymentStatus: 0:PENDING, 1:PAID, 2:CANCELLED
-- PaymentMethod: 0:CASH, 1:CARD, 2:INSURANCE
INSERT INTO invoice (total_amount, doctor_fee, discount, issue_date, payment_status, payment_method, patient_id, doctor_id, appointment_id, created_at, updated_at) VALUES
                                                                                                                                                                        (100.00, 80.00, 0, '2023-11-01', 1, 0, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (120.00, 100.00, 5.0, '2023-11-01', 1, 1, 2, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (90.00, 80.00, 0, '2023-11-01', 1, 0, 3, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (150.00, 120.00, 10.0, '2023-11-02', 1, 2, 4, 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (80.00, 80.00, 0, '2023-11-02', 0, 0, 5, 5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (110.00, 100.00, 0, '2023-11-02', 1, 0, 6, 6, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (200.00, 180.00, 20.0, '2023-11-03', 1, 1, 7, 7, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (95.00, 80.00, 0, '2023-11-03', 1, 0, 8, 8, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (130.00, 110.00, 5.0, '2023-11-03', 0, 1, 9, 9, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                                                                                        (300.00, 250.00, 0, '2023-11-04', 1, 1, 10, 10, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
