package com.example.security.user.controller.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(@NotNull String username,
							  @NotNull String password) {
}
