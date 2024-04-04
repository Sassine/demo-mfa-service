package dev.sassine.api.mfa.dto;

public record UserDTO(Long id, String user, String password, String secret) {
}
