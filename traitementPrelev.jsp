<%@ page pageEncoding="UTF-8"%>
<%@ page import="jirama.*"%>
<%@ page import="form.*"%>
<%@ page import="connexion.*"%>
<%@ page import="function.*"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Timestamp"%> 
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="general.*"%>

<%
    Function f=new Function();
    Fonction fo = new Fonction();
    Generalise g = new Generalise();

    DbConnection dbc=new DbConnection();
    Connection c=dbc.connected("jirama","jirama");

    String wawa = "";
    String class1 = request.getParameter("class");
    
    //String idPrelevement = request.getParameter("idPrelevement");
    //out.println("voici le prelevement du formulaire : "+idPrelevement);

    String message = null;

    try{
        Class cl = Class.forName(class1);
        Object o = cl.newInstance();
        Field[] field = cl.getDeclaredFields();
        String[] listeValue = new String[field.length];
        for(int i = 0; i < field.length; i++){
            listeValue[i] = request.getParameter(field[i].getName());
            out.println(listeValue[i]);
        }
    
        g.setAll(o,listeValue,c);
    
        int resultInsert = g.insert(c,o);    
        if(resultInsert > 0){
          message = "Insertion réussi avec succes!";
        }
    }
    catch(InvocationTargetException e){
      message = e.getCause().getMessage();
    }
    
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
        if(message != null){
          response.sendRedirect("insertPrelevement.jsp?valeur="+message);
        }
        
        c.close();  
      %>
    </div>

  </br>
</br>
    
  <script src="js/jquery.min.js"></script> 
  <script src="js/bootstrap.min.js"></script> 
  </body> 
</html>
