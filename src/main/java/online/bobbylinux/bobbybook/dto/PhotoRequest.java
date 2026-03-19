package online.bobbylinux.bobbybook.dto;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record PhotoRequest(
        @NotBlank(message = "File Name is required") @JsonProperty("file_name") String fileName,
        @NotBlank(message = "File is required") @JsonProperty("file") File file) {

}
