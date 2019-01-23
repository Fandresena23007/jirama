package jirama;
import java.sql.Timestamp;

public class OffrePrepaye{
    String idOffre;
    double offreConsom;
    double prix;
    double majoration;
    int duree;
    int typeOffre;

    public OffrePrepaye(){}

    public OffrePrepaye(String idOffre,double offreConsom,double prix,double majoration,int duree,int typeOffre){
        setIdOffre(idOffre);
        setOffreConsom(offreConsom);
        setPrix(prix);
        setMajoration(majoration);
        setDuree(duree);
        setTypeOffre(typeOffre);
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
    }
    public String getIdOffre() {
        return this.idOffre;
    }

    public void setOffreConsom(double offreConsom) {
        this.offreConsom = offreConsom;
    }
    public double getOffreConsom() {
        return this.offreConsom;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    public double getPrix() {
        return this.prix;
    }

    public void setMajoration(double majoration) {
        this.majoration = majoration;
    }
    public double getMajoration() {
    return this.majoration;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    public int getDuree() {
        return this.duree;
    }

    public void setTypeOffre(int typeOffre) {
        this.typeOffre = typeOffre;
    }
    public int getTypeOffre() {
        return this.typeOffre;
    } 
}