package com.vaultgate.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vaultgate.entity.Vault;
import com.vaultgate.entity.enums.VaultStatus;
import com.vaultgate.repository.VaultRepository;

@Service
public class VaultScheduler {
	
	@Autowired
	private VaultRepository vaultRepository;
	
	@Scheduled(fixedRate = 10000) // every 10 seconds
	public void checkVaults() {

	    LocalDateTime now = LocalDateTime.now();

	    List<Vault> vaults = vaultRepository
	            .findByVaultStatusAndUnlockTimeBefore(VaultStatus.LOCKED, now);

	    for (Vault vault : vaults) {
	        vault.setVaultStatus(VaultStatus.RELEASED);
	    }

	    vaultRepository.saveAll(vaults); // ✅ single DB call

	    if (!vaults.isEmpty()) {
	        System.out.println("Released vaults: " +
	                vaults.stream().map(Vault::getId).toList());
	    }
	}
}
