package jirama;
import java.sql.Timestamp;

public class PrelevAnnuler{
    String idPrelevAnnuler;
    String idPrelevement;
    Timestamp dateAnnulation;

    public PrelevAnnuler(String idPrelevAnnuler,String idPrelevement,Timestamp dateAnnulation){
        setIdPrelevAnnuler(idPrelevAnnuler);
        setIdPrelevement(idPrelevement);
        setDateAnnulation(dateAnnulation);
    }

    public PrelevAnnuler(){}

    public void setIdPrelevAnnuler(String idPrelevAnnuler) {
        this.idPrelevAnnuler = idPrelevAnnuler;
    }
    public String getIdPrelevAnnuler() {
        return this.idPrelevAnnuler;
    }

    public void setIdPrelevement(String idPrelevement) {
        this.idPrelevement = idPrelevement;
    }
    public String getIdPrelevement() {
        return this.idPrelevement;
    }

    public void setDateAnnulation(Timestamp dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }
    public Timestamp getDateAnnulation() {
        return this.dateAnnulation;
    } 
}