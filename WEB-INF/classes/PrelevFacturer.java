package jirama;
import java.sql.Timestamp;

public class PrelevFacturer{
    String idPrelevement;

    public PrelevFacturer(){}

    public PrelevFacturer(String idPrelevement){
        setIdPrelevement(idPrelevement);
    }

    public void setIdPrelevement(String idPrelevement) {
        this.idPrelevement = idPrelevement;
    }
    public String getIdPrelevement() {
        return this.idPrelevement;
    }

}