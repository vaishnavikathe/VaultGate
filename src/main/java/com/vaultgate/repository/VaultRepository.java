package com.vaultgate.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaultgate.entity.Vault;
import com.vaultgate.entity.enums.VaultStatus;

public interface VaultRepository extends JpaRepository<Vault, Long>{

	List<Vault> findByVaultStatusAndUnlockTimeBefore(
	        VaultStatus status,
	        LocalDateTime time
	);
}
