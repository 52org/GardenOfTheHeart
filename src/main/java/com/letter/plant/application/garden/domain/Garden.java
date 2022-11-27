package com.letter.plant.application.garden.domain;

import com.letter.plant.application.letter.domain.Letter;
import com.letter.plant.core.jpa.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Garden extends TimeEntity {

    private String uuid;

    private String name;

    @OneToMany(mappedBy = "garden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Letter> letters = new ArrayList<>();

    @Builder
    public Garden(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}

