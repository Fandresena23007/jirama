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

    String idCompteur = request.getParameter("idCompteur");
    out.println("idCompteur : "+idCompteur);

    String idOffre = request.getParameter("idOffre");
    out.println("idOffre : "+idOffre);

    String s = null;

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
    </br>
      <%
        try{
            Abonnement ab = fo.creerAbonnement(idCompteur,idOffre,c);
            fo.insertAbonnement(ab,c);
            s = "Félicitation! Achat réussi";          
        }
        catch(Exception e){
            e.getMessage();
        }
        finally{
            response.sendRedirect("offrePrepayer.jsp?message="+s);
            c.close();
        }
      %>
    </div>

  </br>
</br>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
