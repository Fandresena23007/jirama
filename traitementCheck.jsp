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
    String[] listePrelev = request.getParameterValues("wawa");
    out.println("Taille : "+listePrelev.length);
    int newValue = 1;
    for(int i=0; i < listePrelev.length; i++){
        fo.updateEtat(newValue,listePrelev[i],c);

        Facture fac = fo.creerFacture(listePrelev[i],c);
        String sequenceName = "idFacture";
        String secAc = "f"+f.getSequenceNextVal(c,sequenceName); 
        out.println("Sequence actuel : "+secAc);
        fo.insertFacture(fac,secAc,c);

        Facture fd = fo.getFactureById(secAc,c);

        DetailFacture df = fo.creerFactureDetaille(fd,listePrelev[i],c);
        fo.insertFactureDetailler(df,c);
    }
    
   
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
        response.sendRedirect("index.jsp");

        c.close();    
      %>
    </div>

  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
