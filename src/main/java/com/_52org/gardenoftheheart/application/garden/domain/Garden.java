package com._52org.gardenoftheheart.application.garden.domain;

import com._52org.gardenoftheheart.core.jpa.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Garden extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String gardenName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String uuid;

    @Builder
    public Garden(String gardenName, String password, String uuid) {
        this.gardenName = gardenName;
        this.password = password;
        this.uuid = uuid;
    }

}
