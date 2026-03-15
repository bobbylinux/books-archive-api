package online.bobbylinux.bobbybook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequest(
        @NotBlank(message = "Name field is required") @JsonProperty("name") String name) {
}
