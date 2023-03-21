package SERVLET;

import COMPONENT.JsonResponse;
import DAO.ProductDAO;
import MODEL.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check no login session
        HttpSession session = req.getSession();
        if (session == null
                || session.getAttribute("userId") == null
        ) {
            resp.sendRedirect("/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        List<Product> products = productDAO.readAll();
        req.setAttribute("username", username);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name") != null
                ? req.getParameter("name") : "";
        String priceStr = req.getParameter("price") != null
                ? req.getParameter("price") : "";

        req.setAttribute("name", name);

        boolean isValid = true;
        if (name.length() == 0) {
            req.setAttribute("flashMessage", "Please enter the name");
            isValid = false;
        } else if (priceStr == null) {
            req.setAttribute("flashMessage", "Price is null");
            isValid = false;
        }
        if (!isValid) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        double price = Double.parseDouble(priceStr);
        boolean isCreated = productDAO.create(new Product(name, price));
        if (!isCreated) {
            req.setAttribute("flashMessage", "Error");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("/");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String id = req.getParameter("id");

        resp.setContentType("application/json");

        if (id == null) {
            JsonResponse res = new JsonResponse(404, "Enter product Id", null);
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        boolean idDeleted = productDAO.delete(Integer.parseInt(id));
        if (!idDeleted) {
            JsonResponse res = new JsonResponse(404, "Error", null);
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        JsonResponse res = new JsonResponse(200, "Product delete successfully", null);
        resp.getWriter().write(gson.toJson(res));
    }
}