import java.util.Date;

public class Politico {
    private Cargo cargo;            // 6 federal 7 estadual
    private String nomeDoCandidato; // nome do candidato
    private String nomeNaUrna;      // nome do candidato na urna
    private int numeroDoCandidato;  // numero do candidato na urna
    private int numeroDaFederacao;  // numero da federacao, -1 representando candidato em partido isolado(que nao participa de federacao)
    private Date dataDeNascimento;  // data de nascimento do candidato
    private int foiEleito;          // 2 ou 3 representam candidato eleito
    private int genero;             // 2 masculino 4 feminino
    private int quantidadeDeVotos;  // quantidade de votos obtidos 
    
    public Politico(Cargo cargo, String nomeDoCandidato, String nomeNaUrna, int numeroDoCandidato, int numeroDaFederacao, Date dataDeNascimento, int foiEleito, int genero, int quantidadeDeVotos) {
        this.cargo = cargo;
        this.nomeDoCandidato = nomeDoCandidato;
        this.nomeNaUrna = nomeNaUrna;
        this.numeroDoCandidato = numeroDoCandidato;
        this.numeroDaFederacao = numeroDaFederacao;
        this.dataDeNascimento = dataDeNascimento;
        this.foiEleito = foiEleito;
        this.genero = genero;
        this.quantidadeDeVotos = quantidadeDeVotos;
    }
    public Politico(int numeroDoCandidato) {
        this.numeroDoCandidato = numeroDoCandidato;
    }
    public Politico() {}

    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    public String getNomeDoCandidato() {
        return nomeDoCandidato;
    }
    public void setNomeDoCandidato(String nomeDoCandidato) {
        this.nomeDoCandidato = nomeDoCandidato;
    }
    public String getNomeNaUrna() {
        return nomeNaUrna;
    }
    public void setNomeNaUrna(String nomeNaUrna) {
        this.nomeNaUrna = nomeNaUrna;
    }
    public int getNumeroDoCandidato() {
        return numeroDoCandidato;
    }
    public void setNumeroDoCandidato(int numeroDoCandidato) {
        this.numeroDoCandidato = numeroDoCandidato;
    }
    public int getNumeroDaFederacao() {
        return numeroDaFederacao;
    }
    public void setNumeroDaFederacao(int numeroDaFederacao) {
        this.numeroDaFederacao = numeroDaFederacao;
    }
    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }
    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
    public int getFoiEleito() {
        return foiEleito;
    }
    public void setFoiEleito(int foiEleito) {
        this.foiEleito = foiEleito;
    }
    public int getGenero() {
        return genero;
    }
    public void setGenero(int genero) {
        this.genero = genero;
    }
    public int getQuantidadeDeVotos() {
        return quantidadeDeVotos;
    }
    public void setQuantidadeDeVotos(int quantidadeDeVotos) {
        this.quantidadeDeVotos = quantidadeDeVotos;
    }
}
