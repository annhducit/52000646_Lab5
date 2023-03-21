package SERVLET;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("isLoggedIn");
        session.invalidate();

        Cookie cookie = new Cookie("userId", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        resp.sendRedirect("/");
    }
}