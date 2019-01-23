package jirama;
import java.sql.Timestamp;

public class PrelevAllClient{
    String idPrelevement;
    String idCompteur;
    double consommation;
    Timestamp datePrelev;
    int mois;
    int annee;
    int etat;
    String compteurId;
    String idClient;
    int typeCompteur;
    String adresseClient;

    public PrelevAllClient(String idPrelevement,String idCompteur,double consommation,Timestamp datePrelev,int mois,int annee,int etat,String compteurId,String idClient,int typeCompteur,String adresseClient){
        setIdPrelevement(idPrelevement);
        setIdCompteur(idCompteur);
        setConsommation(consommation);
        setDatePrelev(datePrelev);
        setMois(mois);
        setAnnee(annee);
        setEtat(etat);
        setCompteurId(compteurId);
        setIdClient(idClient);
        setTypeCompteur(typeCompteur);
        setAdresseClient(adresseClient);
    }

    public PrelevAllClient(){}

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
    public double getConsommation() {
        return this.consommation;
    }

    public void setDatePrelev(Timestamp datePrelev) {
        this.datePrelev = datePrelev;
    }
    public Timestamp getDatePrelev() {
        return this.datePrelev;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }
    public int getMois() {
        return this.mois;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
    public int getAnnee() {
        return this.annee;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    public int getEtat(){   
        return this.etat;
    } 

    public void setCompteurId(String compteurId) {
        this.idCompteur = idCompteur;
    }
    public String getCompteurId() {
        return this.compteurId;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public String getIdClient() {
        return this.idClient;
    }

    public void setTypeCompteur(int typeCompteur) {
        this.typeCompteur = typeCompteur;
    }
    public int getTypeCompteur() {
        return this.typeCompteur;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }
    public String getAdresseClient() {
        return this.adresseClient;
    }
}
