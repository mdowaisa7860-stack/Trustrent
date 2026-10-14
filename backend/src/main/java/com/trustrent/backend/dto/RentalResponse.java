package com.trustrent.backend.dto;

import com.trustrent.backend.entity.Rental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class RentalResponse {
    public UUID id;
    public UUID propertyId;
    public String propertyTitle;
    public String propertyCity;
    public UUID tenantId;
    public String tenantName;
    public UUID landlordId;
    public String landlordName;
    public UUID applicationId;
    public LocalDate startDate;
    public LocalDate endDate;
    public BigDecimal monthlyRent;
    public String status;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;

    public RentalResponse(Rental rental) {
        this.id = rental.getId();
        this.propertyId = rental.getProperty().getId();
        this.propertyTitle = rental.getProperty().getTitle();
        this.propertyCity = rental.getProperty().getCity();
        this.tenantId = rental.getTenant().getId();
        this.tenantName = rental.getTenant().getName();
        this.landlordId = rental.getLandlord().getId();
        this.landlordName = rental.getLandlord().getName();
        this.applicationId = rental.getApplication().getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.monthlyRent = rental.getMonthlyRent();
        this.status = rental.getStatus().name();
        this.createdAt = rental.getCreatedAt();
        this.updatedAt = rental.getUpdatedAt();
    }
}
