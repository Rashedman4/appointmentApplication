package com.example.appointment.specification;

import com.example.appointment.filter.DrugFilter;
import com.example.appointment.model.entity.Drug;
import org.springframework.data.jpa.domain.Specification;

public class DrugSpecification {
    public static Specification<Drug> withFilter(DrugFilter filter) {
        return Specification.where(nameContains(filter.getName()))
                .and(categoryEquals(filter.getCategory()))
                .and(priceGreaterThanOrEqual(filter.getMinPrice()))
                .and(priceLessThanOrEqual(filter.getMaxPrice()))
                .and(quantityGreaterThanOrEqual(filter.getMinQuantity()))
                .and(quantityLessThanOrEqual(filter.getMaxQuantity()));
    }

    private static Specification<Drug> nameContains(String name) {
        return (root, query, cb) ->
                name == null || name.isBlank() ? null :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<Drug> categoryEquals(String category) {
        return (root, query, cb) ->
                category == null || category.isBlank() ? null :
                        cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }

    private static Specification<Drug> priceGreaterThanOrEqual(java.math.BigDecimal minPrice) {
        return (root, query, cb) -> minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Drug> priceLessThanOrEqual(java.math.BigDecimal maxPrice) {
        return (root, query, cb) -> maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Drug> quantityGreaterThanOrEqual(Integer minQuantity) {
        return (root, query, cb) -> minQuantity == null ? null : cb.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
    }

    private static Specification<Drug> quantityLessThanOrEqual(Integer maxQuantity) {
        return (root, query, cb) -> maxQuantity == null ? null : cb.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
    }
}
