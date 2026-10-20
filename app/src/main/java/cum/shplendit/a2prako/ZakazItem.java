package cum.shplendit.a2prako;

public class ZakazItem {
    private int id;
    private String nomerZakaza;
    private String dataZakaza;
    private String gitaraModel;
    private String userName;

    public ZakazItem(int id, String nomerZakaza, String dataZakaza, String gitaraModel, String userName) {
        this.id = id;
        this.nomerZakaza = nomerZakaza;
        this.dataZakaza = dataZakaza;
        this.gitaraModel = gitaraModel;
        this.userName = userName;
    }

    public int getId() { return id; }
    public String getNomerZakaza() { return nomerZakaza; }
    public String getDataZakaza() { return dataZakaza; }
    public String getGitaraModel() { return gitaraModel; }
    public String getUserName() { return userName; }
}