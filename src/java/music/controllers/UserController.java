package music.controllers;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import music.business.User;
import music.data.UserDB;
import acount.CheckPasswordServlet;

public class UserController extends HttpServlet {

    //post to loginServlet(
    //has the password
    //redirect to j_security_check
    //attach two post variables j_username, j_password
    //alt attach two url variables j_username=?&j_password=hassedpw
    //execute the request
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        //  System.out.println("User Controller.doGet");
        // System.err.println("User Controller.doGet");
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/deleteCookies")) {
            url = deleteCookies(request, response);
        } else if (requestURI.endsWith("/mylogin")) {
            // System.out.println("Login custom");
            url = gotoJLogin(request, response);
            response.sendRedirect(url);
            return;
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
        

        //  System.out.println(username);
        //System.out.println(password);
//        password = HashExample.main(password);
//        String salt="";
//        salt=getSalt();
//        password=hashPassword(password);
//        System.out.println(password);
        return "/musicStore/j_security_check?j_username=" + username + "&j_password=" + password + "&submit=Login";
    }

    
    private String hashPassword(String password) {
        return HashExample.main(password);
//        return password + "n";// adding n;
    }
    
        public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/subscribeToEmail")) {
            url = subscribeToEmail(request, response);
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String deleteCookies(HttpServletRequest request,
            HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
        for (Cookie cookie : cookies) {
            cookie.setPath("/musicStore");//musicStore
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

//        }
        return "/delete_cookies.jsp";
    }

    private String subscribeToEmail(HttpServletRequest request,
            HttpServletResponse response) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        request.setAttribute("user", user);

        String url;
        String message;
        //check that email address doesn't already exist
        if (UserDB.emailExists(email)) {
            message = "This email address already exists. <br>"
                    + "Please enter another email address.";
            request.setAttribute("message", message);
            url = "/email/index.jsp";
        } else {
            UserDB.insert(user);
            message = "";
            request.setAttribute("message", message);
            url = "/email/thanks.jsp";
        }
        return url;
    }
    
    public String saltPassword(String password){
    MessageDigest md;
        try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");
//                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Generate the random salt
            SecureRandom random = new SecureRandom(); //SecureRandom();
            byte[] salt = new byte[32];
            random.nextBytes(salt);

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

//        return"";
}
}
//https://www.javainterviewpoint.com/java-salted-password-hashing/
//


class HashExample
{
    public static String main(String password)
    {

        MessageDigest md;
        try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Generate the random salt
//            SecureRandom random = new SecureRandom(); //SecureRandom();
            byte[] salt = new byte[32];
            random.nextBytes(salt);

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

