<%@ page pageEncoding="UTF-8"%>
<%@ page import="jirama.*"%>
<%@ page import="connexion.*"%>
<%@ page import="function.*"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Timestamp"%> 
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.*"%>
<%
    Function f=new Function();
    Fonction fo = new Fonction();

    DbConnection dbc=new DbConnection();
    Connection c=dbc.connected("jirama","jirama");

    String idClient = request.getParameter("idClient"); 
    String idCompteur = request.getParameter("idCompteur");
    out.println("idCompteur : "+idCompteur);
    out.println(" ");

    String idOffre = request.getParameter("idOffre");
    out.println("idOffre : "+idOffre);
    out.println(" ");

    String message = request.getParameter("message");

%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
 
    <title>Jirama(Offre prepayer)</title> 
 
    <link href="css/bootstrap.css" rel="stylesheet"> 
 
  </head> 
  <body> 
    <div class="container">
      <center>
    </br>
      <%
        if(message != null){
          out.println(message);
        }

        if(idClient == null){
            String rc = fo.rechercheClient(c);
            out.println(rc);
        }else{
            String rc = fo.rechercheCompteur(idClient,c);
            out.println(rc);
      %>
        </br>
      </br>
     <%   if(idCompteur != null){
              String lo = fo.afficherListeOffre(idCompteur,c);
              out.println(lo);
          }  
        }            
    
        c.close();
      %>
      </center>
    </div>


  </br>
</br>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
