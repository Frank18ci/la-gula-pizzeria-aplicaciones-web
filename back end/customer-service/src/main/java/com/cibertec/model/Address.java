package com.cibertec.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Relación con cliente
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(length = 50)
    private String label;
    @Column(nullable = false, length = 150)
    private String street;
    @Column(name = "external_number", length = 30)
    private String externalNumber;
    @Column(name = "internal_number", length = 30)
    private String internalNumber;
    @Column(length = 100)
    private String neighborhood;
    @Column(nullable = false, length = 100)
    private String city;
    @Column(length = 100)
    private String state;
    @Column(name = "zip_code", length = 20)
    private String zipCode;
    @Column(length = 2, nullable = false, columnDefinition = "CHAR(2) DEFAULT 'PE'")
    private String country = "PE";
    @Column(length = 255)
    private String reference;
    @Column(name = "is_default", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDefault = false;
    @Column(name = "created_at", nullable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime updatedAt;
}