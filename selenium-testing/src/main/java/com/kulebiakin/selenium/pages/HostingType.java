package com.kulebiakin.selenium.pages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HostingType {
    VPS_HOSTING("VPS Hosting"),
    DEDICATED_SERVERS("Dedicated Servers"),
    WORDPRESS_HOSTING("WordPress Hosting"),
    SHARED_HOSTING("Shared Hosting"),
    RESELLER_HOSTING("Reseller Hosting");

    private final String displayName;

    public static HostingType fromDisplayName(String displayName) {
        return Arrays.stream(values())
            .filter(type -> type.displayName.equals(displayName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Unknown hosting type: %s".formatted(displayName)));
    }
}
