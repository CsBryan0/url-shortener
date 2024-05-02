package com.casotti.encurtador.Service;


import com.casotti.encurtador.domain.dto.UrlDTO;
import com.casotti.encurtador.domain.model.Url;
import com.casotti.encurtador.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


@Component
public class UrlServiceImpl implements UrlService{
    private static final Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(UrlDTO urlDTO) {

        if(StringUtils.isNotEmpty(urlDTO.url())){
            String encodedUrl = encodedUrl(urlDTO.url());
            Url urlToPersist = new Url();
            urlToPersist.setOriginalUrl(urlDTO.url());
            urlToPersist.setShortLink(encodedUrl);
            urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setExpirationDate(getExpirationDate(urlDTO.expirationDate(), urlToPersist.getCreationDate()));
            Url urlToRet = persistShortLink(urlToPersist);

            if(urlToRet != null){
                return urlToRet;
            }

            return null;
        }
        return null;
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate){
        if(StringUtils.isBlank(expirationDate)){
            return creationDate.plusSeconds(60);
        }

        return LocalDateTime.parse(expirationDate);
    }

    private String encodedUrl(String url){
        String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl = Hashing.murmur3_32_fixed()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();

        return encodedUrl;
    }

    @Override
    public Url persistShortLink(Url url) {
        return urlRepository.save(url);
    }

    @Override
    public Url getEncodedUrl(String url) {

        return urlRepository.findByShortLink(url);
    }

    @Override
    public void deleteShortLink(Url url) {

        urlRepository.delete(url);
    }
}
