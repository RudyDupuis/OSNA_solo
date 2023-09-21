<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>OSNA - Vendre un article</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/STYLE/CSS/settings.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/STYLE/CSS/sell.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<jsp:include page="Componants/Nav.jsp">
		<jsp:param value="true" name="connection" />
	</jsp:include>
	
	<c:if test="${article == null}">
		<h1>Vendre un article</h1>
	</c:if>
	<c:if test="${article != null}">
		<h1>Modifier un article</h1>
	</c:if>
	
	<form method="POST" action="" enctype="multipart/form-data">
		<div class="form-top">
			<div class="img"></div>
			
			<div class="form-top-center">
				<input type="text" placeholder="Nom de l'article" name="name" value="${article.name}" required/>
				
				<select name="categorie" required>
  					<option value="home" ${article.categorie == 'home' ? 'selected' : ''}>Maison</option>
  					<option value="garden" ${article.categorie == 'garden' ? 'selected' : ''}>Jardin</option>
  					<option value="electronics" ${article.categorie == 'electronics' ? 'selected' : ''}>Électronique</option>
  					<option value="clothing" ${article.categorie == 'clothing' ? 'selected' : ''}>Vêtements</option>
				</select>

				
				<input type="file" placeholder="Ajouter une image" name="image" required/>
			</div>
			
			<textarea placeholder="Description" name="description" required>${article.description}</textarea>
		</div>
		
		<div class="form-center">
			<input type="text" placeholder="Mise à prix" name="startingPrice" value="${article.startingPrice}" required/>
			<input type="date" placeholder="Date de début" name="startDate" value="${article.startDate}" required/>
			<input type="date" placeholder="Date de fin" name="endDate" value="${article.endDate}" required/>
		</div>
		
		<div>
			<h2>Retrait</h2>
			<input type="text" placeholder="Rue" value="${article.street != null ? article.street : sessionScope.user.street}" name="street" required/>
			<input type="number" placeholder="Code Postal" value="${article.postalCode != 0 ? article.postalCode : sessionScope.user.postalCode}" name="postalCode" required/>
			<input type="text" placeholder="Ville" name="city" value="${article.city != null ? article.city : sessionScope.user.city}" required/>
		</div>
		
		<input type="hidden" value="${article.id}" name="articleId">

		<c:if test="${article == null}">
			<input type="hidden" value="create" name="action">
			<input type="submit" value="Créer la vente" class="form-btn"/>
		</c:if>
			
		<c:if test="${article != null}">
			<input type="hidden" value="update" name="action">
			<input type="submit" value="Modifier la vente" class="form-btn"/>
		</c:if>
	</form>
	
	<c:if test="${article != null}">
		<form action="" method="POST">
			<input type="hidden" value="${article.id}" name="articleId">
			<input type="hidden" value="delete" name="action">
			<input type="hidden" value="${sure != null ? sure : false }" name="sure">
			<input type="submit" value="Annuler la vente" class="btn-delete"/>
		</form>
	</c:if>
	
	<p class="message">${message}</p>
	
	<jsp:include page="Componants/Footer.jsp" />
</body>
</html>