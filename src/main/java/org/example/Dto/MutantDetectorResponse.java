package org.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter

public class MutantDetectorResponse {
    private boolean isMutant;

    public boolean isMutant(){
        return isMutant;
    }
}
