package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CandidateDBStoreTest {
    private static CandidateDBStore store = new CandidateDBStore(new Main().loadPool());

    @AfterEach
    public void wipeTable() {
        store.deleteCandidates();
    }

    @Test
    public void whenCreateCandidate() {
        Candidate candidate = new Candidate(0, "Sergey", "Junior Java dev",
                LocalDateTime.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidate.getName()).isEqualTo(candidateInDb.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        Candidate candidate = new Candidate(0, "Sergey", "Junior Java dev",
                LocalDateTime.now());
        store.add(candidate);
        Candidate newCandidate = new Candidate(candidate.getId(), "Andrey", "Junior Java dev",
                LocalDateTime.now());
        store.update(newCandidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(newCandidate.getName());
    }

    @Test
    public void whenFindAllCandidates() {
        Candidate candidate = new Candidate(0, "Sergey", "Junior Java dev",
                LocalDateTime.now());
        store.add(candidate);
        Candidate newCandidate = new Candidate(0, "Andrey", "Middle Java dev",
                LocalDateTime.now());
        store.add(newCandidate);
        List<Candidate> candidatesList = List.of(candidate, newCandidate);
        List<Candidate> candidatesListFromDB = store.findAll();
        assertThat(candidatesList).isEqualTo(candidatesListFromDB);
    }
}