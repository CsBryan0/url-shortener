package com.casotti.encurtador.repository;

import com.casotti.encurtador.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortLink(String shortLink);
}
