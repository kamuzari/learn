package com.example.security.user.controller.response;

public record LoginResponseDto(TokenDto access,
							   TokenDto refresh
) {
}
