package online.bobbylinux.bobbybook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import online.bobbylinux.bobbybook.enums.ContentType;

public record PhotoResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("path") String path,
        @JsonProperty("file_name") String fileName,
        @JsonProperty("content_type") ContentType contentType) {
}
