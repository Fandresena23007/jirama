<%@ page pageEncoding="UTF-8"%>
<%@ page import="jirama.*"%>
<%@ page import="form.*"%>
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

    String idPrelevement = request.getParameter("valeur");
    String val = request.getParameter("val");

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
        if(idPrelevement != null){
          out.println(idPrelevement);
          String formulaire = fo.creerFormulaireNewPrelev(idPrelevement,c);
          out.println(formulaire);
  
        }

        if(val != null){
            out.println(val);
        }


        c.close();
        
      %>
    <center>
        <a href='index.jsp'>Retour</a>
    </center>
    </div>

  </br>
</br>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
