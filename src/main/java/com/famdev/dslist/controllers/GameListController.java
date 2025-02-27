package com.famdev.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famdev.dslist.dto.GameListDTO;
import com.famdev.dslist.dto.GameMinDTO;
import com.famdev.dslist.dto.ReplacementDTO;
import com.famdev.dslist.services.GameListService;
import com.famdev.dslist.services.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

    @Autowired
    private GameListService gameListservices;

    @Autowired
    private GameService gameServices;

    @GetMapping
    public List<GameListDTO> findAll() {
        return gameListservices.findAll();
    }

    @GetMapping(value = { "/{listId}/games" })
    public List<GameMinDTO> findByList(@PathVariable Long listId) {
        return gameServices.findByList(listId);
    }

    @PostMapping(value = { "/{listId}/replacement" })
    public void move(@PathVariable Long listId, @RequestBody ReplacementDTO replacementDTO) {
        gameListservices.move(listId, replacementDTO.getSourceIndex(), replacementDTO.getDestinationIndex());
    }

}
