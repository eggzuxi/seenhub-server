package com.seenhub.backend.domain.series.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Genre {

    Action_N_Adventure(10759, "Action & Adventure"), Animation(16, "Animation"), Comedy(35, "Comedy"), Crime(80, "Crime"),
    Documentary(99, "Documentary"), Drama(18, "Drama"), Family(10751, "Family"), Kids(10762, "Kids"), Mystery(9648, "Mystery"),
    News(10763, "News"), Reality(10764, "Reality"), SF_N_Fantasy(10765, "Sci-Fi & Fantasy"), Soap(10766, "Soap"), Talk(10767, "Talk"),
    War_N_Politics(10768, "War & Politics"), Western(37, "Western");

    private final int id;
    private final String name;

    Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static final Map<Integer, Genre> idToGenre = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(Genre::getId, Function.identity())));

    public static Genre fromId(int id) {
        return idToGenre.getOrDefault(id, null);
    }

}
