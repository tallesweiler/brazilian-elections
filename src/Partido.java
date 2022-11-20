package src;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Partido {
    private int numeroDoPartido; // número do partido;
    private String siglaDoPartido; // sigla do partido;
    private int quantidadeDeVotosNominais;
    private int quantidadeDeVotosDeLegenda;
    private int qtdDeputadosEleitos;
    private Map<Integer, Deputado> deputados;
    
    public Partido(int numeroDoPartido, String siglaDoPartido) {
        this.numeroDoPartido = numeroDoPartido;
        this.siglaDoPartido = siglaDoPartido;
        this.quantidadeDeVotosNominais = 0;
        this.quantidadeDeVotosDeLegenda = 0;
        this.qtdDeputadosEleitos = 0;
        this.deputados = new HashMap<>();
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
    public Map<Integer, Deputado> getDeputados() {
        return deputados;
    }
    public void setDeputados(Map<Integer, Deputado> deputados){
        this.deputados = deputados;
    }
    public int getQuantidadeDeVotosNominais() {
        return quantidadeDeVotosNominais;
    }
    public void setQuantidadeDeVotosNominais(int quantidadeDeVotosNominais) {
        this.quantidadeDeVotosNominais = quantidadeDeVotosNominais;
    }
    public int getQuantidadeDeVotosDeLegenda() {
        return quantidadeDeVotosDeLegenda;
    }
    public void setQuantidadeDeVotosDeLegenda(int quantidadeDeVotosDeLegenda) {
        this.quantidadeDeVotosDeLegenda = quantidadeDeVotosDeLegenda;
    }
    public int getQtdDeputadosEleitos() {
        return qtdDeputadosEleitos;
    }
    public void setQtdDeputadosEleitos(int qtdDeputadosEleitos) {
        this.qtdDeputadosEleitos = qtdDeputadosEleitos;
    }

    public Deputado retornaDeputado(int id){
        return deputados.get(id);
    }
    public int retornaVotosTotais() {
        return quantidadeDeVotosDeLegenda+quantidadeDeVotosNominais;
    }
    public void adicionaDeputado(int id, Deputado deputado) {
        this.deputados.put(id, deputado);
    }
    public void adicionaVotosNominais(int valor) {
        this.setQuantidadeDeVotosNominais(this.getQuantidadeDeVotosNominais() + valor);
    }
    public void adicionaVotosDeLegenda(int valor) {
        this.setQuantidadeDeVotosDeLegenda(this.getQuantidadeDeVotosDeLegenda() + valor);
    }
    public void adicionaEleito(){
        this.qtdDeputadosEleitos++;
    }
    
    public Deputado deputadoMaisVotado() {
        Deputado deputadoMaisVotado = new Deputado();
        for(Deputado d : deputados.values()){
            if(d.getQuantidadeDeVotos() > deputadoMaisVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)){
                deputadoMaisVotado = d;
            }
        }  
        return deputadoMaisVotado;
    }
    public Deputado deputadoMenosVotado() {
        Deputado deputadoMenosVotado = deputadoMaisVotado();
        for(Deputado d : deputados.values()){
            if(d.getQuantidadeDeVotos() < deputadoMenosVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)){
                deputadoMenosVotado = d;
            }
        }  
        return deputadoMenosVotado;
    }

    public String toString(){
        return siglaDoPartido+" - "+numeroDoPartido+", ";
    }
    public String votosPartido() {
        Locale brLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat nf = NumberFormat.getInstance(brLocale);

        String string=toString();

        int votosTotais=retornaVotosTotais();

        if ((votosTotais)>1)
            string+=nf.format(votosTotais)+" votos ("+nf.format(quantidadeDeVotosNominais);
        else 
            string+=nf.format(votosTotais)+" voto ("+nf.format(quantidadeDeVotosNominais);

        if (quantidadeDeVotosNominais>1)
            string+=" nominais e ";
        else 
            string+=" nominal e ";

        if (qtdDeputadosEleitos>1)
            string+=nf.format(quantidadeDeVotosDeLegenda)+" de legenda), " + qtdDeputadosEleitos + " candidatos eleitos";
        else
            string+=nf.format(quantidadeDeVotosDeLegenda)+" de legenda), " + qtdDeputadosEleitos + " candidato eleito";

        return string;
    }
    public String maisEMenosVotados() {
        return toString()+deputadoMaisVotado().maisEMenosVotado()+" / "+deputadoMenosVotado().maisEMenosVotado();
    }
}
