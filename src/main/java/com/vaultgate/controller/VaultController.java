package com.vaultgate.controller;

//import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vaultgate.dto.VaultRequest;
import com.vaultgate.entity.Vault;
import com.vaultgate.entity.enums.ContentType;
import com.vaultgate.entity.enums.VaultStatus;
import com.vaultgate.entity.User;
import com.vaultgate.repository.UserRepository;
import com.vaultgate.service.VaultService;

@RestController
@RequestMapping("/api/vaults")
public class VaultController {

    @Autowired
    private VaultService vaultService;

    @Autowired
    private UserRepository userRepository;

    // CREATE VAULT
    @PostMapping
    public Map<String, Object> createVault(@RequestBody VaultRequest request) {
    	
    	System.out.println("ContentType from request: " + request.getContentType());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vault vault = new Vault();
        vault.setUser(user);
        vault.setEncryptedData(request.getData());
        
        vault.setContentType(
        	    request.getContentType() != null 
        	        ? request.getContentType() 
        	        : ContentType.TEXT
        	);

        vault.setUnlockTime(request.getUnlockTime());

        vault.setVaultStatus(VaultStatus.LOCKED); 
        Vault savedVault = vaultService.saveVault(vault, request.getPassword());

        return Map.of(
                "message", "Vault created successfully",
                "vaultId", savedVault.getId(),
                "unlockTime", savedVault.getUnlockTime()
        );
    }

    // GET VAULT (DECRYPT)
    @GetMapping("/{id}")
    public Map<String, Object> getVault(
            @PathVariable Long id,
            @RequestParam String password,
            @RequestParam Long userId
    ) {

        return vaultService.getDecryptedVault(id, password, userId);
    }
}