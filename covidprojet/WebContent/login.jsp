<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/site.css">
   <link rel="stylesheet" href="css/style.css">
  <link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
   <link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
 
  <script src="js/jquery.js"></script>
  <script src="js/bootstrap.min.js"></script>
 <style>
    body{
      padding-top: 10px;
    }
    </style>
</head>
<body> 
<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">
<nav class="navbar navbar-default navbar-fixed-top" style="background-color:#444;">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#" style=" font-style:italic;font-size:40px;">Covid-19</a>
      </div>
       <div class="collapse navbar-collapse" id="myNavbar">
           <ul class="nav navbar-nav navbar-right">
           
         <li class="selected" onclick="function()" ><a href="index" style=" font-style:italic;font-size:15px; opacity:0.9;color:white; letter-spacing: 4px;"> Accueil</a></li>
       
      </ul>
    </div>
   
  
 </div>
 
</nav>


<section class="login-block">
    <div class="container">
	<div class="row">
		<div class="col-md-10 login-sec">
		    <h2 class="text-center">Authentification</h2>
        <form action="admin" method="post">

            <div class="form-group">
                <label for="">login</label>
                <input type="text" name="login" class="form-control" />
            </div>


            <div class="form-group">
                <label for="">mot de passe   </label>
                <input type="password" name="password" class="form-control" />
            </div>



            <button type="submit" class="btn btn-primary">connexion</button>
        </form>
       
		</div>
		
		
</div>
<h3 style="color:red;">${erreur }</h3>
  </section>


 
  <br> 
    
          
       
<footer class="text-center">





  <a class="up-arrow" href="#myPage" data-toggle="tooltip" title="TO TOP">
    <span class="glyphicon glyphicon-chevron-up"></span>
  </a><br><br>
  <p>rejoingez-nous sur!<br>tel:0783881980<br>mail:www.covid.fr</p> 
      
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