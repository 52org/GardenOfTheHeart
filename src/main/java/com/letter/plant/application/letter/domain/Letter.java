package com.letter.plant.application.letter.domain;

import com.letter.plant.application.garden.domain.Garden;
import com.letter.plant.core.jpa.TimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Letter extends TimeEntity {

    private String author;
    private String message;
    private String plantName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Garden garden;

    @OneToMany(mappedBy = "letter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Keyword> keywords = new ArrayList<>();

    @Builder
    public Letter(String author, String message, String plantName, Garden garden, List<Keyword> keywords) {
        this.author = author;
        this.message = message;
        this.plantName = plantName;
        this.garden = garden;
        this.keywords = keywords;
    }

    public void addKeywords(List<String> keyWords) {
        for (String word : keyWords) {
            this.keywords.add(new Keyword(this, word));
        }
    }

    public void addGarden(Garden garden) {
        this.garden = garden;
    }
}
