<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>ajouter</title>
     <link type="text/css" rel="stylesheet" href="css/admin.css" />
      
 <link rel="stylesheet" href="css/bootstrap.min.css">
   <link rel="stylesheet" href="css/formation1.css">
  <link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
   <link rel="stylesheet" href="fonts/glyphicons-halflings-regular.ttf">
 
  <script src="js/jquery.js"></script>
  <script src="js/bootstrap.min.js"></script>
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
      <a class="navbar-brand" href="#" style=" font-style:italic;font-size:30px;">Covid19</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
      
          
                       
    

           
         <li class="selected" onclick="function()"> <a href="Admin">DECONNEXION </a></li>
             
                 
         
         
     
         
        
        
        
       
      </ul>
    </div>
  </div>
</nav>

      <br>
      <h4 ><span class="glyphicon glyphicon-chevron-left"></span><a href="retour"> Accueil admin</a></h4> 
          
       <br>
<br><br><br>
<h2>Ajouter une activité </h2>

 
    <form method="post" action="ajout">
    
            <label for="id" > id activité </label>
            <input type="text" name="id" id="id"   required/>
        
        
          <label for="dateactivite"> Date d'activité </label>
            <input type="date" name="date" id="date" required/>
            
            
            <label for="hdebut"> Heure debut  </label>
            <input type="text" name="hdebut" id="hdebut" required/>
       
            <label for="hfin"> Heure fin  </label>
            <input type="text" name="hfin" id="hfin" required/>
     
           <label for="name"> Nom : </label>
            <input type="text" name="name" id="name" required/>
        
            
         
          <button type="submit">Ajouter</button>
       
    </form>
   
    <br>
      <h4 ><span class="glyphicon glyphicon-chevron-left"></span><a href="retour"> Accueil admin</a></h4> 
          
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