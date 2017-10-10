package org.jo.demo;

import javafx.scene.input.DataFormat;
import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String now = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        req.setAttribute("currentTime", now);
        req.setAttribute("name", "Neil");
        req.getRequestDispatcher("/jsp/hello.jsp").forward(req, resp);
    }
}
