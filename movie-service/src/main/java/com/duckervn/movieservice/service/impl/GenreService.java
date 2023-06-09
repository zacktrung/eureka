package com.duckervn.movieservice.service.impl;

import com.duckervn.movieservice.common.Response;
import com.duckervn.movieservice.common.RespMessage;
import com.duckervn.movieservice.common.Utils;
import com.duckervn.movieservice.domain.entity.Genre;
import com.duckervn.movieservice.domain.exception.ResourceNotFoundException;
import com.duckervn.movieservice.domain.model.addgenre.GenreInput;
import com.duckervn.movieservice.repository.GenreRepository;
import com.duckervn.movieservice.service.IGenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GenreService implements IGenreService {
    private final GenreRepository genreRepository;

    private final ObjectMapper objectMapper;

    /**
     * @param id id
     * @return genre
     */
    @Override
    public Response findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return Response.builder().code(HttpStatus.OK.value()).message(RespMessage.FOUND_GENRE)
                .result(genre).build();
    }

    /**
     * @param genreInput genre input
     * @return
     */
    @Override
    public Response save(GenreInput genreInput) {
        Genre genre = objectMapper.convertValue(genreInput, Genre.class);
        save(genre);
        return Response.builder().code(HttpStatus.CREATED.value())
                .message(RespMessage.CREATED_GENRE).build();
    }

    @Override
    public void save(Genre genre) {
        genre.setCreatedAt(LocalDateTime.now());
        genre.setModifiedAt(LocalDateTime.now());
        genreRepository.save(genre);
    }

    /**
     * @return list genre
     */
    @Override
    public Response findAll() {
        return Response.builder().code(HttpStatus.OK.value())
                .message(RespMessage.FOUND_ALL_GENRES)
                .results(Utils.toObjectList(genreRepository.findAll())).build();
    }
}
