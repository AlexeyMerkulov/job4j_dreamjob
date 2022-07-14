package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Junior Java", "Имею опыт работы 1 год",
                LocalDateTime.of(2022, 7, 13, 8, 15)));
        candidates.put(2, new Candidate(2, "Middle Java", "Имею опыт работы 2 года",
                LocalDateTime.of(2022, 5, 30, 10, 30)));
        candidates.put(3, new Candidate(3, "Senior Java", "Имею опыт работы 5 лет",
                LocalDateTime.of(2022, 6, 17, 21, 14)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
