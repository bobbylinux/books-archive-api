package online.bobbylinux.bobbybook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName) {
};
