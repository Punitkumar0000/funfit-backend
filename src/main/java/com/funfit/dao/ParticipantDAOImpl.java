package com.funfit.dao;

import com.funfit.model.Participant;
import com.funfit.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipantDAOImpl implements ParticipantDAO {

    @Override
    public Participant save(Participant p) throws Exception {
        String sql = "INSERT INTO participant (name, email, phone, batch_id, enrolled_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPhone());
            if (p.getBatchId() == null) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, p.getBatchId());
            ps.setDate(5, p.getEnrolledDate());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) p.setId(rs.getInt(1));
        }
        return p;
    }

    @Override
    public Participant update(Participant p) throws Exception {
        String sql = "UPDATE participant SET name=?, email=?, phone=?, batch_id=?, enrolled_date=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPhone());
            if (p.getBatchId() == null) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, p.getBatchId());
            ps.setDate(5, p.getEnrolledDate());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        }
        return p;
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM participant WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Participant> findById(int id) throws Exception {
        String sql = "SELECT * FROM participant WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Participant p = mapRow(rs);
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Participant> findAll() throws Exception {
        String sql = "SELECT * FROM participant";
        List<Participant> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public List<Participant> findByBatchId(int batchId) throws Exception {
        String sql = "SELECT * FROM participant WHERE batch_id = ?";
        List<Participant> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, batchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    private Participant mapRow(ResultSet rs) throws SQLException {
        Participant p = new Participant();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setEmail(rs.getString("email"));
        p.setPhone(rs.getString("phone"));
        int batchId = rs.getInt("batch_id");
        if (rs.wasNull()) p.setBatchId(null);
        else p.setBatchId(batchId);
        p.setEnrolledDate(rs.getDate("enrolled_date"));
        return p;
    }
}
