package com.letter.plant.application.letter.repository;

import com.letter.plant.application.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
