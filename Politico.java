public class Politico {
    private int cargo;              // 6 federal 7 estadual
    private int numeroDoCandidato;  // numero do candidato na urna
    private String nomeNaUrna;      // nome do candidato na urna
    private int numeroDaFederacao;  // numero da federacao, -1 representando candidato em partido isolado(que nao participa de federacao)
    private String dataDeNascimento;// data de nascimento do candidato
    private int genero;             // 2 masculino 4 feminino  
    private int foiEleito;          // 2 ou 3 representam candidato eleito
    private String tipoDeVoto;      // quando for “Válido (legenda)” os votos deste candidato vão para a legenda (e devem ser computados para a legenda, mesmo em caso de CD_SITUACAO_CANDIDADO_TOT diferente de 2 ou 16)
    private int situacaoCandidato;  // processar apenas aqueles com os valores 2 ou 16 que representam candidatos com candidatura deferida;
    private int quantidadeDeVotos;  // quantidade de votos obtidos
    private Partido partido;

    public Politico(int cargo, int numeroDoCandidato, String nomeNaUrna, int numeroDoPartido, String siglaDoPartido, int numeroDaFederacao, String dataDeNascimento, int genero, int foiEleito, String tipoDeVoto, int situacaoCandidato) {
        this.cargo = cargo;
        this.numeroDoCandidato = numeroDoCandidato;
        this.nomeNaUrna = nomeNaUrna;
        this.numeroDaFederacao = numeroDaFederacao;
        this.dataDeNascimento = dataDeNascimento;
        this.genero = genero;
        this.foiEleito = foiEleito;
        this.tipoDeVoto = tipoDeVoto;
        this.situacaoCandidato = situacaoCandidato;
    }
    public Politico(int numeroDoCandidato) {
        this.numeroDoCandidato = numeroDoCandidato;
    }
    public Politico() {}

    public int getCargo() {
        return cargo;
    }
    public void setCargo(int cargo) {
        this.cargo = cargo;
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
    public String getDataDeNascimento() {
        return dataDeNascimento;
    }
    public void setDataDeNascimento(String dataDeNascimento) {
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
    public int getSituacaoCandidato() {
        return situacaoCandidato;
    }
    public void setSituacaoCandidato(int situacaoCandidato) {
        this.situacaoCandidato = situacaoCandidato;
    }
    public String getTipoDeVoto() {
        return tipoDeVoto;
    }
    public void setTipoDeVoto(String tipoDeVoto) {
        this.tipoDeVoto = tipoDeVoto;
    }

    public void adicionaVotos(int valor) {
        setQuantidadeDeVotos(getQuantidadeDeVotos()+valor);
    }
    public String toString() {
        return cargo+";"+numeroDoCandidato+";"+nomeNaUrna+";"+numeroDoPartido+";"+siglaDoPartido+";"+numeroDaFederacao+";"+dataDeNascimento+";"+genero+";"+foiEleito+";"+tipoDeVoto+";"+situacaoCandidato+";"+quantidadeDeVotos;
    }
}
