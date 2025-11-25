package com.funfit.repository;

import com.funfit.dao.BatchDAO;
import com.funfit.dao.BatchDAOImpl;
import com.funfit.dao.ParticipantDAO;
import com.funfit.dao.ParticipantDAOImpl;

public class Repository {
    private final BatchDAO batchDAO = new BatchDAOImpl();
    private final ParticipantDAO participantDAO = new ParticipantDAOImpl();

    public BatchDAO getBatchDAO() {
        return batchDAO;
    }

    public ParticipantDAO getParticipantDAO() {
        return participantDAO;
    }
}

