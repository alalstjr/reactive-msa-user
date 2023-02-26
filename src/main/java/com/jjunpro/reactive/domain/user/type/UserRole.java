package com.jjunpro.reactive.domain.user.type;

import com.jjunpro.reactive.domain.config.type.EnumModel;

public enum UserRole implements EnumModel {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_CLIENT("CLIENT"),
    ROLE_GUEST("GUEST");

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