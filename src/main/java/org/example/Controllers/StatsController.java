package org.example.Controllers;

import org.example.Dto.MutantDetectorResponse;
import org.example.Dto.StatsResponse;
import org.example.Services.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stats")

public class StatsController {
    private final  StatsService statsService;


    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public StatsResponse getStats(){

        return statsService.getStats();

    }

}
