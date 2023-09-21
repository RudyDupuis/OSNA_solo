package fr.eni.OSNA.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.OSNA.bll.ArticleManager;
import fr.eni.OSNA.bo.Article;
import fr.eni.OSNA.bo.User;

@WebServlet("")
public class home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager articleManager = ArticleManager.getInstance();
	    
		if(request.getAttribute("articles") != null) {
			request.setAttribute("articles", request.getAttribute("articles"));
		} else {
			try {
				List<Article> articles = articleManager.selectAll();
				
				for (Article article : articles) {
					boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
					article.setExpiredDate(isBeforeNow);
				}
				
				request.setAttribute("articles", articles);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
	    request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userSession = (User) request.getSession().getAttribute("user");
		int userId = userSession.getId();
		ArticleManager articleManager = ArticleManager.getInstance();
		List<Article> articles = null;
		
		if(request.getParameter("action").equals("firstFilter")){
			if(!request.getParameter("filterSearch").isEmpty()) {
				try {
					articles = articleManager.selectByKeyword(request.getParameter("filterSearch"));
					
					for (Article article : articles) {
						boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
						article.setExpiredDate(isBeforeNow);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if(!request.getParameter("categorie").equals("all")) {
					try {
						articles = articleManager.selectByCategorie(request.getParameter("categorie"));
						
						for (Article article : articles) {
							boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
							article.setExpiredDate(isBeforeNow);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(request.getParameter("action").equals("currentAuction")){
			try {
				articles = articleManager.selectUserCurrentAuction(userId);
				
				for (Article article : articles) {
					boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
					article.setExpiredDate(isBeforeNow);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("action").equals("wonAuction")){
			try {
				articles = articleManager.selectUserWonAuction(userId);
				
				for (Article article : articles) {
					boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
					article.setExpiredDate(isBeforeNow);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("action").equals("mySales")){
			try {
				articles = articleManager.selectUserSales(userId);
				
				for (Article article : articles) {
					boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
					article.setExpiredDate(isBeforeNow);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("action").equals("myEndedSales")){
			try {
				articles = articleManager.selectUserEndedSales(userId);
				
				for (Article article : articles) {
					boolean isBeforeNow = article.getEndDate().isBefore(LocalDate.now());
					article.setExpiredDate(isBeforeNow);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("articles", articles);
		
		doGet(request, response);
	}

}
