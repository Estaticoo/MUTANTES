package org.example.Services;

import org.example.Dto.StatsResponse;
import org.example.Repositories.MutantDetectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class StatsService {
    private final MutantDetectorRepository mutantDetectorRepository;

    @Autowired
    public StatsService(MutantDetectorRepository mutantDetectorRepository) {
        this.mutantDetectorRepository = mutantDetectorRepository;
    }

    public StatsResponse getStats(){

        long countmutantdna = mutantDetectorRepository.countByIsMutant(true);
        long counthumandna = mutantDetectorRepository.countByIsMutant(false);
        double ratio = counthumandna == 0 ? 0 : (double) countmutantdna / counthumandna;
        return new StatsResponse(countmutantdna, counthumandna, ratio);
     }
}
