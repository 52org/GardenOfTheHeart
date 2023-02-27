package com._52org.gardenoftheheart.application.letter.domain;

import com._52org.gardenoftheheart.core.jpa.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    private Letter letter;

    @Column(nullable = false)
    private String keyword;

    @Builder
    public Keyword(Letter letter, String keyword) {
        this.letter = letter;
        this.keyword = keyword;
    }

}
