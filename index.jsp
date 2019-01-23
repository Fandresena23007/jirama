<%@ page pageEncoding="UTF-8"%>
<%@ page import="airmad.*"%>
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
   
%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
 
    <title>Jirama(Tableau de prelevement)</title> 
 
    <link href="css/bootstrap.css" rel="stylesheet"> 
 
  </head> 
  <body> 
    <div class="container">
    </br>
      <%
        String prelevement = fo.afficherPrelevement(c);
        out.print(prelevement);    

        String Labfacture = fo.rechercheFacture(c);
        out.println(Labfacture);

        String clientSituation =  fo.rechercheClientSituation(c);
        out.println(clientSituation);
    
        c.close();
      %>
    </div>

  </br>
</br>
    <center>
      <a href = "offrePrepayer.jsp">Voir les offres prepayer existants</a>
    </br>
  </br>
    <a href = "insertPrelevement.jsp">Inserer un nouveau prelevement</a>
    </center>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
