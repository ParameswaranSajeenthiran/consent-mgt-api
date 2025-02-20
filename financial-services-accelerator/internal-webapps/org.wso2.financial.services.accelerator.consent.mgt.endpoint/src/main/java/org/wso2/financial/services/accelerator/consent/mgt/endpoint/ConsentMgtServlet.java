//package org.wso2.financial.services.accelerator.consent.mgt.endpoint;
//
//import java.io.IOException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Servlet implementation class ConsentMgtServlet
// */
//public class ConsentMgtServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    private String message;
//
//    public void init() throws ServletException {
//        // Do required initialization
//        message = "Hello World";
//    }
//
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        // Set response content type
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);
//
//
//
//    }
//
//    public void destroy() {
//        // do nothing.
//    }
//
//}
