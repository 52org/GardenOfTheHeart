package com.letter.plant.application.letter.repository;

import com.letter.plant.application.letter.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
