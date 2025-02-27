package com.famdev.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.famdev.dslist.dto.GameListDTO;

import com.famdev.dslist.entities.GameList;
import com.famdev.dslist.projections.GameMinProjection;
import com.famdev.dslist.repositories.GameListRepository;
import com.famdev.dslist.repositories.GameRepository;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;
    @Autowired

    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();

        return result.stream().map(GameListDTO::new).toList();

    }

    @Transactional(readOnly = true)
    public void move(Long listId, int sourceIndex, int destinationIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);
       

        GameMinProjection gameToMove = list.remove(sourceIndex);
        list.add(destinationIndex, gameToMove);

        for (int repositionedIndex = 0; repositionedIndex < list.size(); repositionedIndex++) {

            gameListRepository.updateBelongingPosition(listId, list.get(repositionedIndex).getId(),
                    repositionedIndex);
        }
    }

}
