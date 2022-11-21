package src;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

public class Deputado {
    private int cargo;                  // 6 federal 7 estadual
    private int idade;                  // idade do candidato
    private int genero;                 // 2 masculino 4 feminino
    private int foiEleito;              // 2 ou 3 representam candidato eleito
    private int idadeEmDias;            // idade do candidato para fins de comparação
    private int numeroDoPartido;        // número do partido;
    private int numeroDoCandidato;      // numero do candidato na urna
    private int numeroDaFederacao;      // numero da federacao, -1 representando candidato em partido isolado(que nao participa de federacao)
    private int quantidadeDeVotos;      // quantidade de votos obtidos
    private int situacaoCandidato;      // processar apenas aqueles com os valores 2 ou 16 que representam candidatos com candidatura deferida;
    private String nomeNaUrna;          // nome do candidato na urna
    private String tipoDeVoto;          // quando for “Válido (legenda)” os votos deste candidato vão para a legenda (e devem ser computados para a legenda, mesmo em caso de CD_SITUACAO_CANDIDADO_TOT diferente de 2 ou 16)
    private String siglaDoPartido;      // sigla do partido;
    private LocalDate dataDeNascimento; // data de nascimento do candidato

    public Deputado(int cargo, int numeroDoCandidato, String nomeNaUrna, int numeroDoPartido, String siglaDoPartido, int numeroDaFederacao, String dia, String mes, String ano, int genero, int foiEleito, String tipoDeVoto, int situacaoCandidato) {
        this.cargo = cargo;
        this.numeroDoCandidato = numeroDoCandidato;
        this.nomeNaUrna = nomeNaUrna;
        this.numeroDaFederacao = numeroDaFederacao;
        this.dataDeNascimento = LocalDate.of(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
        this.idade = Period.between(dataDeNascimento, LocalDate.of(2022, 10, 2)).getYears();
        this.idadeEmDias = Period.between(dataDeNascimento, LocalDate.of(2022, 10, 2)).getDays();
        this.genero = genero;
        this.foiEleito = foiEleito;
        this.tipoDeVoto = tipoDeVoto;
        this.situacaoCandidato = situacaoCandidato;
        this.numeroDoPartido = numeroDoPartido;
        this.siglaDoPartido = siglaDoPartido;
    }
    public Deputado(){
        this.quantidadeDeVotos=-1;
    }

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
    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }
    public void setDataDeNascimento(LocalDate dataDeNascimento) {
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
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public int getIdadeEmDias() {
        return idadeEmDias;
    }
    public void setIdadeEmDias(int idadeEmDias) {
        this.idadeEmDias = idadeEmDias;
    }

    public void adicionaVotos(int valor) {
        this.setQuantidadeDeVotos(this.getQuantidadeDeVotos() + valor);
    }
    public String toString() {
        Locale brLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getInstance(brLocale);
        
        if(quantidadeDeVotos > 1)
            return nomeNaUrna+" ("+siglaDoPartido+", "+nf.format(quantidadeDeVotos)+" votos)";
        return nomeNaUrna+" ("+siglaDoPartido+", "+nf.format(quantidadeDeVotos)+" voto)";
    }
    public String maisEMenosVotado() {
        Locale brLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getInstance(brLocale);
        
        if(quantidadeDeVotos > 1)
            return nomeNaUrna+" ("+numeroDoCandidato+", "+nf.format(quantidadeDeVotos)+" votos)";
        return nomeNaUrna+" ("+numeroDoCandidato+", "+nf.format(quantidadeDeVotos)+" voto)";
    }
}
