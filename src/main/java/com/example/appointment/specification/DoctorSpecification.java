package com.example.appointment.specification;

import com.example.appointment.filter.DoctorFilter;
import com.example.appointment.model.entity.human.Doctor;
import org.springframework.data.jpa.domain.Specification;



    public class DoctorSpecification {

        public static Specification<Doctor> withFilter(DoctorFilter filter) {

            return Specification.where(firstNameContains(filter.getFirstName()))
                    .and(lastNameContains(filter.getLastName()))
                    .and(licenseNumberEquals(filter.getLicenseNumber()))
                    .and(specializationEquals(filter.getSpecialization()));
        }

        private static Specification<Doctor> firstNameContains(String firstName) {
            return (root, query, cb) ->
                    firstName == null || firstName.isBlank() ? null :
                            cb.like(cb.lower(root.get("fName")), "%" + firstName.toLowerCase() + "%");
        }

        private static Specification<Doctor> lastNameContains(String lastName) {
            return (root, query, cb) ->
                    lastName == null || lastName.isBlank() ? null :
                            cb.like(cb.lower(root.get("lName")), "%" + lastName.toLowerCase() + "%");
        }

        private static Specification<Doctor> licenseNumberEquals(String license) {
            return (root, query, cb) ->
                    license == null || license.isBlank() ? null :
                            cb.equal(root.get("licenseNumber"), license);
        }

        private static Specification<Doctor> specializationEquals(String specialization) {
            return (root, query, cb) ->
                    specialization == null || specialization.isBlank() ? null :
                            cb.equal(cb.lower(root.get("specialization")), specialization.toLowerCase());
        }
    }


