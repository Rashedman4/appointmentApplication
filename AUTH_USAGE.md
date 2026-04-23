# Auth changes added

## Public endpoints
- `POST /api/v1/auth/register/doctor`
- `POST /api/v1/auth/register/receptionist`
- `POST /api/v1/auth/authenticate`

## Example doctor registration
```json
{
  "fName": "Ali",
  "lName": "Saleh",
  "email": "ali.doctor@clinic.com",
  "phoneNumber": "0791234567",
  "gender": "MALE",
  "username": "ali_doctor",
  "password": "password123",
  "specialization": "Cardiology",
  "licenseNumber": "LIC-999",
  "startWorkingHour": "08:00",
  "endingWorkingHour": "16:00"
}
```

## Example receptionist registration
```json
{
  "fName": "Sara",
  "lName": "Yousef",
  "email": "sara.reception@clinic.com",
  "phoneNumber": "0797654321",
  "gender": "FEMALE",
  "username": "sara_reception",
  "password": "password123",
  "deskNumber": "Desk-03"
}
```

## Example login
```json
{
  "username": "ali_doctor",
  "password": "password123"
}
```

## Authorization header
Use the returned token in requests:
`Authorization: Bearer <token>`

## Role protection now applied
- `DOCTOR`: `/api/medical-records/**`, `/api/prescriptions/**`
- `RECEPTIONIST`: `/api/patients/**`, `/api/appointments/**`, `/api/v1/invoices/**`, `/api/receptionists/**`
- `DOCTOR` or `RECEPTIONIST`: read doctors and drugs endpoints
- `RECEPTIONIST`: manages drugs and doctor updates/deletes

## Important note
Seeded users in `data.sql` now use `{noop}` passwords so they can authenticate with the configured delegating password encoder.
Newly registered users are stored with bcrypt.
