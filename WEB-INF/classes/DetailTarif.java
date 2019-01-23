package jirama;

public class DetailTarif{
    String idDetailT;
    String idClient;
    int nbrTranche;

    public DetailTarif(String idDetailT,String idClient,int nbrTranche){
        setIdDetailT(idDetailT);
        setIdClient(idClient);
        setNbrTranche(nbrTranche);
    }

    public DetailTarif(){}

    public void setIdDetailT(String idDetailT) {
        this.idDetailT = idDetailT;
    }
    public String getIdDetailT() {
        return this.idDetailT;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    public String getIdClient() {
        return this.idClient;
    }

    public void setNbrTranche(int nbrTranche) {
        this.nbrTranche = nbrTranche;
    }
    public int getNbrTranche() {
        return this.nbrTranche;
    } 
}
