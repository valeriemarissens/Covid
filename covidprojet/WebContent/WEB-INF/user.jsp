<%@ page pageEncoding="UTF-8" import="beans.User" import="java.util.List" import="bdd.Bdd"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8" />
 <title>Covid</title>
	<link type="text/css" rel="stylesheet" href="css/admin.css" />
    <link type="text/css" rel="stylesheet" href="css/formation1.css" />
 	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
	<link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
  
</head>

<body>
	<br><br><br>
	
	 <body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">
		<nav  class="navbar navbar-default navbar-fixed-top" style="background-color:#444;" >
		  <div class="container-fluid"  >
		    <div class="navbar-header" >
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar" >
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>                        
		      </button>
		      <a class="navbar-brand" href="#" style=" font-style:italic;font-size:30px; color: palevioletred; opacity:0.9; letter-spacing: 4px;">
		      	Covid19
		      </a>
		    </div>
		    <div class="collapse navbar-collapse" id="myNavbar">
		      <ul class="nav navbar-nav navbar-right">    
				<li class="selected" onclick="function()" ><a href="Login" style=" font-style:italic;font-size:15px; opacity:0.9;color:white; letter-spacing: 4px;">
					DECONNEXION
				</a></li>   
		      </ul>
		    </div>
		  </div>
		</nav>
	 
	<br><br><br>
		<div id="liste" class="row" style="margin-top:20px;margin-bottom:20px; border-bottom: 6px groove rgba(0, 153, 204,0.8);">
	        <div class="col-sm-4">
				<h1 style="margin:0;">Profil</h1>
	        </div>
	        <div class="col-sm-offset-1 col-sm-1" style="margin-right:15px;margin-left:750px;">      
				<a class="btn btn-primary" href="edit" >Modifier le profil</a>
	        </div>
	    </div>
	
	    <!-- Début informations : -->
	    <div class="row">
	        <div class="col text-center" style="font-size: 14px;">
	        	<% User user = (User) session.getAttribute("user"); %>
	            <p><c:out value="Login : "/><% out.print(user.getlogin()); %></p>
	            <p><c:out value="Nom : "/><% out.print(user.getlastname()); %></p>
	            <p><c:out value="Prénom : "/><% out.print(user.getfirstname()); %></p>
	            <p><c:out value="Date de naissance : "/><% out.print(user.getbirth()); %></p>
	            <p><c:out value="Covid positif/ve : "/><% out.print(user.getcovid()); %></p>
	            <p><c:out value="A risque : "/><% out.print(user.getatrisk()); %></p>
	        </div>
	    </div>
	    <!-- Fin informations -->	
	    
	  
	  <br><br>
	  
	  <!-- Chercheur -->
	  <p>
	  	Chercher des amis : <input type="search" name="chercheAmis" placehorder="Écire le login." pattern ="[A-Za-z0-9_]{1,11}" title="Le login ne comporte que des lettres, chiffres ou tirets bas.">
	  	<input type="submit" value="Chercher">	  
	  </p>	  
	  <!-- Fin du chercheur -->
	  
	  
	  <br><br>
	  <!-- Amis -->
	  <h3 style="margin:0;">Liste d'amis :</h3>
	  <br>
		<table>
			<% List<User> friends = Bdd.getInstance().getFriends(); %>
			
			<tr>
				<th><c:out value="login"/></th>
				<th><c:out value="lastname"/></th>
				<th><c:out value="firstname"/></th>
				<th><c:out value="birthday"/></th>
				<th><c:out value="has covid ?"/></th>
				<th><c:out value="is at risk ?"/></th>
			</tr>
			
			
			<c:forEach var="user" items="${ friends }">   
				<tr>
				 <td><c:out value="${ user.getlogin() }" /></td>
				<td><c:out value="${ user.getlastname() }" /></td>
				<td><c:out value="${ user.getfirstname() }" /></td>
				<td><c:out value="${ user.getbirth() }" /></td>
				<td><c:out value="${ user.getcovid() }" /></td>
				<td><c:out value="${ user.getatrisk() }" /></td>			
				</tr>
			</c:forEach>
		</table>
    
    <!-- Fin amis -->
	    
	  <br><br><br><br><br> 
	  
	  
	       
	<footer class="text-center">
	
	  <a class="up-arrow" href="#myPage" data-toggle="tooltip" title="TO TOP">
	    <span class="glyphicon glyphicon-chevron-up"></span>
	  </a><br><br>
	  <p>rejoignez-nous sur: <br>tél: 0783881980<br>mail: www.covid.fr</p> 
	      
	      </footer>
	<script src="http://code.jquery.com/jquery.min.js"></script>
	
	<script>
	    
	$(document).ready(function(){
	  
	  // Initialize Tooltip
	  $('[data-toggle="tooltip"]').tooltip(); 
	  
	  // Add smooth scrolling to all links in navbar + footer link
	  $(".navbar a, footer a[href='#myPage']").on('click', function(event) {
	
	    // Make sure this.hash has a value before overriding default behavior
	    if (this.hash !== "") {
	
	      // Prevent default anchor click behavior
	      event.preventDefault();
	
	      // Store hash
	      var hash = this.hash;
	
	      // Using jQuery's animate() method to add smooth page scroll
	      // The optional number (900) specifies the number of milliseconds it takes to scroll to the specified area
	      $('html, body').animate({
	        scrollTop: $(hash).offset().top
	      }, 900, function(){
	   
	        // Add hash (#) to URL when done scrolling (default click behavior)
	        window.location.hash = hash;
	      });
	    } // End if
	  });
	})
	
	   
	
	
	</script>
</body>  
</body>
</html>