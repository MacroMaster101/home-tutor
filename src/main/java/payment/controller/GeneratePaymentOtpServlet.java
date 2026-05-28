package payment.controller;

import student.model.Student;
import student.utils.StudentFileUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet("/GeneratePaymentOtpServlet")
public class GeneratePaymentOtpServlet extends HttpServlet {

    // Handles POST requests from the payment form
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get current session and logged-in user's username
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Get course ID and selected payment method from the request
        String courseId = request.getParameter("courseId");
        String method = request.getParameter("method");

        // Get entered card details (dummy usage; not stored permanently)
        String cardNumber = request.getParameter("cardNumber");
        // Read the student's phone number from students.txt
        // (needed to simulate where OTP would be sent)
        String studentPath = getServletContext().getRealPath("/WEB-INF/students.txt");
        String phoneNumber = "";

        for (Student student : StudentFileUtil.readStudents(studentPath)) {
            if (username != null && username.equals(student.getUserName())) {
                phoneNumber = student.getPhone();
                break;
            }
        }

        // Generate a random 4-digit OTP (between 1000 and 9999)
        int otp = new Random().nextInt(9000) + 1000;

        // Store required values in the session to use in the OTP verification step
        session.setAttribute("paymentOtp", otp);                  // Store the generated OTP
        session.setAttribute("paymentCourseId", courseId);        // Store the selected course ID
        session.setAttribute("paymentMethod", method);            // Store the selected payment method

        // Save a masked version of the card number to show on confirmation screen
        if (cardNumber != null && cardNumber.length() >= 4) {
            session.setAttribute("maskedCard", "**** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        } else {
            session.setAttribute("maskedCard", "selected card");
        }

        // Simulate sending the OTP by printing it to the server console
        System.out.println("[OTP for payment - " + username + "]: " + otp + " sent to phone " + phoneNumber);

        // Redirect to the OTP verification page
        response.sendRedirect("verifyPaymentOtp.jsp");
    }
}
