package com.famdev.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.famdev.dslist.dto.GameDTO;
import com.famdev.dslist.dto.GameMinDTO;
import com.famdev.dslist.entities.Game;
import com.famdev.dslist.projections.GameMinProjection;
import com.famdev.dslist.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;


    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Game result = repository.findById(id).get();
        return new GameDTO(result);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll() {
        List<Game> result = repository.findAll();

        // return result.stream().map(x -> new GameMinDTO(x)).toList();
        return result.stream().map(GameMinDTO::new).toList();

    }
    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long listId) {
           List<GameMinProjection> result = repository.searchByList(listId);

        // return result.stream().map(x -> new GameMinDTO(x)).toList();
        return result.stream().map(GameMinDTO::new).toList();

    }

}
