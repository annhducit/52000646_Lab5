package SERVLET;

import COMPONENT.IdAutoGenerral;
import DAO.UserDAO;
import MODEL.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null
                && session.getAttribute("userId") != null
        ) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username") != null
                ? req.getParameter("username") : "";
        String password = req.getParameter("password") != null
                ? req.getParameter("password") : "";
        String confirmPassword = req.getParameter("confirmPassword") != null
                ? req.getParameter("confirmPassword") : "";

        req.setAttribute("username", username);

        boolean isValid = true;
        if (username.length() == 0) {
            req.setAttribute("flashMessage", "Please enter username");
            isValid = false;
        } else if (password.length() < 6) {
            req.setAttribute("flashMessage", "Password must contains 6 characters");
            isValid = false;
        } else if (confirmPassword.length() == 0) {
            req.setAttribute("flashMessage", "Please enter confirm password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            req.setAttribute("flashMessage", "Confirm password does not match");
            isValid = false;
        }
        if (!isValid) {
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (userDAO.getUserByUsername(username) != null) {
            req.setAttribute("flashMessage", "Account already exists");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        String userId = IdAutoGenerral.generateUserId();
        boolean isCreated = userDAO.create(new User(userId, username, password));
        if (!isCreated) {
            req.setAttribute("flashMessage", "An error occurs");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("/login");
    }
}
