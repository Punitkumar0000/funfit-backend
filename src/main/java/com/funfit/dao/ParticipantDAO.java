package com.funfit.dao;

import com.funfit.model.Participant;
import java.util.List;
import java.util.Optional;

public interface ParticipantDAO {
    Participant save(Participant p) throws Exception;
    Participant update(Participant p) throws Exception;
    boolean delete(int id) throws Exception;
    Optional<Participant> findById(int id) throws Exception;
    List<Participant> findAll() throws Exception;
    List<Participant> findByBatchId(int batchId) throws Exception;
}
