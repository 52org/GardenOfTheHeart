package com.letter.plant.application.letter.domain;

import com.letter.plant.core.jpa.TimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Keyword extends TimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Letter letter;
    private String keyword;

    public Keyword(Letter letter, String keyword) {
        this.letter = letter;
        this.keyword = keyword;
    }
}
