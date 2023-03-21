package SERVLET;

import DAO.UserDAO;
import MODEL.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("userId")) {
                    String userId = cookie.getValue();
                    User user = userDAO.read(userId);
                    if (user != null) {
                        session.setAttribute("userId", userId);
                    }
                    break;
                }
            }
        }

        if (session != null
                && session.getAttribute("userId") != null
        ) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") != null
                ? req.getParameter("username") : "";
        String password = req.getParameter("password") != null
                ? req.getParameter("password") : "";
        boolean rememberMe = req.getParameter("rememberMe") != null
                && req.getParameter("rememberMe").equals("on");

        req.setAttribute("username", username);

        boolean isValid = true;
        if (username.length() == 0) {
            req.setAttribute("flashMessage", "Please enter username");
            isValid = false;
        } else if (password.length() == 0) {
            req.setAttribute("flashMessage", "Please enter password");
            isValid = false;
        } else if (password.length() < 6) {
            req.setAttribute("flashMessage", "Password must more than 6 characters");
            isValid = false;
        }
        if (!isValid) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }


        User user = userDAO.getUserByUsername(username);
        String hashPass = user != null
                ? user.getPassword()
                : "";
        if (!password.equals(hashPass)) {
            req.setAttribute("flashMessage", "Username or password incorrect");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if (rememberMe) {
            Cookie cookie = new Cookie("userId", user.getId());
            cookie.setMaxAge(60 * 60 * 24 * 30);
            resp.addCookie(cookie);
        }

        HttpSession session = req.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        resp.sendRedirect("/");
    }
}
