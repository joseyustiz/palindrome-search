package com.joseyustiz.walmart.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchPhraseDto {
    @ValidSearchPhrase
    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ0-9 ]*$", message = "contains character not allowed")
    private String phrase;
}
