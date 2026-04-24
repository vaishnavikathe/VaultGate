package com.vaultgate.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vaultgate.entity.enums.NomineeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "nominees", indexes = {
    @Index(name = "idx_vault_id", columnList = "vault_id")
})
@Data
public class Nominee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relationship with Vault
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vault_id", nullable = false)
    private Vault vault;

    //Basic Info
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String relation;

    //Verification + Access Control
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "access_granted", nullable = false)
    private boolean accessGranted;

    @Column(name = "access_granted_time")
    private LocalDateTime accessGrantedTime;

    //Secret Sharing Support
    @Column(name = "key_fragment")
    private String keyFragment;

    @Column(name = "fragment_submitted", nullable = false)
    private boolean fragmentSubmitted;

    //Status Tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NomineeStatus status;

    //Audit Fields
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //Default Values
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = NomineeStatus.PENDING;
        }
    }
}
