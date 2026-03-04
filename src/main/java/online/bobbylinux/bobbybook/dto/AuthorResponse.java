package online.bobbylinux.bobbybook.dto;

import lombok.Value;

//value rende la classe immutabile
@Value
public record AuthorResponse (
	Long id,
	String first_name,
	String last_name
) {};
