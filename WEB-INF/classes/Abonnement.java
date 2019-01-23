package jirama;
import java.sql.Timestamp;

public class Abonnement{
    String idAbonnement;
    String idCompteur;
    String idOffre;
    double quantite;
    Timestamp dateDebut;
    Timestamp dateFin;
    
    public Abonnement(){}

    public Abonnement(String idAbonnement,String idCompteur,String idOffre,double quantite,Timestamp dateDebut,Timestamp dateFin){
        setIdAbonnement(idAbonnement);
        setIdCompteur(idCompteur);
        setIdOffre(idOffre);
        setQuantite(quantite);
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }

    public void setIdAbonnement(String idAbonnement) {
        this.idAbonnement = idAbonnement;
    }
    public String getIdAbonnement() {
        return this.idAbonnement;
    }

    public void setIdCompteur(String idCompteur) {
        this.idCompteur = idCompteur;
    }
    public String getIdCompteur() {
        return this.idCompteur;
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
    }
    public String getIdOffre() {
        return this.idOffre;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public double getQuantite() {
        return this.quantite;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Timestamp getDateDebut() {
        return this.dateDebut;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }
    public Timestamp getDateFin() {
        return this.dateFin;
    } 
}
