package fr.eni.OSNA.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;

import fr.eni.OSNA.bll.ArticleManager;
import fr.eni.OSNA.bo.Article;
import fr.eni.OSNA.bo.User;

@WebServlet("/vendre-un-article")
@MultipartConfig(maxFileSize = 1024 * 1024)
public class sell extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleManager articleManager = ArticleManager.getInstance();
		Article article = null;
		int articleId = 0;
		
		if(request.getAttribute("articleId") != null) {
			articleId = Integer.valueOf((int) request.getAttribute("articleId"));
		} else {
			if(request.getParameter("articleId") != null) {
				articleId = Integer.valueOf(request.getParameter("articleId"));
			}	
		}
		
		
		if(articleId != 0) {
			try {
				article = articleManager.selectById(articleId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("article", article);
		
		request.getRequestDispatcher("/WEB-INF/Sell.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userSession = (User) request.getSession().getAttribute("user");
		int userId = userSession.getId();
		
		ArticleManager articleManager = ArticleManager.getInstance();
		
		if(request.getParameter("action").equals("delete")){
			int articleId = Integer.valueOf(request.getParameter("articleId"));
			request.setAttribute("articleId", articleId);
			
			if(request.getParameter("sure").equals("true")) {
				
				try {
					articleManager.delete(articleId);
					doGet(request, response);
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());
					doGet(request, response);
				}
			} else {
				request.setAttribute("message", "Êtes-vous sûr de vouloir supprimer cet article ?");
				request.setAttribute("sure", "true");
				doGet(request, response);
			}
		} else {
			// image management
	        Part filePart = request.getPart("image");

	        byte[] fileBytes = new byte[(int) filePart.getSize()];
	        try (InputStream inputStream = filePart.getInputStream()) {
	            inputStream.read(fileBytes);
	        }

	        Blob imageBlob = null;
	        try {
	            imageBlob = new SerialBlob(fileBytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			if(request.getParameter("action").equals("create")){
				Article article = new Article(
						userId,
						request.getParameter("name"),
						request.getParameter("categorie"),
						imageBlob,
						request.getParameter("description"),
						Integer.valueOf(request.getParameter("startingPrice")),
						LocalDate.parse(request.getParameter("startDate")),
						LocalDate.parse(request.getParameter("endDate")),
						request.getParameter("street"),
						Integer.valueOf(request.getParameter("postalCode")),
						request.getParameter("city")
						);
				
				try {
					articleManager.insert(article);
					request.setAttribute("articleId", article.getId());
					request.setAttribute("message", "Votre article a été ajouté");
					doGet(request, response);
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());
					doGet(request, response);
				}
			}
			
			if(request.getParameter("action").equals("update")){
				int articleId = Integer.valueOf(request.getParameter("articleId"));
				request.setAttribute("articleId", articleId);
				
				Article article = new Article(
						articleId,
						userId,
						request.getParameter("name"),
						request.getParameter("categorie"),
						imageBlob,
						request.getParameter("description"),
						Integer.valueOf(request.getParameter("startingPrice")),
						LocalDate.parse(request.getParameter("startDate")),
						LocalDate.parse(request.getParameter("endDate")),
						request.getParameter("street"),
						Integer.valueOf(request.getParameter("postalCode")),
						request.getParameter("city")
						);
				
				try {
					articleManager.update(article);
					request.setAttribute("message", "Votre article a été modifié");
					doGet(request, response);
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());
					doGet(request, response);
				}
			}
			
		}
				
	}

}
