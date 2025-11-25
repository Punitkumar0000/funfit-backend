package com.funfit.dao;

import com.funfit.model.Batch;
import java.util.List;
import java.util.Optional;

public interface BatchDAO {
    Batch save(Batch batch) throws Exception;         // insert
    Batch update(Batch batch) throws Exception;       // update
    boolean delete(int id) throws Exception;          // delete
    Optional<Batch> findById(int id) throws Exception;
    List<Batch> findAll() throws Exception;
}
