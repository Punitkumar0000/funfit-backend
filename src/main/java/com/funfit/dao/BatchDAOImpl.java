package com.funfit.dao;

import com.funfit.model.Batch;
import com.funfit.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BatchDAOImpl implements BatchDAO {

    @Override
    public Batch save(Batch batch) throws Exception {
        String sql = "INSERT INTO batch (name, start_time, end_time, description) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, batch.getName());
            ps.setTime(2, batch.getStartTime());
            ps.setTime(3, batch.getEndTime());
            ps.setString(4, batch.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) batch.setId(rs.getInt(1));
        }
        return batch;
    }

    @Override
    public Batch update(Batch batch) throws Exception {
        String sql = "UPDATE batch SET name=?, start_time=?, end_time=?, description=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, batch.getName());
            ps.setTime(2, batch.getStartTime());
            ps.setTime(3, batch.getEndTime());
            ps.setString(4, batch.getDescription());
            ps.setInt(5, batch.getId());
            ps.executeUpdate();
        }
        return batch;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM batch WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Batch> findById(int id) throws Exception {
        String sql = "SELECT * FROM batch WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Batch b = new Batch();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setStartTime(rs.getTime("start_time"));
                b.setEndTime(rs.getTime("end_time"));
                b.setDescription(rs.getString("description"));
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Batch> findAll() throws Exception {
        String sql = "SELECT * FROM batch";
        List<Batch> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Batch b = new Batch();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setStartTime(rs.getTime("start_time"));
                b.setEndTime(rs.getTime("end_time"));
                b.setDescription(rs.getString("description"));
                list.add(b);
            }
        }
        return list;
    }
}
