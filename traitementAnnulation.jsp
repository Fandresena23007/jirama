<%@ page pageEncoding="UTF-8"%>
<%@ page import="jirama.*"%>
<%@ page import="connexion.*"%>
<%@ page import="function.*"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Timestamp"%> 
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="general.*"%>
<%@ page import="form.*"%>

<%
    Function f=new Function();
    Fonction fo = new Fonction();
    Generalise g = new Generalise();

    DbConnection dbc=new DbConnection();
    Connection c=dbc.connected("jirama","jirama");

    String wawa = "";
    String class1 = request.getParameter("class");

    String idFacture = request.getParameter("idFacture");
    String idFactureAvoir = request.getParameter("idFactureAvoir");
    String montant = request.getParameter("montant");
    String dateAnnulation = request.getParameter("dateAnnulation");

    String message = null;

    try{
        FactureAvoir fa = new FactureAvoir(idFactureAvoir,idFacture,montant,dateAnnulation); 
        
        double prix = new Double(montant).doubleValue();
        fo.verifPrixFa(idFacture,prix,c);

        int resultInsertFa = g.insert(c,fa);    
        
        if(resultInsertFa > 0){
            message = "Insertion de ce montant réussi avec succes!";
        }

        int verifAnnulP = fo.annulerPrelevement(fa,c);

        if(verifAnnulP == 1){
            message = "Insertion prelevement total réussi!";
            String idPrelevement = fo.getPrelevementByFa(fa,c);
            response.sendRedirect("insererNewPrelevement.jsp?valeur="+idPrelevement);    
        }
        else{
            message = "Insertion factureAvoir partiel réussi! le reste est de : "+verifAnnulP;
        }

    }
    catch(InvocationTargetException e){
        message = e.getCause().getMessage();
    }
    catch(Exception ex){
        message = ex.getMessage();
    }

   
%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
 
    <title>Jirama(Formulaire factureAvoir)</title> 
 
    <link href="css/bootstrap.css" rel="stylesheet"> 
 
  </head> 
  <body> 
    <div class="container">
    </br>
      <%
        if(message != null){
            out.println(message);
        }

        c.close();
      %>
    </div>
    <center>
        <a href='index.jsp'>Retour</a>
    </center>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
