package com.week6.AuthFlow.dtos;

import com.week6.AuthFlow.enums.Subscription;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDTO {

    @NotNull(message = "Subscription type is required.")
    private Subscription subscription;
}