package com.example.ecom.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Notification extends BaseModel{
    @ManyToOne
    private Product product;
    @ManyToOne
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus status;
}
