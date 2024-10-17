package org.example.Repositories;

import org.example.Entidades.MutantDetector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository


public interface MutantDetectorRepository extends JpaRepository<MutantDetector,Long> {
    Optional<MutantDetector> findByDna(String dnaSequence);

    long countByIsMutant(boolean isMutant);
}
