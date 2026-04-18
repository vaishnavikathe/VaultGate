package com.vaultgate.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vaultgate.entity.enums.AccountStatus;
import com.vaultgate.entity.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users", indexes = {
		@Index(name = "idx_username", columnList = "username"),
	    @Index(name = "idx_email", columnList = "email")
	})
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true, nullable = false)
	private String username;
	
	@NotBlank
	@Size(min = 8)
	@Column(nullable = false)
	private String password;
	
	@Email
	@NotBlank
	@Column(unique = true, nullable = false)
	private String email;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Invaild mobile number")
	private String mobile;
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime lastLogin;
	private LocalDateTime lastActive;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_status")
	private AccountStatus accountStatus;
	
	
	
	@Column(name = "failed_login_attempts", nullable = false)
	private int failedLoginAttempts;	
	
	private LocalDateTime lastFailedLogin;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	public void prePersist() {
		if (accountStatus == null) {
			accountStatus = AccountStatus.ACTIVE;
		}
		if (failedLoginAttempts < 0) {
	        failedLoginAttempts = 0;
	    }
	}
}
