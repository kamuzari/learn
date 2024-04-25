package com.example.security.user.controller.request;

import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(@NotNull String username,
							 @NotNull String password) {
}
