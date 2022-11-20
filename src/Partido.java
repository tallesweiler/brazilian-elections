package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Partido {
    private int numeroDoPartido; // número do partido;
    private String siglaDoPartido; // sigla do partido;
    private int quantidadeDeVotosNominais;
    private int quantidadeDeVotosDeLegenda;
    private Map<Integer, Deputado> deputados;
    
    public Partido(int numeroDoPartido, String siglaDoPartido) {
        this.numeroDoPartido = numeroDoPartido;
        this.siglaDoPartido = siglaDoPartido;
        this.quantidadeDeVotosNominais = 0;
        this.quantidadeDeVotosDeLegenda = 0;
        this.deputados = new HashMap<>();
    }
    public Partido() {
    }
    
    public Deputado getDeputadoMaisVotado() {
        int i = 0;
        Deputado deputadoMaisVotado = new Deputado();
        for(Deputado d : deputados.values()){
            if(i == 0){
                deputadoMaisVotado = d;
            }
            if(d.getQuantidadeDeVotos() > deputadoMaisVotado.getQuantidadeDeVotos()){
                deputadoMaisVotado = d;
            }
            i++;
        }

        return deputadoMaisVotado;
        
    }
    public Deputado getDeputadoMenosVotado() {
        return deputadoMenosVotado;
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
    public Map<Integer, Deputado> getDeputados() {
        return deputados;
    }
    public void setDeputados(Map<Integer, Deputado> deputados){
        this.deputados = deputados;
    }
    public LinkedList<Deputado> getDeputadosOrdenados() {
        return deputadosOrdenados;
    }
    public void setDeputadosOrdenados(LinkedList<Deputado> deputadosOrdenados) {
        this.deputadosOrdenados = deputadosOrdenados;
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

    public Deputado retornaDeputado(int id){
        return deputados.get(id);
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

    public String toString(){
        String string;
        string = siglaDoPartido + "(" + (quantidadeDeVotosNominais + quantidadeDeVotosDeLegenda) + ")\n";
        for(Deputado d : deputados.values()){
            string += d + "\n";
        }

        return string;
    }
}
