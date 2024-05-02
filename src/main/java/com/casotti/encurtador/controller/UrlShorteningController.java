package com.casotti.encurtador.controller;


import com.casotti.encurtador.Service.UrlService;
import com.casotti.encurtador.domain.dto.UrlDTO;
import com.casotti.encurtador.domain.dto.UrlErrorResponseDTO;
import com.casotti.encurtador.domain.dto.UrlResponseDTO;
import com.casotti.encurtador.domain.model.Url;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShorLink(@RequestBody UrlDTO urlDTO){

        Url urlToRet = urlService.generateShortLink(urlDTO);

        if(urlToRet != null){
            UrlResponseDTO urlResponseDTO = new UrlResponseDTO();
            urlResponseDTO.setOriginalUrl(urlToRet.getOriginalUrl());
            urlResponseDTO.setShortLink(urlToRet.getShortLink());
            urlResponseDTO.setExpirationDate(String.valueOf(urlToRet.getExpirationDate()));

            return new ResponseEntity<UrlResponseDTO>(urlResponseDTO, HttpStatus.OK);
        }

        UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
        urlErrorResponseDTO.setStatus("500");
        urlErrorResponseDTO.setError("There was an error processing your request. Please try again.");
        return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(shortLink)) {
            UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
            urlErrorResponseDTO.setError("Invalid URL");
            urlErrorResponseDTO.setStatus("404");
            return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
        }

        Url urlToRet = urlService.getEncodedUrl(shortLink);

        if (urlToRet == null) {
            UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
            urlErrorResponseDTO.setError("Url does not exist or it might have expired!");
            urlErrorResponseDTO.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
        }

        if (urlToRet.getExpirationDate().isBefore(LocalDateTime.now())) {
            urlService.deleteShortLink(urlToRet);
            UrlErrorResponseDTO urlErrorResponseDTO = new UrlErrorResponseDTO();
            urlErrorResponseDTO.setError("URL expired! Please try generating a fresh one!");
            urlErrorResponseDTO.setStatus("404");
            return new ResponseEntity<UrlErrorResponseDTO>(urlErrorResponseDTO, HttpStatus.OK);
        }

        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
    }
}
