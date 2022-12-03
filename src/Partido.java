import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

public class Partido {
    private int numeroDoPartido;
    private int qtdDeputadosEleitos;
    private int quantidadeDeVotosNominais;
    private int quantidadeDeVotosDeLegenda;
    private String siglaDoPartido;

    private LinkedList<Deputado> deputados;
    
    // construtor padrão do partido
    public Partido(int numeroDoPartido, String siglaDoPartido) {
        this.qtdDeputadosEleitos        = 0;
        this.quantidadeDeVotosNominais  = 0;
        this.quantidadeDeVotosDeLegenda = 0;
        this.siglaDoPartido             = siglaDoPartido;
        this.numeroDoPartido            = numeroDoPartido;
        this.deputados                  = new LinkedList<>();
    }
    
    // getters e setters dos atributos do partido
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

    // adiciona candidato a lista
    public void adicionaDeputado(Deputado deputado) {
        deputados.add(deputado);
    }
    // retorna quantidade de votos totais do partido
    public int retornaVotosTotais() {
        return quantidadeDeVotosDeLegenda + quantidadeDeVotosNominais;
    }

    // adiciona estatisticas
    public void adicionaVotosNominais(int valor) {
        this.setQuantidadeDeVotosNominais(this.getQuantidadeDeVotosNominais()+valor);
    }
    public void adicionaVotosDeLegenda(int valor) {
        this.setQuantidadeDeVotosDeLegenda(this.getQuantidadeDeVotosDeLegenda()+valor);
    }
    public void adicionaEleito(){
        this.qtdDeputadosEleitos++;
    }
    
    // percorre toda a lista de deputados e retorna o mais votado
    public Deputado deputadoMaisVotado() {
        Deputado deputadoMaisVotado = new Deputado();
        for(Deputado d : deputados) {
            // verifica se o candidato é válido (ignorando o válido-legenda)
            if(d.getQuantidadeDeVotos() > deputadoMaisVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)) {
                deputadoMaisVotado = d;
            }
            // em caso de empate o o critério de comparação é a idade
            else if(d.getQuantidadeDeVotos() == deputadoMaisVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)) {
                if(d.getIdade() > deputadoMaisVotado.getIdade()) {
                    deputadoMaisVotado = d;
                }
            }
        }  
        return deputadoMaisVotado;
    }
    // percorre toda a lista de deputados e retorna o menos votado
    public Deputado deputadoMenosVotado() {
        Deputado deputadoMenosVotado = deputadoMaisVotado();
        for(Deputado d : deputados) {
            // verifica se o candidato é válido (ignorando o válido-legenda)
            if(d.getQuantidadeDeVotos() < deputadoMenosVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)) {
                deputadoMenosVotado=d;
            }
            // em caso de empate o o critério de comparação é a idade
            else if(d.getQuantidadeDeVotos() == deputadoMenosVotado.getQuantidadeDeVotos() && (d.getSituacaoCandidato() == 2 || d.getSituacaoCandidato() == 16)) {
                if(d.getIdade() < deputadoMenosVotado.getIdade()) {
                    deputadoMenosVotado = d;
                }
            }
        }  
        return deputadoMenosVotado;
    }

    // retorna string contendo a saída número 6: Votos totalizados por partido e número de candidatos eleitos
    public String votosPartido() {
        Locale brLocale=Locale.forLanguageTag("pt-BR");
        NumberFormat nf=NumberFormat.getInstance(brLocale);
        
        String string=toString();
        
        int votosTotais=retornaVotosTotais();
        
        if ((votosTotais) > 1)
        string+=nf.format(votosTotais)+" votos ("+nf.format(quantidadeDeVotosNominais);
        else 
        string+=nf.format(votosTotais)+" voto ("+nf.format(quantidadeDeVotosNominais);
        
        if (quantidadeDeVotosNominais > 1)
        string+=" nominais e ";
        else 
        string+=" nominal e ";
        
        if (qtdDeputadosEleitos > 1)
        string+=nf.format(quantidadeDeVotosDeLegenda)+" de legenda), "+qtdDeputadosEleitos+" candidatos eleitos";
        else
        string+=nf.format(quantidadeDeVotosDeLegenda)+" de legenda), "+qtdDeputadosEleitos+" candidato eleito";
        
        return string;
    }

    // toString padrão do partido
    public String toString(){
        return siglaDoPartido+" - "+numeroDoPartido+", ";
    }
    // toString para a análise de mais e menos votados
    public String toStringMaisEMenosVotados() {
        return toString()+deputadoMaisVotado().toStringMaisEMenosVotado()+" / "+deputadoMenosVotado().toStringMaisEMenosVotado();
    }
}
