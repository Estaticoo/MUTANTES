package org.example.Entidades;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class MutantDetector extends Base implements Serializable {

        private String dna;
        private boolean isMutant;



}
