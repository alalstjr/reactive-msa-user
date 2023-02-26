package com.jjunpro.reactive.domain.user.type;

import com.jjunpro.reactive.domain.config.type.EnumModel;

public enum UserRole implements EnumModel {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    CLIENT("ROLE_CLIENT"),
    GUEST("ROLE_GUEST");

    private final String roleUser;

    UserRole(String roleUser) {
        this.roleUser = roleUser;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return roleUser;
    }
}