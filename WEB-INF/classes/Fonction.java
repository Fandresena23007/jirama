package function;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import connexion.DbConnection;
import java.sql.Timestamp;
import java.lang.Math;
import jirama.*;
import form.*;
import general.*;

public class Fonction
{
    public Fonction(){}

    Function f = new Function();


    public String afficherPrelevement(Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "etat != 3";
        Object[] obj = g.selectOpt(c,new PrelevAllClient(),where);
        String table = "<h3>Liste des prelevements</h3><table class=\"table\" border=\"1\"><tr><td>Date de prelevement</td><td>Numero compteur</td><td>Client n°</td><td>Adresse du client</td><td>Type compteur</td><td>Consommation</td><td>Mois</td><td>Annee</td><td>Facturer</td></tr>";

        for(int i = 0; i < obj.length;i++){
            PrelevAllClient p =(PrelevAllClient)obj[i];  

            table += "<tr>";
            table += "<td>"+p.getDatePrelev()+"</td>";
            table += "<td>"+p.getIdCompteur()+"</td>";
            table += "<td>"+p.getIdClient()+"</td>";
            table += "<td>"+p.getAdresseClient()+"</td>";
            table += "<td>"+p.getTypeCompteur()+"</td>";
            table += "<td>"+p.getConsommation()+"</td>";
            table += "<td>"+p.getMois()+"</td>";
            table += "<td>"+p.getAnnee()+"</td>";
            
            if(p.getEtat() != 2){
                table += "<td>Deja facturer</td>";
            }else{
                table +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"traitementCheck.jsp\" method=\"post\">";
                table += "<td><input type=\"checkbox\" name = \"wawa\" value="+p.getIdPrelevement()+"></td>";
            }

            table += "</tr>";
        }

        table += "</table>";
        table += "<p style=\"text-align: center\"><button type=\"submit\" class=\"btn btn-primary\">Valider</button></p>";
        table += "</form>";
        
        return table;
    }

    public double[] addDouble(double[] tab,double obj) {
		int n = tab.length;
		double[] res = new double[n+1];
		int i = 0;
		
		while(i < n) {
			res[i] = tab[i];
			i++;
		}
		res[n] = obj;
		
		return res;
	}

    public FacturePrelev[] addFacturePrelev(FacturePrelev[] tab,FacturePrelev obj) {
		int n = tab.length;
		FacturePrelev[] res = new FacturePrelev[n+1];
		int i = 0;
		
		while(i < n) {
			res[i] = tab[i];
			i++;
		}
		res[n] = obj;
		
		return res;
	}


    public Client[] castClient(Object[] ob)throws Exception{
        Client[] app = new Client[ob.length];
        for(int i=0; i<ob.length;i++){
            app[i] = (Client)ob[i];
        } 
        return app;
    }

    public String rechercheFacture(Connection c)throws Exception{
        String formulaire = "";
        formulaire = "<h3>Rechercher une facture</h3>";
        formulaire +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"facturation.jsp\" method=\"post\">";

        Client client = new Client();
        Object[] ob1 = f.find("client",client,null,c);
        Client[]  clientDeroulant = castClient(ob1);
        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Veuillez choisir un client</label></br>";
            formulaire +="<select class=\"form-control\" name=\"idClient\">";
    
            for(int i=0; i<clientDeroulant.length;i++){
                Client cl = clientDeroulant[i];
                formulaire +="<option value="+ cl.getIdClient()+">"+ cl.getNom() +"</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        formulaire +="</br>";

        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Mois</label></br>";
            formulaire +="<select class=\"form-control\" name=\"mois\">";

            String[] listeMois = f.getListeMois();
            for(int i=0; i< listeMois.length ;i++){
                formulaire +="<option style=\"width : 10px\" value="+i+">"+ listeMois[i] +"</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        formulaire +="</br>";
    
        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Annee</label></br>";
            formulaire +="<select class=\"form-control\" name=\"annee\">";

            for(int i=2015; i < 2020 ;i++){
                formulaire +="<option style=\"width : 10px\" value="+i+">"+i+"</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        formulaire +="</br>";
        formulaire +="</br>";


        formulaire +="<button type=\"submit\" class=\"btn btn-primary\"> Valider</button>";
        formulaire +="</form>";

        return formulaire;
    }

    public FacturePrelev[] getFacturePrelev(int mois,int annee,String idClient,Connection c)throws Exception{
        Generalise g = new Generalise();
        mois = mois + 1;
        String where = "idClient = '"+idClient+"' and mois = "+mois+" and annee = "+annee;
        Object[] obj = g.selectOpt(c,new FacturePrelev(),where);
        FacturePrelev[] valiny = new FacturePrelev[0];

        for(int i=0; i < obj.length; i++){
            FacturePrelev facP = (FacturePrelev)obj[i];
            int resultVerif = verifIfFactureAnnuler(facP,c);
            if(resultVerif == 1){
                valiny = addFacturePrelev(valiny,facP);            
            }
        }

        System.out.println("Taille de la table originale : "+valiny.length);

        return valiny;
    }

    public int verifIfFactureAnnuler(FacturePrelev fp,Connection c)throws Exception{
        Generalise g = new Generalise();
        String idPrelevement = fp.getIdPrelevement();
        String where = "idPrelevement = '"+idPrelevement+"'";
        Object[] obj = g.selectOpt(c,new PrelevAnnuler(),where);
        if(obj.length > 0){
            return 0;
        }
        else{
            return 1;
        }
    }

    public double[] calculerPrixParTranche(double[] listePrixU, double[] listeLimite, double consommation) {
		//fonction généralisée permettant de raisoner en n tranches, n un entier > 1
		double[] result = new double[listeLimite.length];
		if(consommation < listeLimite[0]) {
			result[0] = consommation;
			return result;
		} else {
			int nbrTranche = 1;
			for(int i=0; i < listeLimite.length; i++) {
				if(consommation > listeLimite[i] && listeLimite[i] > 0) {
					nbrTranche++;
				}
			}
			System.out.println(nbrTranche);
			result[0] = listeLimite[0];
			for(int j=1; j < nbrTranche-1; j++) {
                System.out.println("limite1 : "+listeLimite[j]);
                System.out.println("limite2 : "+listeLimite[j-1]);
				result[j] = listeLimite[j] - listeLimite[j-1];
                System.out.println("result[j] : "+listeLimite[j]);
			}
			result[nbrTranche-1] = consommation;
            System.out.println("consom actuel : "+consommation);
			for(int k=0; k < nbrTranche-1; k++) {
				result[nbrTranche-1] -= result[k];
                System.out.println("result[nbrTranche - 1] : "+result[nbrTranche-1]);
                System.out.println(" ");
			}
		}
		for(int i=0; i < result.length; i++) {
            System.out.println("result[i] avant mult: "+result[i]);
			result[i] = result[i]*listePrixU[i];
            System.out.println("result[i] avant mult: "+result[i]);
            System.out.println(" ");

		}
		return result;
	}

    public PrelevAllClient getPrelevClient(String idPrelevement,Connection c)throws Exception{
        Function f = new Function();
        PrelevAllClient pac = new PrelevAllClient();
        pac.setIdPrelevement(idPrelevement);
        Object[] obj = f.find("PrelevAllClient",pac,null,c);
        pac = (PrelevAllClient)obj[0];
       
        return pac;
    }


    public double[][] getTarif(String idPrelevement,Connection c) throws Exception {
        Generalise g = new Generalise();
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        int type = pac.getTypeCompteur();
        System.out.println("typeCompteur : "+type);
        double consommation = pac.getConsommation();
        System.out.println("Consommation : "+consommation);

        //prendre le tarif correspondant au type
        String where = "typeTarif = "+type;
        Object[] obj2 = g.selectOpt(c,new Tarif(),where);
        Tarif[] listeTarif = new Tarif[obj2.length];
        for(int k = 0; k < listeTarif.length;k++){
            listeTarif[k] = (Tarif)obj2[k];
        }
		
		double[] prixUnitaire =  new double[listeTarif.length];
		double[] limiteConsom = new double[listeTarif.length];
	
        System.out.println("taille tableau listeTarif : "+listeTarif.length);
		for(int i = 0; i < listeTarif.length;i++) {
			prixUnitaire[i] = listeTarif[i].getPrixUnitaire();
			limiteConsom[i] = listeTarif[i].getConsomLimite();
		}
		
		double[] prixParTranche = calculerPrixParTranche(prixUnitaire,limiteConsom,consommation);
		double[][] result = new double[3][limiteConsom.length];
		
		result[0] = prixParTranche; //prix final par tranche
		result[1] = limiteConsom; //table de la limite des consommations
		result[2] = prixUnitaire; //table des prix unitaires
		
		return result;
	}

    public double[][] getTarifAvecConsom(String idPrelevement,double consommation,Connection c) throws Exception {
        Generalise g = new Generalise();
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        int type = pac.getTypeCompteur();
        System.out.println("typeCompteur : "+type);

        //prendre le tarif correspondant au type
        String where = "typeTarif = "+type;
        Object[] obj2 = g.selectOpt(c,new Tarif(),where);
        Tarif[] listeTarif = new Tarif[obj2.length];
        for(int k = 0; k < listeTarif.length;k++){
            listeTarif[k] = (Tarif)obj2[k];
        }
		
		double[] prixUnitaire =  new double[listeTarif.length];
		double[] limiteConsom = new double[listeTarif.length];
	
        System.out.println("taille tableau listeTarif : "+listeTarif.length);
		for(int i = 0; i < listeTarif.length;i++) {
			prixUnitaire[i] = listeTarif[i].getPrixUnitaire();
			limiteConsom[i] = listeTarif[i].getConsomLimite();
		}
		
		double[] prixParTranche = calculerPrixParTranche(prixUnitaire,limiteConsom,consommation);
		double[][] result = new double[3][limiteConsom.length];
		
		result[0] = prixParTranche; //prix final par tranche
		result[1] = limiteConsom; //table de la limite des consommations
		result[2] = prixUnitaire; //table des prix unitaires
		
		return result;
	}

    public void updateEtat(int newValue,String idPrelevement,Connection c)throws Exception{
        String value = ""+newValue;
        // String request="update "+table+" set "+colom+"='"+value+"'  where  "+condition+"="+valeurCondition;
        String condition = "idPrelevement";
        f.alter("prelevement","etat",value,condition,idPrelevement,c);
        System.out.println("Update reussi");
    }

    public Facture creerFacture(String idPrelevement,Connection c)throws Exception{
        //prendre le client correspondant au prelevement
        PrelevAllClient p = getPrelevClient(idPrelevement,c);
        String idClient = p.getIdClient();

        Timestamp now = f.getNow(c);

        Facture f = new Facture(idClient,now);

        return f;
    }

    public void insertFacture(Facture f,String nextval,Connection c)throws Exception{
         boolean bool=false;
         Statement s=null;
         try{
            if(c==null)
            {
                DbConnection dbc=new DbConnection();
                c=dbc.connected("jirama","jirama");
                bool=true;
            }
            s=c.createStatement();
            String request="insert into facture values('"+nextval+"','"+f.getIdClient()+"',timestamp'"+f.getDateFacture()+"')";
            System.out.println(request);
            s.executeUpdate("ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.ff'");
            s.executeUpdate(request);
            s.execute("commit");
            c.commit();s.close();
            System.out.println("Insertion reussi");
            
        }catch(Exception e){
            throw e;
        }finally{
            // if(bool==true)c.close();
        }
    }

    public String afficherFactureSimple(String idPrelevement,Facture fac,Connection c)throws Exception {
        String idClient = fac.getIdClient();
        Client cl = new Client();
        cl.setIdClient(idClient);
        Object[] client = f.find("Client",cl,null,c);
        String[] listeMois = f.getListeMois();
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        String moisPrelev = listeMois[pac.getMois() - 1];


        cl = (Client)client[0];

        boolean b = verifIfOffreExist(idPrelevement,c);

        String table = "<h3>Facture du mois de "+moisPrelev+" </h3><table class=\"table\" border=\"1\"><tr><td>Date de facturation</td><td>Prelevement n°</td><td>Nom du client</td><td>Adresse du client</td></tr>";
        if(b == true){
            table += "<h2>Ce client possede une offre prepayer</h2>";
        }

        table += "<tr>";
        table += "<td>"+fac.getDateFacture()+"</td>";
        table += "<td>"+idPrelevement+"</td>";
        table += "<td>"+cl.getNom()+"</td>";
        table += "<td>"+cl.getAdresse()+"</td>";
        table += "</tr>";

        table += "</table>";
        
        return table;
    }

    public DetailFacture creerFactureDetaille(Facture fac,String idPrelevement,Connection c)throws Exception{
        Generalise g = new Generalise();
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        String idCompteur = pac.getIdCompteur();
        Timestamp datePrelev = pac.getDatePrelev();
        int type = pac.getTypeCompteur();
        int mois = pac.getMois();
        int annee = pac.getAnnee();

        //A changer
        double consommation = getConsommationNew(idCompteur,c);
        System.out.println("Consommation : "+consommation);

        double d = dernierConsommation(mois,annee,idCompteur,c);
        if(d != 0){
            consommation = d;  
        }

        String idClient = fac.getIdClient();
        System.out.println("IdClient : "+idClient);

        //Prendre le detailTarif correspondant au client
        DetailTarif dt = new DetailTarif();
        dt.setIdClient(idClient);
        Object[] obj1 = f.find("DetailTarif",dt,null,c);
        dt = (DetailTarif)obj1[0];
        int nbrTranche = dt.getNbrTranche();

        String idFacture = fac.getIdFacture();
        System.out.println("Voici l idFacture : "+idFacture);

		boolean b = verifIfOffreExist(idPrelevement,c);
        double majoration = 0;

        if(b == true){
           consommation = calculConsommation(idPrelevement,c);
           System.out.println("consommation seter de : "+consommation);
           
            //Pour prendre l'abonnement correspondant au prelevement
            String where = "idCompteur = '"+idCompteur+"' and '"+datePrelev+"' >= dateDebut and '"+datePrelev+"' <= dateFin order by dateDebut";
            Object[] obj = g.selectOpt(c,new Abonnement(), where);
            Abonnement abonnement = (Abonnement)obj[obj.length-1];

            //Prendre l'offre correspondant
            String idOffre = abonnement.getIdOffre();
            String condition = "idOffre = '"+idOffre+"'";
            Object[] obj2 = g.selectOpt(c,new OffrePrepaye(), condition);
            OffrePrepaye op = (OffrePrepaye)obj2[0];

            majoration = op.getMajoration();

        }

        System.out.println("consommation final : "+consommation);
        double[][] donnees = getTarifAvecConsom(idPrelevement,consommation,c);


		double[] prixParTranche = donnees[0]; //prix par tranche
		double[] limiteConso = donnees[1]; //table de la limite des consommations
		double[] prixUnitaire = donnees[2]; //table des prix unitaires
				
		double total = 0;
        
        for(int i=0; i < prixParTranche.length; i++){
            total += prixParTranche[i];
            total = total; 
        }

        if(majoration != 0){
            total = total * (majoration/100);
        }
        
		DetailFacture df = new DetailFacture(
						"'df' || idDetailF.nextval", 
						idFacture, 
						idPrelevement, 
						nbrTranche, 
						
						limiteConso[0], 
						limiteConso[1], 
						
						prixUnitaire[0], 
						prixUnitaire[1], 
						prixUnitaire[2], 
						
						prixParTranche[0], 
						prixParTranche[1], 
						prixParTranche[2], 
						
						total, 
						consommation
					);
		
		return df;
	}

    public void insertFactureDetailler(DetailFacture df,Connection c)throws Exception{
         boolean bool=false;
         Statement s=null;
         try{
            if(c==null)
            {
                DbConnection dbc=new DbConnection();
                c=dbc.connected("jirama","jirama");
                bool=true;
            }
            s=c.createStatement();
            String request="insert into detailfacture values("+df.getIdDetailF()+",'"+df.getIdFacture()+"','"+df.getIdPrelevement()+"',"+df.getNbrTranche()+","+df.getConsomLimite1()+","+df.getConsomLimite2()+","+df.getPrixUnitaire1er()+","+df.getPrixUnitaire2eme()+","+df.getPrixUnitaire3eme()+","+df.getPremierTranche()+","+df.getDeuxiemeTranche()+","+df.getTroisiemeTranche()+","+df.getTotal()+","+df.getConsom()+")";
            System.out.println(request);
            s.executeUpdate("ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.ff'");
            s.executeUpdate(request);
            s.execute("commit");
            c.commit();s.close();
            System.out.println("Insertion reussi");
            
        }catch(Exception e){
            throw e;
        }finally{
            // if(bool==true)c.close();
        }
    }

    public String compteur(int typeCompteur)throws Exception{
        String compteur = "";
        if(typeCompteur == 0){
            compteur = "Eau";
        }else{
            compteur = "Electricite";
        }

        return compteur;
    } 

    public String afficherFactureDetaille(Facture fac,String idPrelevement,Connection c)throws Exception{
        Function f = new Function();
        Generalise g = new Generalise();
        String separateur = " ";

        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        int typeCompteur = pac.getTypeCompteur();
        String compteur = compteur(typeCompteur);

        String idFacture = fac.getIdFacture();
        String where = "idFacture = '"+idFacture+"'";
        Object[] obj = g.selectOpt(c,new DetailFacture(),where);
        DetailFacture df = (DetailFacture)obj[0];

        String table = "<h3>Facture detaille</h3><table class=\"table\" border=\"1\"><tr><td>Prelevement n°</td><td>Consommation du client</td><td>Type compteur</td><td>Nbr tranche</td><td>Lim 1er Tranche</td><td>Lim 2eme Tranche</td><td>PU 1er Tranche</td><td>PU 2eme Tranche</td><td>PU 3eme Tranche</td><td>1er Tranche</td><td>2eme Tranche</td><td>3eme Tranche</td><td>Total</td></tr>";
        
        table += "<tr>";
        table += "<td>"+ idPrelevement +"</td>";
        table += "<td>"+ df.getConsom() +"</td>";
        table += "<td>"+ compteur +"</td>";
        table += "<td>"+ df.getNbrTranche() +"</td>";
        table += "<td>"+ df.getConsomLimite1() +"</td>";
        table += "<td>"+ df.getConsomLimite2() +"</td>";
        table += "<td>"+ df.getPrixUnitaire1er() +" Ar</td>";
        table += "<td>"+ df.getPrixUnitaire2eme() +" Ar</td>";
        table += "<td>"+ df.getPrixUnitaire3eme() +" Ar</td>";
        table += "<td>"+ f.formatage(df.getPremierTranche(),separateur) +" Ar</td>";
        table += "<td>"+ f.formatage(df.getDeuxiemeTranche(),separateur) +" Ar</td>";
        table += "<td>"+ f.formatage(df.getTroisiemeTranche(),separateur) +" Ar</td>";
        table += "<td>"+ f.formatage(df.getTotal(),separateur) +" Ar</td>";
        table += "</tr>";

        table += "</table>";
        
        System.out.println(df.getPremierTranche());

        double total = df.getTotal();
        String totalEnLettre = null;
        if(total == 0){
            totalEnLettre = "zero";
        }
        else{
            String totalEnString = ""+total;
            totalEnString = totalEnString.substring(0,totalEnString.indexOf("."));
            int totalFinal = new Integer(totalEnString).intValue();
            totalEnLettre = f.enLettre(totalFinal);
        }

        table += "</br>";
        table += "</br>";

        table += "<h3> Montant total : "+totalEnLettre+" Ariary</h3>";

        table += "<hr>";

        table += "</br>";
        table += "</br>";

        table += "<center><a href='annulation.jsp?idFacture="+fac.getIdFacture()+"'>Annuler cette facturation</a></center>";

        table += "</br>";
        table += "</br>";

        table += "<center><a href='index.jsp'>Retour</a></center>";
        System.out.println(table);

        return table;
    }

    public Facture getFactureById(String idFacture,Connection c)throws Exception{
        Function f = new Function();
        Facture fac = new Facture();
        fac.setIdFacture(idFacture);
        Object[] obj = f.find("facture",fac,null,c);
        fac = (Facture)obj[0];

        return fac;
    }

    public String rechercheClient(Connection c)throws Exception{
        String formulaire = "";
        formulaire += "<center>";
        formulaire = "<h3>Recherche d`une offre prepayer</h3>";
        formulaire +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"offrePrepayer.jsp\" method=\"post\">";

        Client client = new Client();
        Object[] ob1 = f.find("client",client,null,c);
        Client[]  clientDeroulant = castClient(ob1);
        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Veuillez choisir un client</label></br>";
            formulaire +="<select class=\"form-control\" name=\"idClient\">";
    
            for(int i=0; i<clientDeroulant.length;i++){
                Client cl = clientDeroulant[i];
                formulaire +="<option value="+ cl.getIdClient()+">"+ cl.getNom() +"</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        
        formulaire += "</br>";
        formulaire += "</br>";

        formulaire += "<p style=\"text-align: center\"><button type=\"submit\" class=\"btn btn-primary\">Valider</button></p>";
        formulaire += "</form>";
        formulaire += "</center>";

        return formulaire;
    }

    public String afficherListeOffre(String idCompteur,Connection c)throws Exception {
        Generalise g = new Generalise();
        String where = "idCompteur = '"+idCompteur+"'";
        Object[] obj1 = g.selectOpt(c,new Compteur(), where);
        Compteur com = (Compteur)obj1[0];
        int typeCompteur = com.getTypeCompteur();
        System.out.println("Type compteur de la liste : "+typeCompteur);

        OffrePrepaye op = new OffrePrepaye();
        String condition = "typeOffre = '"+typeCompteur+"'";
        Object[] obj = g.selectOpt(c,new OffrePrepaye(), condition);
        OffrePrepaye[] offre = new OffrePrepaye[obj.length];
        
        String table = "<h3>Liste des offres disponibles : </h3><table class=\"table\" border=\"1\"><tr><td>Offre n°</td><td>Consommation</td><td>Prix</td><td>Majoration</td><td>Duree</td><td>Type de l'offre</td><td>Achat</td></tr>";

        for(int i = 0; i < obj.length; i++){
            offre[i] = (OffrePrepaye)obj[i];
            op = offre[i];
            String compteur = compteur(op.getTypeOffre());

            table += "<tr>";
            table += "<td>"+op.getIdOffre()+"</td>";
            table += "<td>"+op.getOffreConsom()+"</td>";
            table += "<td>"+op.getPrix()+"</td>";
            table += "<td>"+op.getMajoration()+"</td>";
            table += "<td>"+op.getDuree()+"</td>";
            table += "<td>"+compteur+"</td>";

            table +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"traitementAchat.jsp\" method=\"post\">";
            table += "<input type=\"hidden\" value='"+op.getIdOffre()+"' name=\"idOffre\"/>";
            table += "<input type=\"hidden\" value='"+idCompteur+"' name=\"idCompteur\"/>";

            table += "<td><button type=\"submit\" class=\"btn btn-secondary\">Acheter</button></td>";
            table += "</form>";
            table += "</tr>";

        }

        table += "</table>";
        
        return table;
    }

    public String rechercheCompteur(String idClient,Connection c)throws Exception{
        String formulaire = "";
        formulaire += "<center>";
        formulaire = "<h3>Veuillez choisir un compteur</h3>";
        formulaire +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"offrePrepayer.jsp\" method=\"post\">";

        Compteur cmpt = new Compteur();
        System.out.println("idClient ici : "+idClient);
        cmpt.setIdClient(idClient);
        Object[] obj = f.find("compteur",cmpt,null,c);
        Compteur[] co = new Compteur[obj.length];

        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Veuillez le compteur que vous voulez utiliser : </label></br>";
            formulaire +="<select class=\"form-control\" name=\"idCompteur\">";
    
            for(int i=0; i<obj.length;i++){
                co[i] = (Compteur)obj[i];
                cmpt = co[i];
                System.out.println("Type compteur : "+ cmpt.getTypeCompteur());
                formulaire +="<option value="+ cmpt.getIdCompteur()+">"+ cmpt.getIdCompteur() +" ("+compteur(cmpt.getTypeCompteur()) +")</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        
        formulaire += "</br>";
        formulaire += "</br>";        
        formulaire += "<input type=\"hidden\" value='"+idClient+"' name=\"idClient\"/>";
        formulaire += "<p style=\"text-align: center\"><button type=\"submit\" class=\"btn btn-primary\">Valider</button></p>";
        formulaire += "</form>";
        formulaire += "</center>";

        return formulaire;
    }

    public Timestamp calculDateFin(String idOffre,Connection c)throws Exception{
        Timestamp now = f.getNow(c);
        int j = now.getDate();
        // System.out.println("jour actuel : "+j);

        int m = now.getMonth();
        // System.out.println("mois actuel : "+m);

        int y = now.getYear();
        // System.out.println("annee actuel "+y);

        OffrePrepaye of = new OffrePrepaye();
        of.setIdOffre(idOffre);
        Object[] obj = f.find("offrePrepaye",of,null,c);
        of = (OffrePrepaye)obj[0];
        int duree = of.getDuree();
        // System.out.println("duree : "+duree);

        int jourFin = j + duree;
        // System.out.println("jourFin : "+jourFin);

        now.setDate(jourFin);

        return now;
    }

    public Abonnement creerAbonnement(String idCompteur,String idOffre,Connection c)throws Exception{
        Generalise g = new Generalise();

        Function f = new Function();
        String sequenceName = "idAbonnement";
        String secAc = "abonnement"+f.getSequenceNextVal(c,sequenceName);
        System.out.println("sequence actuel : "+secAc);
        
        String where = "idOffre= '"+idOffre+"'";
        Object[] obj = g.selectOpt(c,new OffrePrepaye(), where);
        OffrePrepaye op = (OffrePrepaye)obj[0];

        Timestamp dateDebut = f.getNow(c);
        System.out.println("dateDebut : "+dateDebut);
        
        Timestamp dateFin = calculDateFin(idOffre,c);
        System.out.println("dateFin : "+dateFin);
        
        double quantite = 0;
        quantite = op.getOffreConsom();
        
        boolean verif = verifNouvelAchat(idCompteur,c);
        if(verif == true){
            quantite = calculNewQuantite(idCompteur,idOffre,c);
            System.out.println("Nouvelle quantite : "+quantite);
        }

        System.out.println("Quantite prix : "+quantite);

        Abonnement ab = new Abonnement(secAc,idCompteur,idOffre,quantite,dateDebut,dateFin);
        
        return ab;
    }

    public void insertAbonnement(Abonnement ab,Connection c)throws Exception{
         boolean bool=false;
         Statement s=null;
         try{
            if(c==null)
            {
                DbConnection dbc=new DbConnection();
                c=dbc.connected("jirama","jirama");
                bool=true;
            }
            s=c.createStatement();
            String request="insert into abonnement values('"+ab.getIdAbonnement()+"','"+ab.getIdCompteur()+"','"+ab.getIdOffre()+"',"+ab.getQuantite()+",timestamp'"+ab.getDateDebut()+"',timestamp'"+ab.getDateFin()+"')";
            System.out.println(request);
            s.executeUpdate("ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY-MM-DD HH24:MI:SS.ff'");
            s.executeUpdate(request);
            s.execute("commit");
            c.commit();s.close();
            System.out.println("Insertion abonnement reussi");
            
        }catch(Exception e){
            throw e;
        }finally{
            // if(bool==true)c.close();
        }
    }

    public boolean verifIfOffreExist(String idPrelevement,Connection c)throws Exception{
        Generalise g = new Generalise();
        
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        String idCompteur = pac.getIdCompteur();
        Timestamp datePrelev = pac.getDatePrelev();

        //Pour prendre l'abonnement correspondant au prelevement
        String where = "idCompteur = '"+idCompteur+"' and '"+datePrelev+"' >= dateDebut and '"+datePrelev+"' <= dateFin order by dateDebut";
        Object[] obj = g.selectOpt(c,new Abonnement(), where);
        
        if(obj.length > 0){
            return true;
        }
        return false;
    }


    public double calculConsommation(String idPrelevement,Connection c)throws Exception{
        Generalise g = new Generalise();
        
        PrelevAllClient pac = getPrelevClient(idPrelevement,c);
        String idCompteur = pac.getIdCompteur();
        Timestamp datePrelev = pac.getDatePrelev();
        double consomPrelev = pac.getConsommation();
        System.out.println("consomPrelev : "+consomPrelev);
        System.out.println(" ");

        //Pour prendre l'abonnement correspondant au prelevement
        String where = "idCompteur = '"+idCompteur+"' and timestamp'"+datePrelev+"' >= dateDebut and timestamp'"+datePrelev+"' <= dateFin order by dateDebut";
        Object[] obj = g.selectOpt(c,new Abonnement(), where);
        Abonnement abonnement = (Abonnement)obj[obj.length-1];

        //Prendre l'offre correspondant
        System.out.println("Voici l'id offre correspondant a la demande : "+abonnement.getIdOffre());

        String idOffre = abonnement.getIdOffre();
        String condition = "idOffre = '"+idOffre+"'";
        Object[] obj1 = g.selectOpt(c,new OffrePrepaye(), condition);
        OffrePrepaye op = (OffrePrepaye)obj1[0];

        //Prendre tout a propos de l'offre
        double consomAcheter = abonnement.getQuantite();
        System.out.println("consomAcheter : "+consomAcheter);
        
        // commencement du calcul
        double conso = 0;
            
        if(consomPrelev < consomAcheter){
            conso = 0;
        }
        else{
            conso = consomPrelev - consomAcheter;
        }
        return conso;
    }

    public boolean verifNouvelAchat(String idCompteur,Connection c)throws Exception{
        Function f = new Function();
        Generalise g = new Generalise();
        
        Timestamp dateAchat = f.getNow(c);
        String where = "idCompteur = '"+idCompteur+"' and '"+dateAchat+"' >= dateDebut and '"+dateAchat+"' <= dateFin order by dateDebut";
        Object[] obj = g.selectOpt(c,new Abonnement(), where);

        if(obj.length > 0){
            return true;
        }
        return false;
    }

    public double calculNewQuantite(String idCompteur,String idNewOffre,Connection c)throws Exception{
        Function f = new Function();
        Generalise g = new Generalise();

        Timestamp dateAchat = f.getNow(c);
        String where = "idCompteur = '"+idCompteur+"' and '"+dateAchat+"' >= dateDebut and '"+dateAchat+"' <= dateFin order by dateDebut";
        Object[] obj = g.selectOpt(c,new Abonnement(), where);
        Abonnement abonnement = (Abonnement)obj[obj.length-1];

        //Prendre l'offre correspondant a l'abonnement
        String idOffre = abonnement.getIdOffre();
        Timestamp dateDebut = abonnement.getDateDebut();
        String condition = "idOffre = '"+idOffre+"'";
        Object[] obj1 = g.selectOpt(c,new OffrePrepaye(), condition);
        OffrePrepaye op = (OffrePrepaye)obj1[0];

        //Prendre tout a propos de l'offre
        double consomAcheter = op.getOffreConsom();
        int duree = op.getDuree();

        //Prendre l'offre correspondant a l'idNewOffre
        String newCondition = "idOffre = '"+idNewOffre+"'";
        Object[] obj2 = g.selectOpt(c,new OffrePrepaye(), newCondition);
        OffrePrepaye newOp = (OffrePrepaye)obj2[0];

        //Prendre tout a propos de l'offre
        double newConsomAcheter = newOp.getOffreConsom();
        int newDuree = newOp.getDuree();

        //calcul 
        int diffJour = f.diffEnJours(dateAchat,dateDebut);
        double consomMoyenne = consomAcheter/duree;
        double resteOffre = diffJour * consomMoyenne;
        double newQuantite = newConsomAcheter + resteOffre;

        return newQuantite;
    }


    public String creerFormulaire(Connection c)throws Exception{
        Function f = new Function();
        String action = "traitementPrelev.jsp";
        String method = "post";
        Formulaire form = new Formulaire(new Prelevement(),action,method);
        
        String sequenceName = "idPrelevement";
        String secPrelev = "p"+f.getSequenceNextVal(c,sequenceName);
        form.getChamp("idPrelevement").setVisible(false);
        form.getChamp("idPrelevement").setValeurParDef(secPrelev); 

        String tableN = "compteur";
        form.getChamp("idCompteur").createListe(c,tableN);

        form.getChamp("etat").setVisible(false);
        
        String valeur = "2";
        form.getChamp("etat").setValeurParDef(valeur); 

        String html = "<h3>Insertion d'un nouveau prelevement : </h3>";
        html += form.getHtml();

        return html;
    } 

    public double getConsomMax(String idCompteur,Connection c) throws Exception{
        boolean bool=false;
        Statement s=null;
        try{
            if(c==null)
            {
                DbConnection dbc=new DbConnection();
                c=dbc.connected("jirama","jirama");
                bool=true;
            }

            s = c.createStatement();
            String sql = "select Max(consommation) from Prelevement where  idCompteur = '"+idCompteur+"' and idPrelevement not in (select idPrelevement from prelevAnnuler)";
            System.out.println("sql SELECTMAX1 : "+sql);
            ResultSet rep = s.executeQuery(sql);
            System.out.println("sql SELECTMAX2 : "+sql);
            double consomMax = 0;

            while(rep.next()){
                consomMax = rep.getDouble(1);
            }

            return consomMax;

        }catch(Exception e){
            throw e;
        }finally{
            if(bool==true)c.close();
        }
    }

    public double getConsommationNew(String idCompteur,Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "idCompteur = '"+idCompteur+"' order by annee,mois";
        Object[] obj = g.selectOpt(c,new Prelevement(),where);
        Prelevement[] listePrelev = new Prelevement[obj.length];
        for(int i = 0; i < listePrelev.length; i++){
            listePrelev[i] = (Prelevement)obj[i];
        }

        double newConso = listePrelev[0].getConsommation();
        System.out.println("newConso : "+newConso);

        if(listePrelev.length > 1){
            newConso = listePrelev[1].getConsommation() - newConso; 
        }

        return newConso;
    }

    public String creerFormulaireFacAvoir(String idFacture,Connection c)throws Exception{
        Function f = new Function();
        String action = "traitementAnnulation.jsp";
        String method = "post";
        Timestamp now = f.getNow(c);
        String dateAnnulation = ""+now;
        
        Formulaire form = new Formulaire(new FactureAvoir(),action,method);
        
        String sequenceName = "idFactureAvoir";
        String secPrelev = "idFa"+f.getSequenceNextVal(c,sequenceName);
        form.getChamp("idFactureAvoir").setVisible(false);
        form.getChamp("idFactureAvoir").setValeurParDef(secPrelev); 

        form.getChamp("idFacture").setVisible(false);
        form.getChamp("idFacture").setValeurParDef(idFacture);

        form.getChamp("dateAnnulation").setVisible(false);
        form.getChamp("dateAnnulation").setValeurParDef(dateAnnulation);

        String html = "<h3>Veuillez inserer le vrai montant  : </h3>";
        html += form.getHtml();

        return html;
    }


    public PrelevAnnuler creerPrelevAnnuler(String idPrelevement,Connection c)throws Exception{
        Timestamp dateAnnulation = f.getNow(c);
        String sequenceName = "idPrelevAnnuler";
        String idPrelevAnnuler = "idPa"+f.getSequenceNextVal(c,sequenceName);

        PrelevAnnuler pa = new PrelevAnnuler();
        pa.setIdPrelevement(idPrelevement);
        pa.setDateAnnulation(dateAnnulation);
        pa.setIdPrelevAnnuler(idPrelevAnnuler);

        return pa;
    }

    public void verifPrixFa(String idFacture,double montant,Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "idFacture = '"+idFacture+"'";
        Object[] obj = g.selectOpt(c,new FacturePrelev(),where);
        FacturePrelev fp = (FacturePrelev)obj[0];

        if(fp.getTotal() - montant < 0){
            throw new Exception("Annulation impossible, cette valeur est incorrect!");
        }
    }
    
    public int annulerPrelevement(FactureAvoir fa,Connection c)throws Exception{
        Generalise g = new Generalise();
        String idFacture = fa.getIdFacture();
        String where = "idFacture = '"+idFacture+"'";
        Object[] obj = g.selectOpt(c,new FacturePrelev(),where);
        FacturePrelev fp = (FacturePrelev)obj[0];
        
        Object[] obj1 = g.selectOpt(c,new FactureAvoir(),where);
        FactureAvoir[] facA = new FactureAvoir[obj1.length];
        for(int i = 0; i < obj1.length; i++){
            facA[i] = (FactureAvoir)obj1[i];
        } 

        double totalAvoir = g.getSum(facA,"montant");

        String idPrelevement = fp.getIdPrelevement();
        
        System.out.println("total facture prelev : "+fp.getTotal());
        System.out.println("total Avoir : "+totalAvoir);
        
        double reste = fp.getTotal() - totalAvoir;
        System.out.println("reste : "+reste);

        int resultInsertPa = 0;

        //Verifier si partielle
        if(reste == 0){
            PrelevAnnuler pa = creerPrelevAnnuler(idPrelevement,c);
            updateEtat(3,idPrelevement,c);
            resultInsertPa = g.insert(c,pa);
        }
        else{
            String s = ""+reste;
            s = s.substring(0,s.lastIndexOf("."));
            int valiny = new Integer(s).intValue();
            resultInsertPa = valiny;
        }

        return resultInsertPa;
    }

    public String getPrelevementByFa(FactureAvoir fa,Connection c)throws Exception{
        Generalise g = new Generalise();
        String idFacture = fa.getIdFacture();
        String where = "idFacture = '"+idFacture+"'";
        Object[] obj = g.selectOpt(c,new FacturePrelev(),where);
        FacturePrelev fp = (FacturePrelev)obj[0];
        String idPrelevement = fp.getIdPrelevement();

        return idPrelevement;
    }

    public String creerFormulaireNewPrelev(String idPrelevement,Connection c)throws Exception{
        Function f = new Function();
        Generalise g = new Generalise();
        String action = "traitementPrelevNew.jsp";
        String method = "post";
        Formulaire form = new Formulaire(new Prelevement(),action,method);
        
        String where = "idPrelevement = '"+idPrelevement+"'";
        Object[] obj = g.selectOpt(c,new Prelevement(),where);
        Prelevement p = (Prelevement)obj[0];
        String idCompteur = p.getIdCompteur();
        String mois = ""+p.getMois();

        String annee = ""+p.getAnnee();

        String sequenceName = "idPrelevement";
        String secPrelev = "p"+f.getSequenceNextVal(c,sequenceName);
        form.getChamp("idPrelevement").setVisible(false);
        form.getChamp("idPrelevement").setValeurParDef(secPrelev); 

        form.getChamp("idCompteur").setVisible(false);
        form.getChamp("idCompteur").setValeurParDef(idCompteur);

        form.getChamp("mois").setVisible(false);
        form.getChamp("mois").setValeurParDef(mois);

        form.getChamp("annee").setVisible(false);
        form.getChamp("annee").setValeurParDef(annee);

        form.getChamp("etat").setVisible(false);
        
        String valeur = "2";
        form.getChamp("etat").setValeurParDef(valeur); 

        String html = "<h3>Insertion d'un nouveau prelevement : </h3>";
        html += form.getHtml();

        return html;
    }

    public double dernierConsommation(int mois,int annee,String idCompteur,Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "prelevement.mois = "+mois+" and prelevement.annee = "+annee+"and prelevement.idCompteur = '"+idCompteur+"'and prelevement.idPrelevement not in (select idPrelevement from prelevAnnuler)";
        Object[] obj = g.selectOpt(c,new Prelevement(),where);
        Prelevement p = (Prelevement)obj[0];
        double consommation = p.getConsommation();

        return consommation;
    }    

    public String rechercheClientSituation(Connection c)throws Exception{
        String formulaire = "";
        formulaire = "<h3>Rechercher client pour sa situation : </h3>";
        formulaire +="<form class=\"form-inline\" role=\"form\" name=\"form\" action=\"situation.jsp\" method=\"post\">";

        Client client = new Client();
        Object[] ob1 = f.find("client",client,null,c);
        Client[]  clientDeroulant = castClient(ob1);
        formulaire +="<div class=\"form-group\">";
        formulaire +="<label for=\"Classe\">Veuillez choisir un client</label></br>";
            formulaire +="<select class=\"form-control\" name=\"idClient\">";
    
            for(int i=0; i<clientDeroulant.length;i++){
                Client cl = clientDeroulant[i];
                formulaire +="<option value="+ cl.getIdClient()+">"+ cl.getNom() +"</option>";
            }

            formulaire += "</select>";
         
        formulaire += "</div>";
        formulaire +="</br>";

        formulaire += "</br>";
        formulaire += "</br>";


        formulaire +="<button type=\"submit\" class=\"btn btn-primary\"> Valider</button>";
        formulaire +="</form>";

        return formulaire;
    }

    public FacturePrelev[] getAllFactureClient(String idClient,Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "idClient = '"+idClient+"'";
        Object[] obj = g.selectOpt(c,new FacturePrelev(),where);
        FacturePrelev[] fp = new FacturePrelev[obj.length];
        for(int i=0;i<obj.length;i++){
            fp[i] = (FacturePrelev)obj[i];
        }

        return fp;
    }
    
    public Facture2Avoir[] getAllFactureAvoir(String idClient,Connection c)throws Exception{
        Generalise g = new Generalise();
        String where = "idFacture in (select idFacture from facture where idClient = '"+idClient+"')";
        Object[] obj = g.selectOpt(c,new Facture2Avoir(),where);
        Facture2Avoir[] fp = new Facture2Avoir[obj.length];
        for(int i=0;i<obj.length;i++){
            fp[i] = (Facture2Avoir)obj[i];
        }

        return fp;
    }

    public String getSituation(String idClient,Connection c)throws Exception{
        Generalise g = new Generalise();
        FacturePrelev[] listeFSimple = getAllFactureClient(idClient,c);
        Facture2Avoir[] listeAvoir = getAllFactureAvoir(idClient,c);

        double sommeListeS = g.getSum(listeFSimple,"total");
        double sommeListeA = g.getSum(listeAvoir,"montant");

        double situation = sommeListeS - sommeListeA;

        String table = "<h3>Situation : </h3><table class=\"table\" border=\"1\"><tr><td>Total facture simple</td><td>Total facture avoir</td><td>Difference</td></tr>";

        table += "<td>"+sommeListeS+"</td>";
        table += "<td>"+sommeListeA+"</td>";
        table += "<td>"+situation+"</td>";

        table += "</table>";

        return table;
    }

}


