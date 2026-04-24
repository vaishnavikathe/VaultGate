package com.vaultgate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaultgate.entity.Vault;

public interface VaultRepository extends JpaRepository<Vault, Long>{

}
