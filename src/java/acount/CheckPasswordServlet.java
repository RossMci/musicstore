package acount;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;

import util.PasswordUtil;

public class CheckPasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // get parameters from the request
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        // check strength requirements
        String message;
        try {
            PasswordUtil.checkPasswordStrength(password);
            message = "";
        } catch (Exception e) {
            message = e.getMessage();
        }
        request.setAttribute("message", message);        
        
        // hash and salt password
        String salt = "";
        String saltedAndHashedPassword;

        String hashedPassword;
        try {
            hashedPassword = PasswordUtil.hashPassword(password);
            salt = PasswordUtil.getSalt();
            saltedAndHashedPassword = PasswordUtil.hashAndSaltPassword(password);                    
            
        } catch (NoSuchAlgorithmException ex) {
            hashedPassword = ex.getMessage();
            saltedAndHashedPassword = ex.getMessage();
        }
        request.setAttribute("hashedPassword", hashedPassword);
        request.setAttribute("salt", salt);
        request.setAttribute("saltedAndHashedPassword", saltedAndHashedPassword);
        
//        String url = "/index.jsp";
//        getServletContext()
//                .getRequestDispatcher(url)
//                .forward(request, response);
    }    
}