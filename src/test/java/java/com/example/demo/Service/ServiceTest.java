package java.com.example.demo.Service;

import org.example.Services.MutantDetectorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTest {
    @Test
    public void mutantTest1(){
        String[] dna = {
                "AAAATG",
                "TGCAGT",
                "GCTTCC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };
        assertTrue(MutantDetectorService.isMutant(dna));
    }

    @Test
    public void mutantTest2(){
        String[] dna = {
                "AGAATG",
                "TACAGT",
                "GCAGCC",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(MutantDetectorService.isMutant(dna));
    }

    @Test
    public void mutantTest3(){
        String[] dna = {
                "ATAATG",
                "GTTAGT",
                "GGCTCG",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(MutantDetectorService.isMutant(dna));
    }
}
