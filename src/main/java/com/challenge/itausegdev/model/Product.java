package com.challenge.itausegdev.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NonNull
    private String nome;

    @NonNull
    private CategoryTypes categoria;

    @NonNull
    private BigDecimal precoBase;

    @NonNull
    private BigDecimal precoTarifado;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    void preInsert() {
        if (this.createdAt == null)
            this.createdAt = new Date();

        if (this.updatedAt == null)
            this.updatedAt = new Date();
    }

    @PreUpdate
    void preUpdate() {
        if (this.updatedAt == null)
            this.updatedAt = new Date();
    }
}
