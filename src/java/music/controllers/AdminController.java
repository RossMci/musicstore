package music.controllers;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import music.data.ReportDB;
import music.data.InvoiceDB;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import music.business.*;
import music.data.UserDB;
import music.data.AdminDB;
import util.PasswordUtil;

public class AdminController extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
        } else if (requestURI.endsWith("/processInvoice")) {
            url = processInvoice(request, response);
        } else if (requestURI.endsWith("/displayReport")) {
            displayReport(request, response);
        } else if (requestURI.endsWith("/RegisterAdmin")) {
            try {
                RegisterAdmin(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        } else if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
        } else if (requestURI.endsWith("/mylogin")) {
           url = gotoJLogin(request, response);
            response.sendRedirect(url);
            return;
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String displayInvoices(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Invoice> unprocessedInvoices
                = InvoiceDB.selectUnprocessedInvoices();

        String url;
        if (unprocessedInvoices != null) {
            if (unprocessedInvoices.size() <= 0) {
                unprocessedInvoices = null;
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("unprocessedInvoices", unprocessedInvoices);
        url = "/admin/invoices.jsp";
        return url;
    }

    private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();

        String invoiceNumberString = request.getParameter("invoiceNumber");
        int invoiceNumber = Integer.parseInt(invoiceNumberString);
        List<Invoice> unprocessedInvoices = (List<Invoice>) session.getAttribute("unprocessedInvoices");

        Invoice invoice = null;
        for (Invoice unprocessedInvoice : unprocessedInvoices) {
            invoice = unprocessedInvoice;
            if (invoice.getInvoiceNumber() == invoiceNumber) {
                break;
            }
        }

        session.setAttribute("invoice", invoice);

        return "/admin/invoice.jsp";
    }

    private String processInvoice(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        Invoice invoice = (Invoice) session.getAttribute("invoice");
        InvoiceDB.update(invoice);

        return "/adminController/displayInvoices";
    }

    private void displayReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String reportName = request.getParameter("reportName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Workbook workbook;
        if (reportName.equalsIgnoreCase("userEmail")) {
            workbook = ReportDB.getUserEmail();
        } else if (reportName.equalsIgnoreCase("downloadDetail")) {
            workbook = ReportDB.getDownloadDetail(startDate, endDate);
        } else {
            workbook = new HSSFWorkbook();
        }

        response.setHeader("content-disposition",
                "attachment; filename=" + reportName + ".xls");
        try (OutputStream out = response.getOutputStream()) {
            workbook.write(out);
        }
    }

    private void RegisterAdmin(HttpServletRequest request,
            HttpServletResponse response) throws NoSuchAlgorithmException {
        String Username = request.getParameter("Username");
        String Password = request.getParameter("Password");
        String Rolename = request.getParameter("Rolename");
        String HashedPassword = PasswordUtil.hashPassword(Password);
        Admin admin = new Admin();
        admin.setUsername(Username);
        admin.setPassword(HashedPassword);
        admin.setRolename(Rolename);
        String message;
        try {
            PasswordUtil.checkPasswordStrength(Password);
            message = "";
        } catch (Exception e) {
            message = e.getMessage();
        }

        request.setAttribute("message", message);

        request.setAttribute("admin", admin);

        String url;
        //check that email address doesn't already exist
//        if (UserDB.emailExists(email)) {
//            message = "This email address already exists. <br>"
//                    + "Please enter another email address.";
//            request.setAttribute("message", message);
//            url = "/email/index.jsp";
//        } else {
        AdminDB.Register(admin);
//            message = "";
//            request.setAttribute("message", message);
//            url = "/admin/RegisterAdmin.jsp";
//        }
//        return url;
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
}
