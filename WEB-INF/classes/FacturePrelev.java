package jirama;
import java.sql.Timestamp;

public class FacturePrelev{
    String idClient;
    String idFacture;
    String idPrelevement;
    int mois;
    int annee;
    double total;

    public FacturePrelev(){}

    public FacturePrelev(String idClient,String idFacture,String idPrelevement,int mois,int annee,double total){
        setIdClient(idPrelevement);
        setIdFacture(idFacture);
        setIdPrelevement(idPrelevement);
        setMois(mois);
        setAnnee(annee);
        setTotal(total);
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public String getIdClient() {
        return this.idClient;
    }

    public void setIdFacture(String idFacture) {
        this.idFacture = idFacture;
    }
    public String getIdFacture() {
        return this.idFacture;
    }

    public void setIdPrelevement(String idPrelevement) {
        this.idPrelevement = idPrelevement;
    }
    public String getIdPrelevement() {
        return this.idPrelevement;
    }

    public void setTotal(double total){
        this.total = total;
    }
    public double getTotal(){
        return this.total;
    }

    public void setMois(int mois){
        this.mois = mois;
    }
    public int getMois(){
        return mois;
    }

    public void setAnnee(int annee){
        this.annee = annee;
    }
    public int getAnnee(){
        return this.annee;
    }
}