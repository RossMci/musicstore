package music.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.PasswordUtil;

public class LoginController extends HttpServlet {

//    public LoginController() {
//        System.out.println("building");
//    }
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/mylogin")) {
            url = gotoJLogin(request, response);
            response.sendRedirect(url);
            return;
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        //  System.out.println("User Controller.doGet");
        // System.err.println("User Controller.doGet");
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/mylogin")) {
            // System.out.println("Login custom");
            url = gotoJLogin(request, response);
//            response.sendRedirect(url);
//            return;
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    public String gotoJLogin(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        System.out.println(username);
        System.out.println(password);
        try {
            String HashPassword = PasswordUtil.hashPassword(password);
            password = HashPassword;
//        password = hashPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        String requestURI = request.getRequestURI();
        String url = "/musicStore/j_security_check?j_username=" + username + "&j_password=" + password + "&submit=Login";

//        getServletContext()
//                .getRequestDispatcher(url)
//                .forward(request, response);
        return "/musicStore/j_security_check?j_username=" + username + "&j_password=" + password + "&submit=Login";
    }

    private String hashPassword(String password) {
        return password + "n";
    }
}
