package com.example.appointment.specification;

import com.example.appointment.filter.PatientFilter;
import com.example.appointment.model.entity.human.Patient;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {
    public static Specification<Patient> withFilter(PatientFilter filter) {
        return Specification.where(firstNameContains(filter.getFirstName()))
                .and(lastNameContains(filter.getLastName()))
                .and(emailContains(filter.getEmail()))
                .and(bloodTypeEquals(filter.getBloodType()))
                .and(dateOfBirthGreaterThanOrEqual(filter.getDateOfBirthFrom()))
                .and(dateOfBirthLessThanOrEqual(filter.getDateOfBirthTo()));
    }

    private static Specification<Patient> firstNameContains(String firstName) {
        return (root, query, cb) ->
                firstName == null || firstName.isBlank() ? null :
                        cb.like(cb.lower(root.get("fName")), "%" + firstName.toLowerCase() + "%");
    }

    private static Specification<Patient> lastNameContains(String lastName) {
        return (root, query, cb) ->
                lastName == null || lastName.isBlank() ? null :
                        cb.like(cb.lower(root.get("lName")), "%" + lastName.toLowerCase() + "%");
    }

    private static Specification<Patient> emailContains(String email) {
        return (root, query, cb) ->
                email == null || email.isBlank() ? null :
                        cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    private static Specification<Patient> phoneContains(String phone) {
        return (root, query, cb) ->
                phone == null || phone.isBlank() ? null :
                        cb.like(cb.lower(root.get("phoneNumber")), "%" + phone.toLowerCase() + "%");
    }

    private static Specification<Patient> bloodTypeEquals(String bloodType) {
        return (root, query, cb) ->
                bloodType == null || bloodType.isBlank() ? null :
                        cb.equal(cb.lower(root.get("bloodType")), bloodType.toLowerCase());
    }

    private static Specification<Patient> dateOfBirthGreaterThanOrEqual(java.time.LocalDate from) {
        return (root, query, cb) -> from == null ? null : cb.greaterThanOrEqualTo(root.get("dateOfBirth"), from);
    }

    private static Specification<Patient> dateOfBirthLessThanOrEqual(java.time.LocalDate to) {
        return (root, query, cb) -> to == null ? null : cb.lessThanOrEqualTo(root.get("dateOfBirth"), to);
    }
}
