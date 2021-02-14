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
            url = RegisterAdmin(request, response);
//                    response.sendRedirect(url);
        } else if (requestURI.endsWith("/mylogin")) {
            try {
                url = gotoJLogin(request, response);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        String requestURI = request.getRequestURI();
        String url = "/admin";
        if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        } else if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
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

    private boolean validUser(Admin admin) {
        errorMessage = "";
        boolean validPassword = false;
        boolean avaibleUsername = false;
        try {
            PasswordUtil.checkPasswordStrength(admin.getPassword());
            validPassword = true;
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        //check that Username  doesn't already exist
        if (!AdminDB.UsernameExists(admin.getUsername())) {
            avaibleUsername = true;
        } else {
            if (!validPassword) {
                errorMessage += "<br />";
            }
            errorMessage += "<br />" + "This Username:`" + admin.getUsername() + "` is in use." + "<br />";
        }
        return validPassword && avaibleUsername;
    }

    String errorMessage;

    private String RegisterAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Username = request.getParameter("Username");
        String Password = request.getParameter("Password");
       // String Rolename = request.getParameter("Rolename");

        Admin admin = new Admin();
        admin.setUsername(Username);
        admin.setPassword(Password);
        admin.setRolename("Administrator");

        String message = "";

        boolean isValidUser = validUser(admin);
        if (isValidUser) {
            try {
                AdminDB.Register(admin);
                message = "It worked";
            } catch (Exception ex) {
                message += ex.getMessage();
            }
        } else {
            message = errorMessage;
        }
        request.setAttribute("message", message);
        return "/admin/RegisterAdmin.jsp";
    }

    public String gotoJLogin(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException, NoSuchAlgorithmException {

        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        String hashAndSaltPassword = PasswordUtil.hashAndSaltPassword(password);
        return "/musicStore/j_security_check?j_username=" + username + "&j_password=" + hashAndSaltPassword + "&submit=Login";
    }
}
