package com.flame239.dumbaccountservice.entities.converters;

import com.flame239.dumbaccountservice.entities.CustomerStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CustomerStatusConverter implements AttributeConverter<CustomerStatus, String> {
    @Override
    public String convertToDatabaseColumn(CustomerStatus status) {
        switch (status) {
            case ACTIVE:
                return "A";
            case BLOCKED:
                return "B";
            default:
                throw new IllegalArgumentException("No converter for customer status type: " + status);
        }
    }

    @Override
    public CustomerStatus convertToEntityAttribute(String status) {
        switch (status) {
            case "A":
                return CustomerStatus.ACTIVE;
            case "B":
                return CustomerStatus.BLOCKED;
            default:
                throw new IllegalArgumentException("Unknown customer status in db: " + status);
        }
    }
}
