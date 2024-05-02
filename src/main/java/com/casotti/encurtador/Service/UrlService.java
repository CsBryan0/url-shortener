package com.casotti.encurtador.Service;

import com.casotti.encurtador.domain.dto.UrlDTO;
import com.casotti.encurtador.domain.model.Url;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url generateShortLink(UrlDTO urlDTO);
    public Url persistShortLink(Url url);
    public Url getEncodedUrl(String url);
    public void deleteShortLink(Url url);
}
