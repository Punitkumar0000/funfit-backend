package com.funfit.servlet;

import com.funfit.model.Participant;
import com.funfit.repository.Repository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;


public class ParticipantServlet extends HttpServlet {

    private Repository repo = new Repository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();  // /, /5 or null
        String batchIdParam = req.getParameter("batchId");

        try {
            // Filter participants by batch
            if (batchIdParam != null) {
                int batchId = Integer.parseInt(batchIdParam);
                List<Participant> participants = repo.getParticipantDAO().findByBatchId(batchId);
                req.setAttribute("participants", participants);
                req.getRequestDispatcher("/jsp/listParticipants.jsp").forward(req, resp);
                return;
            }

            // GET /participants → list all
            if (pathInfo == null || "/".equals(pathInfo)) {
                List<Participant> list = repo.getParticipantDAO().findAll();
                req.setAttribute("participants", list);
                req.getRequestDispatcher("/jsp/listParticipants.jsp").forward(req, resp);
                return;
            }

            // GET /participants/{id} → load update form
            int id = Integer.parseInt(pathInfo.substring(1));
            Optional<Participant> p = repo.getParticipantDAO().findById(id);

            if (p.isPresent()) {
                req.setAttribute("participant", p.get());
                req.getRequestDispatcher("/updateParticipant.html").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Participant not found");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // CREATE + METHOD OVERRIDE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 🔥 STEP 10 — Method override handling
        String override = req.getParameter("_method");
        if (override != null) {
            if (override.equalsIgnoreCase("PUT")) {
                doPut(req, resp);
                return;
            }
            if (override.equalsIgnoreCase("DELETE")) {
                doDelete(req, resp);
                return;
            }
        }
        // END override logic


        // CREATE new participant
        try {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String batchIdStr = req.getParameter("batch_id");
            String enrolledDateStr = req.getParameter("enrolled_date");

            Integer batchId = (batchIdStr == null || batchIdStr.isEmpty()) ? null : Integer.parseInt(batchIdStr);
            Date enrolled = (enrolledDateStr == null || enrolledDateStr.isEmpty()) ? null : Date.valueOf(enrolledDateStr);

            Participant p = new Participant();
            p.setName(name);
            p.setEmail(email);
            p.setPhone(phone);
            p.setBatchId(batchId);
            p.setEnrolledDate(enrolled);

            repo.getParticipantDAO().save(p);

            resp.sendRedirect(req.getContextPath() + "/participants");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // UPDATE participant using PUT
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /5
        if (pathInfo == null || "/".equals(pathInfo)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String batchIdStr = req.getParameter("batch_id");
            String enrolledDateStr = req.getParameter("enrolled_date");

            Integer batchId = (batchIdStr == null || batchIdStr.isEmpty()) ? null : Integer.parseInt(batchIdStr);
            Date enrolled = (enrolledDateStr == null || enrolledDateStr.isEmpty()) ? null : Date.valueOf(enrolledDateStr);

            Participant p = new Participant();
            p.setId(id);
            p.setName(name);
            p.setEmail(email);
            p.setPhone(phone);
            p.setBatchId(batchId);
            p.setEnrolledDate(enrolled);

            repo.getParticipantDAO().update(p);

            resp.sendRedirect(req.getContextPath() + "/participants");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // DELETE participant using DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /5

        try {
            if (pathInfo == null || "/".equals(pathInfo)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            boolean removed = repo.getParticipantDAO().delete(id);

            if (removed) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

   // }
//}
