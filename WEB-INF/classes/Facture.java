package jirama;
import java.sql.Timestamp;

public class Facture{
    String idFacture;
    String idClient;
    Timestamp dateFacture;

    public Facture(String idFacture,String idClient,Timestamp dateFacture){
        setIdFacture(idFacture);
        setIdClient(idClient);
        setDateFacture(dateFacture);
    }

    public Facture(String idClient,Timestamp dateFacture){
        setIdClient(idClient);
        setDateFacture(dateFacture);
    }

    public Facture(){}

    public void setIdFacture(String idFacture) {
        this.idFacture = idFacture;
    }
    public String getIdFacture() {       
        return this.idFacture;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public String getIdClient() {
        return this.idClient;
    }

    public void setDateFacture(Timestamp dateFacture) {
        this.dateFacture = dateFacture;
    }
    public Timestamp getDateFacture() {
        return this.dateFacture;
    } 
}