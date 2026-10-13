package com.trustrent.backend.dto;

import com.trustrent.backend.entity.Review;

import java.util.UUID;
import java.time.OffsetDateTime;

public class ReviewResponse {
    public UUID id;
    public UUID rentalId;
    public UUID reviewerId;
    public UUID reviewedUserId;
    public Integer maintenanceRating;
    public Integer responsivenessRating;
    public Integer communicationRating;
    public Integer propertyCareRating;
    public Integer ruleComplianceRating;
    public Integer overallRating;
    public String comment;
    public OffsetDateTime createdAt;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rentalId = review.getRental().getId();
        this.reviewerId = review.getReviewer().getId();
        this.reviewedUserId = review.getReviewedUser().getId();
        this.maintenanceRating = review.getMaintenanceRating();
        this.responsivenessRating = review.getResponsivenessRating();
        this.communicationRating = review.getCommunicationRating();
        this.propertyCareRating = review.getPropertyCareRating();
        this.ruleComplianceRating = review.getRuleComplianceRating();
        this.overallRating = review.getOverallRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
