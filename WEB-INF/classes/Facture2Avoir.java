package jirama;
import java.sql.Timestamp;
import function.*;

public class Facture2Avoir{
    String idClient;
    String idFac;
    String idFactureAvoir;
    String idFacture;
    double montant;
    Timestamp dateAnnulation;

    public Facture2Avoir(String idClient,String idFac,String idFactureAvoir,String idFacture,double montant,Timestamp dateAnnulation){
        setIdClient(idClient);
        setIdFac(idFac);
        setIdFactureAvoir(idFactureAvoir);
        setIdFacture(idFacture);
        setMontant(montant);
        setDateAnnulation(dateAnnulation);
    }

    public Facture2Avoir(){}
    
    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public String getIdClient() {
        return this.idClient;
    }

    public void setIdFac(String idFac) {
        this.idFac = idFac;
    }
    public String getIdFac() {       
        return this.idFac;
    }

    public void setIdFactureAvoir(String idFactureAvoir) {
        this.idFactureAvoir = idFactureAvoir;
    }
    public String getIdFactureAvoir() {
        return this.idFactureAvoir;
    }

    public void setIdFacture(String idFacture) {
        this.idFacture = idFacture;
    }
    public String getIdFacture() {
        return this.idFacture;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    public void setMontant(String montant){
        double valeur = new Double(montant).doubleValue();
        setMontant(valeur);
    }
    public double getMontant() {
        return this.montant;
    }

    public void setDateAnnulation(Timestamp dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }
    public void setDateAnnulation(String dateAnnulation)throws Exception{
        Function f = new Function();
        Timestamp da = f.controllDate(dateAnnulation);
        System.out.println("date a seter : "+da);
        setDateAnnulation(da);
    }
    public Timestamp getDateAnnulation() {
        return this.dateAnnulation;
    } 
}