package com._52org.gardenoftheheart.application.letter.repository;

import com._52org.gardenoftheheart.application.letter.domain.Keyword;
import com._52org.gardenoftheheart.application.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findByLetter(final Letter letter);

}
