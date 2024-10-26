package org.example.Services;

import org.example.Entidades.MutantDetector;
import org.example.Repositories.MutantDetectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service


public class MutantDetectorService {


    private final MutantDetectorRepository mutantDetectorRepository;
    private static final int SEQUENCE_LENGTH = 4;

    @Autowired
    public MutantDetectorService(MutantDetectorRepository mutantDetectorRepository) {
        this.mutantDetectorRepository = mutantDetectorRepository;
    }

    public static boolean isMutant(String[] dna) {
        int sequenceCount = 0;
        int n = dna.length;

//        // Verificación de la matriz de ADN
        if (!checkMatriz(dna, n)) {
            throw new IllegalArgumentException("El ADN es nulo o no es una matriz válida");
        }

//        //Verificacion de la letras
        if (!checkLetras(dna, n)) {
            throw new IllegalArgumentException("Las letras no son validas");
        }

        //Busqueda de secuencias mutantes

        sequenceCount += checkHorizontal(dna);
        if (sequenceCount > 1) return true;


        sequenceCount += checkVertical(dna, n);
        if (sequenceCount > 1) return true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (checkDiagonal(dna, i, j) || checkAntiDiagonal(dna, i, j)) {
                    sequenceCount++;
                }
                if (sequenceCount > 1) {
                    return true; // Es mutante si encuentra más de una secuencia
                }
            }
        };



        return false;
    }

    private static boolean checkDiagonal(String[] dna, int row, int col) {
        if (row + SEQUENCE_LENGTH - 1 >= dna.length || col + SEQUENCE_LENGTH - 1 >= dna.length) return false;
        char base = dna[row].charAt(col);
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (base != dna[row + i].charAt(col + i)) return false;
        }
        return true;
    }

    private static boolean checkAntiDiagonal(String[] dna, int row, int col) {
        if (row + SEQUENCE_LENGTH - 1 >= dna.length || col - SEQUENCE_LENGTH + 1 < 0) return false;
        char base = dna[row].charAt(col);
        for (int i = 1; i < SEQUENCE_LENGTH; i++) {
            if (base != dna[row + i].charAt(col - i)) return false;
        }
        return true;
    }

    private static int checkHorizontal(String[] dna) {
        int sequenceCount = 0;

        for (String row : dna) {

            for (int j = 0; j <= row.length() - SEQUENCE_LENGTH; j++) {
                char firstChar = row.charAt(j);  // Tomamos el primer carácter de la secuencia de 4
                boolean allEqual = row.chars()
                        .skip(j)       // Saltamos hasta el índice actual
                        .limit(SEQUENCE_LENGTH)      // Limitamos el stream a los siguientes 4 caracteres
                        .allMatch(ch -> ch == firstChar);  // Verificamos si todos coinciden con el primer carácter
                if (allEqual) {
                    sequenceCount++;

                }
            }
        }
            return sequenceCount;  // Si no encontramos una secuencia, retornamos false

    }

    private static int checkVertical(String[] dna, int n){
        int sequenceCount = 0;  // Contador de secuencias encontradas

        for (int j = 0; j < n; j++) {  // Itera por cada columna
            int count = 1;  // Contador de caracteres consecutivos iguales
            for (int i = 1; i < n; i++) {  // Itera por cada fila de la columna
                // Compara el carácter actual con el anterior en la columna
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) {
                    count++;  // Incrementa el contador si son iguales
                    if (count == SEQUENCE_LENGTH) {  // Si el contador alcanza SEQUENCE_LENGTH
                        sequenceCount++;  // Aumenta el contador de secuencias
                        if (sequenceCount > 1) {return sequenceCount;}  // Si ya hay más de una secuencia, retorna

                    }
                } else {
                    count = 1;  // Reinicia el contador si los caracteres son diferentes
                }
            }
        }
        return sequenceCount;  // Retorna el número total de secuencias encontradas
    }



    public static boolean checkLetras(String[] dna, int n){
        final String VALID_CHARACTERS = "AGTC";

        for (String sequence : dna){
            if (sequence == null || sequence.length() != n ) {
                return false;
            }
            for (char c : sequence.toCharArray()) {
                if(VALID_CHARACTERS.indexOf(c) == -1 ){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkMatriz(String[] dna, int n) {
        // Verifica si el ADN es nulo o si no tiene secuencias
        if (dna == null || n == 0) {
            return false;
        }

        // Verifica que todas las filas del ADN tengan la misma longitud
        for (String row : dna) {
            if (row.length() != n) {
                return false; // No es una matriz cuadrada
            }
        }

        return true;
    }



    public boolean analyzeDna(String[] dna){
        String dnaSequence = String.join(",", dna);

        Optional<MutantDetector> existingMutant = mutantDetectorRepository.findByDna(dnaSequence);
        if (existingMutant.isPresent()){
            return existingMutant.get().isMutant();

        }
        boolean isMutant = isMutant(dna);
        MutantDetector mutantEntity= MutantDetector.builder()
                .dna(dnaSequence)
                .isMutant(isMutant)
                .build();
        mutantDetectorRepository.save(mutantEntity);
        return isMutant(dna);
    }

    public List<MutantDetector> getAll() {
        return mutantDetectorRepository.findAll();
    }
}
