package com.vaultgate.entity.enums;

public enum NomineeStatus {
	PENDING,        // invited but not verified
    VERIFIED,       // email verified
    ELIGIBLE,       // vault conditions met
    ACCESS_GRANTED, // can access vault
    REVOKED
}
