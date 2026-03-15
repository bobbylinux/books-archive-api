package online.bobbylinux.bobbybook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(
        @NotBlank(message = "First Name is required") @JsonProperty("first_name") String firstName,
        @NotBlank(message = "Last Name is required") @JsonProperty("last_name") String last_name) {
}
