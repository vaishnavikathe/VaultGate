package com.vaultgate.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vaultgate.entity.enums.ContentType;
import com.vaultgate.entity.enums.VaultStatus;

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
@Table(name = "vaults", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_unlock_time", columnList = "unlock_time")
})
@Data
public class Vault {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "encrypted_data", nullable = false, columnDefinition = "TEXT")
    private String encryptedData;

    @Column(name = "unlock_time", nullable = false)
    private LocalDateTime unlockTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VaultStatus vaultStatus;

    @Column(name = "release_time")
    private LocalDateTime releaseTime;

    @Column(name = "last_check_in")
    private LocalDateTime lastCheckIn;

    @Column(name = "inactivity_threshold_days")
    private Integer inactivityThresholdDays;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (vaultStatus == null) {
            vaultStatus = VaultStatus.LOCKED;
        }
        if (contentType == null) {
            contentType = ContentType.TEXT;
        }
    }
}
