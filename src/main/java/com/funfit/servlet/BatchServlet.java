package com.funfit.servlet;

import com.funfit.model.Batch;
import com.funfit.repository.Repository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;


public class BatchServlet extends HttpServlet {

    private Repository repo = new Repository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // null or /{id}

        try {
            if (pathInfo == null || "/".equals(pathInfo)) {
                List<Batch> batches = repo.getBatchDAO().findAll();
                req.setAttribute("batches", batches);
                req.getRequestDispatcher("/jsp/listBatches.jsp").forward(req, resp);

            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Optional<Batch> b = repo.getBatchDAO().findById(id);

                if (b.isPresent()) {
                    req.setAttribute("batch", b.get());
                    req.getRequestDispatcher("/updateBatch.html").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Batch not found");
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // Create + Update + Delete (override)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 🔥 STEP 10: METHOD OVERRIDE LOGIC GOES HERE
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
        // 🔥 END OF METHOD OVERRIDE LOGIC


        // Normal CREATE logic
        String name = req.getParameter("name");
        String start = req.getParameter("start_time");
        String end = req.getParameter("end_time");
        String description = req.getParameter("description");

        try {
            Time startTime = (start == null || start.isEmpty()) ? null : Time.valueOf(start);
            Time endTime = (end == null || end.isEmpty()) ? null : Time.valueOf(end);

            Batch b = new Batch();
            b.setName(name);
            b.setStartTime(startTime);
            b.setEndTime(endTime);
            b.setDescription(description);

            repo.getBatchDAO().save(b);
            resp.sendRedirect(req.getContextPath() + "/batches");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 🔥 UPDATE using PUT (works through override from POST)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /{id}
        if (pathInfo == null || "/".equals(pathInfo)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing ID");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));

            String name = req.getParameter("name");
            String start = req.getParameter("start_time");
            String end = req.getParameter("end_time");
            String description = req.getParameter("description");

            Time startTime = (start == null || start.isEmpty()) ? null : Time.valueOf(start);
            Time endTime = (end == null || end.isEmpty()) ? null : Time.valueOf(end);

            Batch b = new Batch();
            b.setId(id);
            b.setName(name);
            b.setStartTime(startTime);
            b.setEndTime(endTime);
            b.setDescription(description);

            repo.getBatchDAO().update(b);

            resp.sendRedirect(req.getContextPath() + "/batches");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /{id}

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            boolean removed = repo.getBatchDAO().delete(id);

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

    //}
//}

