package co.edu.uniajc.agrovalle.api.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresIn) { }
