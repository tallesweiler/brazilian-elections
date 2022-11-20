package src;

import java.util.HashMap;
import java.util.Map;

public class Partido {
    private int numeroDoPartido; // número do partido;
    private String siglaDoPartido; // sigla do partido;
    private int quantidadeDeVotos;
    private Map<Integer, Deputado> deputados;

    
    public Partido(int numeroDoPartido, String siglaDoPartido) {
        this.numeroDoPartido = numeroDoPartido;
        this.siglaDoPartido = siglaDoPartido;
        this.quantidadeDeVotos = 0;
        this.deputados = new HashMap<>();
    }
    public Partido() {
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
    public int getQuantidadeDeVotos() {
        return quantidadeDeVotos;
    }
    public void setQuantidadeDeVotos(int quantidadeDeVotos) {
        this.quantidadeDeVotos = quantidadeDeVotos;
    }
    public Map<Integer, Deputado> getDeputados() {
        return deputados;
    }
    public void setDeputados(Map<Integer, Deputado> deputados){
        this.deputados = deputados;
    }

    public Deputado retornaDeputado(int id){
        return deputados.get(id);
    }
    public void adicionaDeputado(int id, Deputado deputado) {
        this.deputados.put(id, deputado);
    }
    public void adicionaVotos(int valor) {
        this.setQuantidadeDeVotos(this.getQuantidadeDeVotos() + valor);
    }

    public String toString(){
        String string;
        string = siglaDoPartido + "(" + quantidadeDeVotos + ")\n";
        for(Deputado d : deputados.values()){
            string += d + "\n";
        }

        return string;
    }
}
