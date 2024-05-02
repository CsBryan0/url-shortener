package com.casotti.encurtador.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDTO {

    private String originalUrl;
    private String shortLink;
    private String expirationDate;
}
