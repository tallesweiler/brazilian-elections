import java.util.List;

public class Partido {
    private int numeroDoPartido;    // número do partido;
    private String siglaDoPartido;  // sigla do partido;
    private int quantidadeDeVotos;
    private List<Politico> politicos;

    public Partido(int numeroDoPartido, String siglaDoPartido) {
        this.numeroDoPartido = numeroDoPartido;
        this.siglaDoPartido = siglaDoPartido;
    }
    public Partido() {}

    public int getNumeroDoPartido() {
        return numeroDoPartido;
    }
    public void setNumeroDoPartido(int numeroDoPartido) {
        this.numeroDoPartido = numeroDoPartido;
    }
    public String getSiglaDoPartido() {
        return siglaDoPartido;
    }
    public void setSiglaDoPartido(String siglaDoPartido) {
        this.siglaDoPartido = siglaDoPartido;
    }
    public int getQuantidadeDeVotos() {
        return quantidadeDeVotos;
    }
    public void setQuantidadeDeVotos(int quantidadeDeVotos) {
        this.quantidadeDeVotos = quantidadeDeVotos;
    }
    public List<Politico> getPoliticos() {
        return politicos;
    }
    public void setPoliticos(List<Politico> politicos) {
        this.politicos = politicos;
    }
}
