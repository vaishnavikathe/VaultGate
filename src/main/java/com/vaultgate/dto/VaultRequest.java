package com.vaultgate.dto;

import java.time.LocalDateTime;

import com.vaultgate.entity.enums.ContentType;

import lombok.Data;

@Data
public class VaultRequest {
    private String data;
    private String password;
    private Long userId;
    private LocalDateTime unlockTime;
    private ContentType contentType;
}