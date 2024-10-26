package org.example.Controllers;


import jakarta.validation.Valid;
import org.example.Dto.MutantDetectorRequest;
import org.example.Dto.MutantDetectorResponse;
import org.example.Entidades.MutantDetector;
import org.example.Services.MutantDetectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/mutant")

public class DnaController {
    private final MutantDetectorService mutantdetectoservice;


    public DnaController(MutantDetectorService mutantdetectoservice) {
        this.mutantdetectoservice = mutantdetectoservice;
    }

    @PostMapping
    public ResponseEntity<MutantDetectorResponse> checkMutant(@RequestBody MutantDetectorRequest mutantDetectorRequest){
       boolean isMutant = mutantdetectoservice.analyzeDna(mutantDetectorRequest.getDna());
       MutantDetectorResponse mutantDetectorResponse = new MutantDetectorResponse((isMutant));
       if (isMutant){
           return ResponseEntity.ok(mutantDetectorResponse);

       }else {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mutantDetectorResponse);
       }
    }

    @GetMapping("/get")
    public ResponseEntity<List<MutantDetector>> getAllHumans() {
        List<MutantDetector> personas = mutantdetectoservice.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(personas);
    }

}
