package org.apache.jsp.catalog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/includes/header.jsp", out, false);
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/includes/column_left_catalog.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("<!-- start the middle column -->\n");
      out.write("\n");
      out.write("<!-- If necessary, this page could be generated from the database. -->\n");
      out.write("\n");
      out.write("<section>\n");
      out.write("    <h1>The Fresh Corn Records Catalog</h1>\n");
      out.write("\n");
      out.write("    <h2>86 (the band)</h2>\n");
      out.write("    <p><a href=\"product/8601\">True Life Songs and Pictures</a></p>\n");
      out.write("\n");
      out.write("    <h2 class=\"top_margin\">Paddlefoot</h2>\n");
      out.write("    <p><a href=\"product/pf01\">Paddlefoot (the first album)</a></p>\n");
      out.write("    <p><a href=\"product/pf02\">Paddlefoot (the second album)</a></p>\n");
      out.write("\n");
      out.write("    <h2 class=\"top_margin\">Joe Rut</h2>\n");
      out.write("    <p><a href=\"product/jr01\">Genuine Wood Grained Finish</a></p>    \n");
      out.write("</section>\n");
      out.write("\n");
      out.write("<!-- end the middle column -->\n");
      out.write("\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/includes/column_right_news.jsp", out, true);
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/includes/footer.jsp", out, false);
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
