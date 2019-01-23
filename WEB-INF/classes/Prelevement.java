package jirama;
import java.sql.Connection;
import java.sql.Timestamp;
import connexion.DbConnection;
import exception.*;
import function.*;

public class Prelevement{
    String idPrelevement;
    String idCompteur;
    double consommation;
    Timestamp datePrelev;
    int mois;
    int annee;
    int etat;

    public Prelevement(String idPrelevement,String idCompteur,double consommation,Timestamp datePrelev,int mois,int annee,int etat){
        setIdPrelevement(idPrelevement);
        setIdCompteur(idCompteur);
        setConsommation(consommation);
        setDatePrelev(datePrelev);
        setMois(mois);
        setAnnee(annee);
        setEtat(etat);
    }

    public Prelevement(String idPrelevement,String idCompteur,String consommation,String datePrelev,String mois,String annee,String etat,Connection c)throws Exception{
        setIdPrelevement(idPrelevement);
        setIdCompteur(idCompteur);
        setConsommation(consommation);
        setDatePrelev(datePrelev);
        setMois(mois);
        setAnnee(annee);
        setEtat(etat);
    }

    public Prelevement(){}

    public void setIdPrelevement(String idPrelevement) {
        this.idPrelevement = idPrelevement;
    }
    public String getIdPrelevement() {
        return this.idPrelevement;
    }

    public void setIdCompteur(String idCompteur) {
       this.idCompteur = idCompteur;
    }
    public String getIdCompteur() {
        return this.idCompteur;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }
    public void setConsommation(String consommation) throws Exception{
        DbConnection dbc=new DbConnection();
        Connection c=dbc.connected("jirama","jirama");
        
        Fonction fo = new Fonction();

        double consomMax = fo.getConsomMax(this.idCompteur,c);
        System.out.println("consomMax : "+consomMax);

        double consom = new Double(consommation).doubleValue();
        System.out.println("consom : "+consom);
        
        if(consom < consomMax){
            throw new Exception("La nouvelle valeur de la consommation est inferieur a celle du dernier prelevement");
        }
        setConsommation(consom);

        c.close();
    }
    public double getConsommation() {
        return this.consommation;
    }

    public void setDatePrelev(Timestamp datePrelev) {
        this.datePrelev = datePrelev;
    }
    public void setDatePrelev(String datePrelev)throws DateException{
        Function f = new Function();
        Timestamp dp = f.controllDate(datePrelev);
        setDatePrelev(dp);
    }
    public Timestamp getDatePrelev() {
        return this.datePrelev;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }
    public void setMois(String mois)throws Exception{
        Function f = new Function();
        int month = f.controllMonth(mois);
        if(month == 0){throw new Exception("Mois inserer invalide!");}
        setMois(month);
    }
    public int getMois() {
        return this.mois;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public void setAnnee(String annee)throws Exception{
        Function f = new Function();
        int year = f.controllYear(annee);
        if(year == 0){throw new Exception("Annee inserer invalide!");}
        setAnnee(year);
    }
    public int getAnnee() {
        return this.annee;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    public void setEtat(String etat){
        int value = new Integer(etat).intValue();
        setEtat(value);
    }
    public int getEtat(){   
        return this.etat;
    } 
}