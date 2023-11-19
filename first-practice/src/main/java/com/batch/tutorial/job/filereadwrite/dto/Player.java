package com.batch.tutorial.job.filereadwrite.dto;

import lombok.Builder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.io.Serializable;

public record Player(
        String ID
        , String lastName
        , String firstName
        , String position
        , int birthYear
        , int debutYear) implements Serializable {

    @Builder
    public Player {
    }

    @Override
    public String toString() {
        return "PLAYER:ID=" + ID + ",Last Name=" + lastName +
                ",First Name=" + firstName + ",Position=" + position +
                ",Birth Year=" + birthYear + ",DebutYear=" +
                debutYear;
    }

    public static class PlayerFieldSetMapper implements FieldSetMapper<Player> {
        public Player mapFieldSet(FieldSet fieldSet) {
            return Player.builder()
                    .ID(fieldSet.readString(0))
                    .lastName(fieldSet.readString(1))
                    .firstName(fieldSet.readString(2))
                    .position(fieldSet.readString(3))
                    .birthYear(fieldSet.readInt(4))
                    .debutYear(fieldSet.readInt(5))
                    .build();
        }
    }

}
