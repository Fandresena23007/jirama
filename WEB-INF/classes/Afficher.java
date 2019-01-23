package affiche;

import jirama.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Vector;
import java.lang.Math;
import connexion.*;
import function.*;
import general.*;
// import java.lang.reflect.Field;
public class Afficher
{
    public static void main(String[] args)throws ClassNotFoundException, Exception
    {
        Function f=new Function();
        Fonction fo = new Fonction();
        Generalise g = new Generalise();
        DbConnection db=new DbConnection();
        Connection c = db.connected("jirama","jirama");
        
        String idPrelevement = "p1";
        // double[] prixParTranche = fo.calculPrix(idPrelevement,c);
        
        // System.out.println("Taille du tableau de double : "+prixParTranche.length);
        // for(int i = 0;i < prixParTranche.length;i++){
        //     System.out.println("Prix tranche "+i+" = "+prixParTranche[i]);
        // }

        // int newValue = 0;
        // fo.updateEtat(newValue,idPrelevement,c);

        // PrelevFacturer pf = new PrelevFacturer();
        // pf.setIdPrelevement(idPrelevement);
        // f.insertbdd(pf,c);

        //fo.insertFacture(fac,c);
        // String factureSimple = fo.afficherFactureSimple(idPrelevement,c);
        // System.out.println(factureSimple);

        // double[][] result = fo.getTarif(idPrelevement,c);
        // for(int i=0; i< result[0].length; i++){
        //     System.out.println("PrixParTranche : "+result[0][i]);
        //     System.out.println("limiteConso : "+result[1][i]);
        //     System.out.println("PrixUnitaire : "+result[2][i]);
        //     System.out.println(" ");
        // }

        // Facture fac = fo.creerFacture(idPrelevement,c);
        // String sequenceName = "idFacture";
        // String secAc = "f"+f.getSequenceNextVal(c,sequenceName); 
        // System.out.println("Sequence actuel : "+secAc);
        // fo.insertFacture(fac,secAc,c);

        // Facture fd = fo.getFactureById(secAc,c);

        // DetailFacture df = fo.creerFactureDetaille(fd,idPrelevement,c);
        // fo.insertFactureDetailler(df,c);

        // System.out.println("idDetailF : "+df.getIdDetailF());
        // System.out.println("idFacture : "+df.getIdFacture());
        // System.out.println("idPrelevement : "+df.getIdPrelevement());
        // System.out.println("limiteConso[0] : "+df.getConsomLimite1());
        // System.out.println("limiteConso[1] : "+df.getConsomLimite2());
        // System.out.println("prixUnitaire[0] : "+df.getPrixUnitaire1er());
        // System.out.println("prixUnitaire[1] : "+df.getPrixUnitaire2eme());
        // System.out.println("prixUnitaire[2] : "+df.getPrixUnitaire3eme());
        // System.out.println("prixParTranche[0] : "+df.getPremierTranche());
        // System.out.println("prixParTranche[1] : "+df.getDeuxiemeTranche());
        // System.out.println("prixParTranche[2] : "+df.getTroisiemeTranche());
        // System.out.println("total : "+df.getTotal());
        // System.out.println("consommation : "+df.getConsom());

        // int mois = 10;
        // int annee = 2018;
        // String idClient = "cl1";
        // FacturePrelev[] fp = fo.getFacturePrelev(mois,annee,idClient,c);
        // System.out.println("Taille du tableau fp : "+fp.length);

        // for(int i = 0; i < fp.length; i++){
        //     System.out.println("idClient : "+fp[i].getIdClient());
        //     System.out.println("idFacture : "+fp[i].getIdFacture());
        //     System.out.println("idPrelevement "+fp[i].getIdPrelevement());
        //     System.out.println("DatePrelev : "+fp[i].getDatePrelev());
        // }

        //String idClient = "cl1";
        //String rc = fo.rechercheCompteur(idClient,c);
        // System.out.println("Type compteur correspondant a eau : "+ fo.compteur(0));
        // System.out.println("Type compteur correspondant a electricite : "+ fo.compteur(1));

        // String idOffre = "op21";
        // Timestamp t = fo.calculDateFin(idOffre,c);
        // System.out.println("dateFin : "+t);

        // String sequenceName = "idAbonnement";
        // String secAc = "abonnement"+f.getSequenceNextVal(c,sequenceName);
        // System.out.println("sequence actuel du idAbonnement : "+secAc);

        // String idCompteur = "compteur1";
        // String idOffre = "op1";
        // Abonnement ab = fo.creerAbonnement(idCompteur,idOffre,c);

        // String idCompteur = "compteur1";
        // double newConsommation = fo.getConsommationNew(idCompteur,c);
        // System.out.println("nouvelleConsommation : "+newConsommation);

        // int year = 0;
        // String annee = "2020";
        // try{
        //     year = f.controllYear(annee);
        //     if(year == 0){
        //         throw new Exception("Annee inserer invalide!");
        //     }
        // }
        // catch(Exception e){
        //     System.out.println(e.getMessage());
        // }
        // System.out.println("le mois : "+year);
        
        // String idFacture = "f1";
        // String where = "idFacture = '"+idFacture+"'";
        // Object[] obj = g.selectOpt(c,new Facture(),where);
        // Facture fac = (Facture)obj[0];
        // String factureDetail = fo.afficherFactureDetaille(fac,idPrelevement,c);
        // System.out.println(factureDetail);


        c.close();
            
    }
}