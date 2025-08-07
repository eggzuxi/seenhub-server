package com.seenhub.backend.domain.movie.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Genre {

    Action(28, "Action"), Adventure(12, "Adventure"), Animation(16, "Animation"),
    Comedy(35, "Comedy"), Crime(80, "Crime"), Documentary(99, "Documentary"),
    Drama(18, "Drama"), Family(10751, "Family"), Fantasy(14, "Fantasy"),
    History(36, "History"), Horror(27, "Horror"), Music(10402, "Music"),
    Mystery(9648, "Mystery"), Romance(10749, "Romance"), SF(878, "Science Fiction"),
    TV_Movie(10770, "TV Movie"), Thriller(53, "Thriller"), War(10752, "War"), Western(37, "Western");

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
