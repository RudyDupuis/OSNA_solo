<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>OSNA - Mon profil</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/STYLE/CSS/settings.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/STYLE/CSS/myProfile.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<jsp:include page="Componants/Nav.jsp">
		<jsp:param value="true" name="connection" />
	</jsp:include>
	
	<h1>Mon Profil</h1>
	
	<p class="points"><strong>Mes points : </strong>${sessionScope.user.points} points</p>
	
	<form method="POST" action="" class="update-form">
		<div>
			<h2>Nom et Prénom</h2>
			<div>
				<input type="text" placeholder="Prénom" name="firstName" value="${sessionScope.user.firstName}" required />
				<input type="text" placeholder="Nom" name="lastName" value="${sessionScope.user.lastName}" required />
			</div>
			
			<h2>Pseudo, Mail et Téléphone</h2>
			<div>
				<input type="text" placeholder="Pseudo" name="pseudo" value="${sessionScope.user.pseudo}" required/>
				<input type="email" placeholder="Mail" name="mail" value="${sessionScope.user.mail}" required/>
				<input type="text" placeholder="Téléphone" name="phone" value="${sessionScope.user.phone}" />
			</div>
			
			<h2>Adresse</h2>
			<div>
				<input type="text" placeholder="Rue" name="street" value="${sessionScope.user.street}" required />
				<input type="number" placeholder="Code Postal" name="postalCode" value="${sessionScope.user.postalCode}" required />
				<input type="text" placeholder="Ville" name="city" value="${sessionScope.user.city}" required />
			</div>
			
			<h2>Mot de passe</h2>
			<div>
				<input type="password" placeholder="Mot de passe" name="password" required />
				<input type="password" placeholder="Confirmer le mot de passe" name="confirmPassword" required />
			</div>
		</div>
		
		<input type="hidden" value="update"  name="action"/>
		<input type="submit" value="Enregister" class="update-form-submit"/>
	</form>
	
	<section class="other-btns">
		<form method="POST" action="">
			<input type="hidden" value="disconnection"  name="action"/>
			<input type="submit" value="Se déconnecter" />
		</form>
		<form method="POST" action="">
			<input type="hidden" value="${sure}"  name="sure"/>
			<input type="hidden" value="delete"  name="action"/>
			<input type="submit" value="Supprimer mon compte" class="other-btns-delete" />
		</form>
	</section>
	
	<p class="message">${message}</p>
	
	<jsp:include page="Componants/Footer.jsp" />
</body>
</html>