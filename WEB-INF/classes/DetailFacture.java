package jirama;
import java.sql.Timestamp;

public class DetailFacture{
    String idDetailF;
    String idFacture;
    String idPrelevement;
    int nbrTranche;
    double consomLimite1;
    double consomLimite2;
    double prixUnitaire1er;
    double prixUnitaire2eme;
    double prixUnitaire3eme;
    double premierTranche;
    double deuxiemeTranche;
    double troisiemeTranche;
    double total;
    double consom;

    public DetailFacture(String idDetailF,String idFacture,String idPrelevement,int nbrTranche,double consomLimite1,double consomLimite2,double prixUnitaire1er,double prixUnitaire2eme,double prixUnitaire3eme,double premierTranche,double deuxiemeTranche,double troisiemeTranche,double total, double consom){
        setIdDetailF(idDetailF);
        setIdFacture(idFacture);
        setIdPrelevement(idPrelevement);
        setNbrTranche(nbrTranche);
        setConsomLimite1(consomLimite1);
        setConsomLimite2(consomLimite2);
        setPrixUnitaire1er(prixUnitaire1er);
        setPrixUnitaire2eme(prixUnitaire2eme);
        setPrixUnitaire3eme(prixUnitaire3eme);
        setPremierTranche(premierTranche);
        setDeuxiemeTranche(deuxiemeTranche);
        setTroisiemeTranche(troisiemeTranche);
        setTotal(total);
        setConsom(consom);
    }

    public DetailFacture(){}

    public void setIdDetailF(String idDetailF) {
        this.idDetailF = idDetailF;
    }
    public String getIdDetailF() {
        return this.idDetailF;
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

    public void setNbrTranche(int nbrTranche) {
        this.nbrTranche = nbrTranche;
    }
    public int getNbrTranche() {
        return this.nbrTranche;
    }

    public void setConsomLimite1(double consomLimite1) {
        this.consomLimite1 = consomLimite1;
    }
    public double getConsomLimite1() {
        return this.consomLimite1;
    }

    public void setConsomLimite2(double consomLimite2) {
        this.consomLimite2 = consomLimite2;
    }
    public double getConsomLimite2() {
        return this.consomLimite2;
    }

    public void setPrixUnitaire1er(double prixUnitaire1er) {
        this.prixUnitaire1er = prixUnitaire1er;
    }
    public double getPrixUnitaire1er() {
        return this.prixUnitaire1er;
    }

    public void setPrixUnitaire2eme(double prixUnitaire2eme) {
        this.prixUnitaire2eme = prixUnitaire2eme;
    }
    public double getPrixUnitaire2eme() {
        return this.prixUnitaire2eme;
    }

    public void setPrixUnitaire3eme(double prixUnitaire3eme) {
        this.prixUnitaire3eme = prixUnitaire3eme;
    }
    public double getPrixUnitaire3eme() {
        return this.prixUnitaire3eme;
    }

    public void setPremierTranche(double premierTranche) {
        this.premierTranche = premierTranche;
    }
    public double getPremierTranche() {
        return this.premierTranche;
    }

    public void setDeuxiemeTranche(double deuxiemeTranche) {
       this.deuxiemeTranche = deuxiemeTranche;
    }
    public double getDeuxiemeTranche() {
        return this.deuxiemeTranche;
    }

    public void setTroisiemeTranche(double troisiemeTranche) {
        this.troisiemeTranche = troisiemeTranche;
    }
    public double getTroisiemeTranche() {
        return this.troisiemeTranche;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public double getTotal() {
        return this.total;
    }

    public void setConsom(double consom) {
        this.consom = consom;
    }
    public double getConsom() {
        return this.consom;
    } 
}