package com.batch.tutorial.job.filereadwrite.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PlayerYears(
        String ID
        , String lastName
        , String firstName
        , String position
        , int birthYear
        , int debutYear
        , int yearsExperience) implements Serializable {

    @Builder
    public PlayerYears {
    }

    public static PlayerYears from(Player player) {
        return PlayerYears.builder()
                .ID(player.ID())
                .lastName(player.lastName())
                .firstName(player.firstName())
                .position(player.position())
                .birthYear(player.birthYear())
                .debutYear(player.debutYear())
                .yearsExperience(LocalDateTime.now().getYear() - player.debutYear())
                .build();
    }
}
