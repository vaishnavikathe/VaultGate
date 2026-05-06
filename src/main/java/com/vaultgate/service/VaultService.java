package com.vaultgate.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vaultgate.entity.Vault;
import com.vaultgate.entity.enums.VaultStatus;
import com.vaultgate.repository.VaultRepository;
import com.vaultgate.util.EncryptionUtil;

@Service
public class VaultService {

    @Autowired
    private VaultRepository vaultRepository;

    // CREATE VAULT
    public Vault saveVault(Vault vault, String userPassword) {

        String encrypted = EncryptionUtil.encrypt(vault.getEncryptedData(), userPassword);
        vault.setEncryptedData(encrypted);

        return vaultRepository.save(vault);
    }

    // GET + VALIDATE + DECRYPT
    public Map<String, Object> getDecryptedVault(Long vaultId, String userPassword, Long userId) {

        Vault vault = vaultRepository.findById(vaultId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Vault not found"
                ));
        
        // ✅ OWNERSHIP CHECK
        if (!vault.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to access this vault"
            );
        }

        // CHECK 1: Unlock time
        if (vault.getUnlockTime() == null || LocalDateTime.now().isBefore(vault.getUnlockTime())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vault is still locked"
            );
        }

        // CHECK 2: Status
        if (vault.getVaultStatus() != VaultStatus.RELEASED) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vault is not released yet"
            );
        }

        try {
            // ✅ DECRYPT (controlled)
            String decrypted = EncryptionUtil.decrypt(vault.getEncryptedData(), userPassword);

            return Map.of(
                    "vaultId", vault.getId(),
                    "data", decrypted,
                    "status", vault.getVaultStatus(),
                    "releasedAt", vault.getReleaseTime()
            );

        } catch (Exception e) {
            // ❗ CRITICAL FIX
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password"
            );
        }
    }
}