package fr.eni.OSNA.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.OSNA.bll.ArticleManager;
import fr.eni.OSNA.bll.UserManager;
import fr.eni.OSNA.bo.Article;
import fr.eni.OSNA.bo.User;

@WebServlet("/article")
public class article extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager articleManager = ArticleManager.getInstance();
		UserManager userManager = UserManager.getInstance();
		Article article = null;
		
		if(request.getParameter("articleId") != null) {
			int articleId = Integer.valueOf(request.getParameter("articleId"));
			
			try {
				article = articleManager.selectById(articleId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				article.setNameSeller(userManager.getPseudo(article.getIdSeller()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(article.getIdUserBestOffer() != 0) {
				try {
					article.setNameUserBestOffer(userManager.getPseudo(article.getIdUserBestOffer()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		request.setAttribute("article", article);
		
		request.getRequestDispatcher("/WEB-INF/Article.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userSession = (User) request.getSession().getAttribute("user");
		int userId = userSession.getId();
		int points = Integer.valueOf(request.getParameter("points"));
		int articleId = Integer.valueOf(request.getParameter("articleId"));
		
		ArticleManager articleManager = ArticleManager.getInstance();
		
		try {
			articleManager.updateOffer(points, userId, articleId);
			request.setAttribute("message", "Vous avez fait une offre de " + points + " points");
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		
		doGet(request, response);
	}

}
