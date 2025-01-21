package com.famdev.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.famdev.dslist.dto.GameListDTO;

import com.famdev.dslist.entities.GameList;
import com.famdev.dslist.repositories.GameListRepository;

@Service
public class GameListService {

    @Autowired
    private GameListRepository repository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> result = repository.findAll();

        return result.stream().map(GameListDTO::new).toList();

    }

}
