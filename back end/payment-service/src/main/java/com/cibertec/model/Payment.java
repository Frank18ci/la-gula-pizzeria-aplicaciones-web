package com.cibertec.model;



import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments",
        indexes = {
                @Index(name = "idx_payments_order", columnList = "order_id"),
                @Index(name = "idx_payments_status", columnList = "status_id")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne con Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_order"))
    private Order order;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "CHAR(3)", nullable = false)
    private String currency = "USD";

    // Relación ManyToOne con PaymentProvider
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_provider"))
    private PaymentProvider paymentProvider;

    // Relación ManyToOne con PaymentStatus
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payments_status"))
    private PaymentStatus paymentStatus;

    @Column(name = "external_id", length = 100)
    private String externalId;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "created_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
            insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false)
    private LocalDateTime updatedAt;
}

