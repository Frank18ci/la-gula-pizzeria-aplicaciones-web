package com.cibertec.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "payment_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    // Relación OneToMany con Payments
    @OneToMany(mappedBy = "paymentStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> status;
}

