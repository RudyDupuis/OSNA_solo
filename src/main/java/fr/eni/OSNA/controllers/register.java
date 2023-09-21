package fr.eni.OSNA.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.OSNA.bll.UserManager;
import fr.eni.OSNA.bo.User;

@WebServlet("/inscription")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check password
		if(!request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
			errorDirection(request, response, "Les deux mots de passe ne sont pas identiques");
		} else {

			User user = new User(
					request.getParameter("firstName"),
					request.getParameter("lastName"),
					request.getParameter("pseudo"),
					request.getParameter("mail"),
					request.getParameter("phone"),
					request.getParameter("street"),
					Integer.valueOf(request.getParameter("postalCode")),
					request.getParameter("city"),
					request.getParameter("password")
					);
			
			UserManager userManager = UserManager.getInstance();
			
			try {
				userManager.insert(user);
				User userConnected = userManager.login(user.getPseudo(), user.getPassword());
				request.getSession().setAttribute("user", userConnected);
				response.sendRedirect(request.getContextPath() + "/");
			} catch (Exception e) {
				errorDirection(request, response, e.getMessage());
			}
		}
		
	}
	
	private void errorDirection(HttpServletRequest request,  HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("firstNameSave", request.getParameter("firstName"));
		request.setAttribute("lastNameSave", request.getParameter("lastName"));
		request.setAttribute("pseudoSave", request.getParameter("pseudo"));
		request.setAttribute("mailSave", request.getParameter("mail"));
		request.setAttribute("phoneSave", request.getParameter("phone"));
		request.setAttribute("streetSave", request.getParameter("street"));
		request.setAttribute("postalCodeSave", request.getParameter("postalCode"));
		request.setAttribute("citySave", request.getParameter("city"));
		
		request.setAttribute("message", message);
		
		doGet(request, response);
	}

}
