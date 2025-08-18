package com.seenhub.backend.domain.movie.service.impl;

import com.seenhub.backend.domain.common.dto.PageResponseDto;
import com.seenhub.backend.domain.movie.dto.MovieCreateRequestDto;
import com.seenhub.backend.domain.movie.dto.MovieListDto;
import com.seenhub.backend.domain.movie.dto.MovieSearchDto;
import com.seenhub.backend.domain.movie.dto.MovieUpdateRequestDto;
import com.seenhub.backend.domain.movie.entity.Genre;
import com.seenhub.backend.domain.movie.entity.Movie;
import com.seenhub.backend.domain.movie.repository.MovieRepository;
import com.seenhub.backend.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieApiServiceImpl movieApiService;
    private final ReactiveMongoTemplate mongoTemplate;

    @Value("${webclient.tmdb-poster-url}")
    private String posterUrl;

    @Override
    public Mono<List<MovieSearchDto>> searchMovie(String title) {

        return movieApiService.findMovieByTitle(title)
                .flatMapMany(movieResponseDto -> Flux.fromIterable(movieResponseDto.getResults()))
                .map(results -> {
                    double rating = results.getVoteAverage();
                    List<Genre> genres = results.getGenreIds().stream()
                            .map(Genre::fromId)
                            .filter(genre -> genre != null)
                            .collect(Collectors.toList());
                    String thumbnail = results.getPosterPath() != null
                            ? posterUrl + results.getPosterPath()
                            : null;

                    return MovieSearchDto.builder()
                            .title(results.getTitle())
                            .rating(rating)
                            .genres(genres)
                            .thumbnail(thumbnail)
                            .build();

                })
                .collectList();

    }

    @Override
    public Mono<Void> createMovie(MovieCreateRequestDto dto) {

        Movie movie = Movie.builder()
                .title(dto.getMovie().getTitle())
                .genres(dto.getMovie().getGenres())
                .rating(dto.getMovie().getRating())
                .thumbnail(dto.getMovie().getThumbnail())
                .isMasterPiece(dto.isMasterPiece())
                .build();

        return movieRepository.save(movie).then();

    }

    @Override
    public Mono<PageResponseDto<MovieListDto>> findMovieList(int page, int size) {

        Mono<Long> totalCount = movieRepository.count();

        Query query = new Query(
                Criteria.where("isDeleted").is(false)
        )
                .skip((long)(page - 1) * size)
                .limit(size)
                .with(Sort.by(Sort.Direction.DESC, "createdAt"));

        Mono<List<MovieListDto>> movieList = mongoTemplate.find(query, Movie.class)
                .map(movie -> MovieListDto.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .genres(movie.getGenres())
                        .rating(movie.getRating())
                        .thumbnail(movie.getThumbnail())
                        .commentId(movie.getCommentId())
                        .isMasterPiece(movie.isMasterPiece())
                        .build())
                .collectList();

        return Mono.zip(movieList, totalCount)
                .map(tuple -> {
                    List<MovieListDto> content = tuple.getT1();
                    Long totalCnt = tuple.getT2();
                    boolean isLast = (long) (page + 1) * size >= totalCnt;

                    return PageResponseDto.<MovieListDto>builder()
                            .content(content)
                            .pageNumber(page)
                            .pageSize(size)
                            .last(isLast)
                            .build();

                        });

    }

    @Override
    public Mono<Void> updateMovie(String id, MovieUpdateRequestDto dto) {

        return movieRepository.findById(id)
                .flatMap(oldMovie -> {

                    Movie.MovieBuilder<?, ?> builder = oldMovie.toBuilder();

                    if (dto.getTitle() != null) {
                        builder.title(dto.getTitle());
                    }

                    if (dto.getGenres() != null) {
                        builder.genres(dto.getGenres());
                    }

                    if (dto.getRating() != null) {
                        builder.rating(dto.getRating());
                    }

                    if (dto.getIsMasterPiece() != null) {
                        builder.isMasterPiece(dto.getIsMasterPiece());
                    }

                    Movie newMovie = builder.build();
                    return movieRepository.save(newMovie);

                })
                .then();

    }

    @Override
    public Mono<Void> deleteMovie(String id) {

        return movieRepository.findById(id)
                .flatMap(oldMovie -> {

                    Movie movie = oldMovie.toBuilder()
                            .isDeleted(true)
                            .build();

                    return movieRepository.save(movie);

                })
                .then();

    }

}
