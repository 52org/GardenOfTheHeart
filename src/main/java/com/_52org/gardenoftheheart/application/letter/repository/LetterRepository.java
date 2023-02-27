package com._52org.gardenoftheheart.application.letter.repository;

import com._52org.gardenoftheheart.application.garden.domain.Garden;
import com._52org.gardenoftheheart.application.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByGarden(final Garden garden);

}
