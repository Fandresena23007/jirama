package jirama;

public class Tarif{
    String idTarif;
    double prixUnitaire;
    double consomLimite;
    int typeTarif;

    public Tarif(String idTarif,double prixUnitaire,double consomLimite,int typeTarif){
        setIdTarif(idTarif);
        setPrixUnitaire(prixUnitaire);
        setConsomLimite(consomLimite);
        setTypeTarif(typeTarif);
    }

    public Tarif(){}

    public void setIdTarif(String idTarif) {
        this.idTarif = idTarif;
    }
    public String getIdTarif() {
        return this.idTarif;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public double getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public void setConsomLimite(double consomLimite) {
        this.consomLimite = consomLimite;
    }
    public double getConsomLimite() {
        return this.consomLimite;
    }

    public void setTypeTarif(int typeTarif) {
        this.typeTarif = typeTarif;
    }
    public int getTypeTarif() {
        return this.typeTarif;
    } 
}