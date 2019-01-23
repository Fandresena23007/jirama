package jirama;

public class Compteur{
    String idCompteur;
    String idClient;
    int typeCompteur;
    String adresseClient;

    public Compteur(String idCompteur,String idClient,int typeCompteur,String adresseClient){
        setIdCompteur(idCompteur);
        setIdClient(idClient);
        setTypeCompteur(typeCompteur);
        setAdresseClient(adresseClient);
    }

    public Compteur(){}

    public void setIdCompteur(String idCompteur) {
        this.idCompteur = idCompteur;
    }
    public String getIdCompteur() {
        return this.idCompteur;
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