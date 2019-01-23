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
    //out.println("idClient : "+idClient);

    String mois = request.getParameter("mois");
    //out.println("mois : "+mois);

    String annee = request.getParameter("annee");
    //out.println("annee : "+annee);
  
    int month = new Integer(mois).intValue();
    int year = new Integer(annee).intValue();

    FacturePrelev[] fp = fo.getFacturePrelev(month,year,idClient,c);
    Facture fd = new Facture();
    String idPrelevement = "";
    for(int i = 0; i < fp.length;i++){
        String idFacture = fp[i].getIdFacture();
        fd =  fo.getFactureById(idFacture,c);

        idPrelevement = fp[i].getIdPrelevement();
      //  out.println("idPrelevement : "+idPrelevement);
    
   
%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
 
    <title>Jirama</title> 
 
    <link href="css/bootstrap.css" rel="stylesheet"> 
 
  </head> 
  <body> 
    <div class="container">
    </br>
      <%
        String factureSimple = fo.afficherFactureSimple(idPrelevement,fd,c);
        out.println(factureSimple);

      %>
  </br>
</br>
      <%
        String factureDetaille = fo.afficherFactureDetaille(fd,idPrelevement,c);
        out.println(factureDetaille);  
    }
      c.close();    
      %>
    </div>

  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
