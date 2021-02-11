package music.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {

    public LoginController() {
        System.out.println("building");
    }

    
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        System.out.println(username);
        System.out.println(password);
        password = hashPassword(password);

        String requestURI = request.getRequestURI();
        String url = "/j_security_check?j_username=" + username + "&j_password=" + password + "&submit=Login";

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    

    private String hashPassword(String password) {
        return password + "n";
    }
}
