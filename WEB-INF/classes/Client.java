package jirama;

public class Client
{
    String idClient;
    String adresse;
    String nom;

    public Client(String idClient,String adresse,String nom){
        setIdClient(idClient);
        setAdresse(adresse);
        setNom(nom);
    }

    public Client(){}

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdClient() {
        return this.idClient;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getAdresse() {
        return this.adresse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return this.nom;
    } 
}
