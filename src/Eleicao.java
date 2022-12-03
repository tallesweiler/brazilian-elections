import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class Eleicao {
    private int vagas;
    private int qtdFeminino;
    private int qtdMasculino;
    private int qtdVotosValidos;
    private int idadeMenorQue30;
    private int idadeMenorQue40;
    private int idadeMenorQue50;
    private int idadeMenorQue60;
    private int idadeMaiorQue60;
    private int qtdVotosNominais;
    private int qtdVotosDeLegenda;
    
    private LinkedList<Deputado> listaDeputados;
    private LinkedList<Deputado> listaDeputadosEleitos;
    private LinkedList<Deputado> listaDeputadosMaisVotados;
    private LinkedList<Deputado> listaDeputadosQueDeveriamGanhar;
    private LinkedList<Deputado> listaDeputadosQueDeveriamPerder;
    private LinkedList<Partido> listaPartidos;
    
    private Map<Integer, Partido> partidos;
    private Map<Integer, Deputado> deputados; 

    public Eleicao(){
        vagas                              = 0;
        qtdFeminino                        = 0;
        qtdMasculino                       = 0;
        qtdVotosValidos                    = 0;
        idadeMenorQue30                    = 0;
        idadeMenorQue40                    = 0;
        idadeMenorQue50                    = 0;
        idadeMenorQue60                    = 0;
        idadeMaiorQue60                    = 0;
        qtdVotosNominais                   = 0;
        qtdVotosDeLegenda                  = 0;
        listaDeputados                     = new LinkedList<>();
        listaPartidos                      = new LinkedList<>();
        listaDeputadosEleitos              = new LinkedList<>();
        listaDeputadosMaisVotados          = new LinkedList<>();
        listaDeputadosQueDeveriamGanhar    = new LinkedList<>();
        listaDeputadosQueDeveriamPerder    = new LinkedList<>();
        partidos                           = new HashMap<>();
        deputados                          = new HashMap<>();
    }
    
    // acessa o arquivo com as informações de cada deputado e constrói todos os partidos e candidatos válidos
    public void preencheDeputados(String nomeArquivo, int cargo, LocalDate data) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        // define a codificação do arquivo e constrói um reader
        try(FileInputStream in=new FileInputStream(nomeArquivo);
            Reader r=new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br=new BufferedReader(r);) 
        {
            String[] infoCandidato = null;

            // consome a primeira linha (cabeçalho da planilha)
            String linha = br.readLine();
            while((linha = br.readLine()) != null){
                // remove todas as aspas da linha
                linha = linha.replaceAll("\"", "");
                // divide a linha com base nos ";"
                infoCandidato = linha.split(";");
                // posições específicas de cada informação
                int CD_CARGO                        = Integer.parseInt(infoCandidato[13]);
                int NR_CANDIDATO                    = Integer.parseInt(infoCandidato[16]);
                String NM_URNA_CANDIDATO            = infoCandidato[18];
                int NR_PARTIDO                      = Integer.parseInt(infoCandidato[27]);
                String SG_PARTIDO                   = infoCandidato[28];
                int NR_FEDERACAO                    = Integer.parseInt(infoCandidato[30]);
                String DT_NASCIMENTO                = infoCandidato[42];
                int CD_GENERO                       = Integer.parseInt(infoCandidato[45]);
                int CD_SIT_TOT_TURNO                = Integer.parseInt(infoCandidato[56]);
                String NM_TIPO_DESTINACAO_VOTOS     = infoCandidato[67];
                int CD_SITUACAO_CANDIDATO_TOT       = Integer.parseInt(infoCandidato[68]);

                // caso o partido ainda não exista, cria-se um novo partido
                if(partidos.get(NR_PARTIDO) == null){
                    partidos.put(NR_PARTIDO, new Partido(NR_PARTIDO, SG_PARTIDO));
                }
                // caso o deputado seja de nosso interesse
                if(CD_CARGO == cargo){
                    // caso o deputado seja válido ou válido (legenda), ele será criado e adicionado no hashmap
                    if((CD_SITUACAO_CANDIDATO_TOT == 2 || CD_SITUACAO_CANDIDATO_TOT == 16) || NM_TIPO_DESTINACAO_VOTOS.equals("Válido (legenda)")){
                        preencheDeputado(CD_CARGO, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, DT_NASCIMENTO, CD_GENERO, CD_SIT_TOT_TURNO, NM_TIPO_DESTINACAO_VOTOS, CD_SITUACAO_CANDIDATO_TOT, data);
                    }
                }
            }
        }
    }
    private void preencheDeputado(int CD_CARGO, int NR_CANDIDATO, String NM_URNA_CANDIDATO, int NR_PARTIDO, String SG_PARTIDO, int NR_FEDERACAO, String DT_NASCIMENTO, int CD_GENERO, int CD_SIT_TOT_TURNO, String NM_TIPO_DESTINACAO_VOTOS, int CD_SITUACAO_CANDIDATO_TOT, LocalDate data) {

        // divide a string data em dia, mes e ano
        String[] data2 = DT_NASCIMENTO.split("/");
        Deputado novoDeputado = new Deputado(CD_CARGO, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, data2[0], data2[1], data2[2], data, CD_GENERO, CD_SIT_TOT_TURNO, NM_TIPO_DESTINACAO_VOTOS, CD_SITUACAO_CANDIDATO_TOT);

        // caso o deputado seja eleito, geram-se estatisticas
        if(CD_SIT_TOT_TURNO == 2 || CD_SIT_TOT_TURNO == 3) {
            vagas++;

            if(novoDeputado.getIdade() < 30)
                idadeMenorQue30++;
            else if(novoDeputado.getIdade() < 40)
                idadeMenorQue40++;
            else if(novoDeputado.getIdade() < 50)
                idadeMenorQue50++;
            else if(novoDeputado.getIdade() < 60)
                idadeMenorQue60++;
            else if(novoDeputado.getIdade() >= 60)
                idadeMaiorQue60++;

            if(novoDeputado.getGenero()==2)
                qtdMasculino++;
            else if(novoDeputado.getGenero()==4)
                qtdFeminino++;

            listaDeputadosEleitos.add(novoDeputado);
            partidos.get(NR_PARTIDO).adicionaEleito();
        };
        // adiciona o novo deputado ao hashmap
        deputados.put(NR_CANDIDATO, novoDeputado);
    }

    // acessa o arquivo com as informações de cada voto e adiciona os votos aos candidatos
    public void preencheVotosDeputados(String nomeArquivo, int cargo) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        // define a codificação do arquivo e constrói um reader
        try(FileInputStream in=new FileInputStream(nomeArquivo);
            Reader r=new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br=new BufferedReader(r);) 
        {
            String[] infoVotacao = null;

            // consome a primeira linha (cabeçalho da planilha)
            String linha = br.readLine();
            while((linha = br.readLine()) != null){
                // remove todas as aspas da linha
                linha = linha.replaceAll("\"", "");
                // divide a linha com base nos ";"
                infoVotacao = linha.split(";");

                // posições específicas de cada informação
                int CD_CARGO    = Integer.parseInt(infoVotacao[17]);
                int NR_VOTAVEL  = Integer.parseInt(infoVotacao[19]);
                int QT_VOTOS    = Integer.parseInt(infoVotacao[21]);

                // caso o voto seja de nosso interesse
                if(CD_CARGO == cargo && !(NR_VOTAVEL >= 95 && NR_VOTAVEL <= 98)){
                    // o voto é computado
                    preencheVotosDeputado(CD_CARGO, NR_VOTAVEL, QT_VOTOS);
                }
            }
        }

    }
    private void preencheVotosDeputado(int CD_CARGO, int NR_VOTAVEL, int QT_VOTOS) {
       
        Partido partidoTemp = partidos.get(NR_VOTAVEL);
        // caso o partido não exista, o voto foi feito em um candidato
        if(partidoTemp == null){ 
            Deputado deputadoTemp = deputados.get(NR_VOTAVEL);
            // caso o candidato não exista
            if(deputadoTemp == null){
                return;
            }
            partidoTemp = partidos.get(deputadoTemp.getNumeroDoPartido());
            // caso candidato seja de legenda, o voto irá para a legenda
            if(deputadoTemp.getTipoDeVoto().equals("Válido (legenda)")){
                partidoTemp.adicionaVotosDeLegenda(QT_VOTOS);
                qtdVotosDeLegenda+=QT_VOTOS;
            }
            // caso contrário, irá para o deputado
            else{
                deputadoTemp.adicionaVotos(QT_VOTOS);
                partidoTemp.adicionaVotosNominais(QT_VOTOS);
                qtdVotosNominais+=QT_VOTOS;
            }
        }
        // caso contrário, o voto foi feito em um partido
        else{
            partidoTemp.adicionaVotosDeLegenda(QT_VOTOS);
            qtdVotosDeLegenda+=QT_VOTOS;
        }
    }

    // após contabilizar todas os votos, as informações são passadas para listas para fins de ordenação
    private void preencheTodosDeputados() {
        for(Deputado d : deputados.values()) {
            listaDeputados.add(d);
            // deputado também é adicionado dentro do partido
            partidos.get(d.getNumeroDoPartido()).adicionaDeputado(d);;
        }
    }
    private void preencheTodosPartidos() {
        for(Partido p : partidos.values()) {
            listaPartidos.add(p);
        }
    }

    // constroi classes anonimas que extendem a classe Comparator, para ordenar os deputados/partidos em diferentes critérios
    private void ordenaDeputadosPorQuantidadeDeVotos(LinkedList<Deputado> deputados) {
        Collections.sort(deputados, new Comparator<Deputado>() {
            @Override
            public int compare(Deputado d1, Deputado d2) { 
                return d2.getQuantidadeDeVotos()-d1.getQuantidadeDeVotos(); 
            } 
        });
    }
    private void ordenaPartidosPorQuantidadeDeVotos(LinkedList<Partido> partidos) {
        Collections.sort(partidos, new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) { 
                int a=p2.retornaVotosTotais()-p1.retornaVotosTotais();
                if(a==0){
                    a=p1.getNumeroDoPartido()-p2.getNumeroDoPartido();
                }
                return a;
            } 
        });
    }
    private void ordenaPartidosPorDeputadoMaisVotado(LinkedList<Partido> listaPartidos){
        Collections.sort(listaPartidos, new Comparator<Partido>() {
            @Override
            public int compare(Partido p1, Partido p2) { 
                int a=p2.deputadoMaisVotado().getQuantidadeDeVotos()-p1.deputadoMaisVotado().getQuantidadeDeVotos();
                if(a==0){
                    a=p2.deputadoMaisVotado().getIdade()-p1.deputadoMaisVotado().getIdade();
                }
                return a;
            } 
        });
    }
    
    // retorna string contendo a saída número 2: Candidatos eleitos (nome completo e na urna), indicado partido e número de votos nominais
    private String retornaEleitos(int cargo) {
        int i=1;

        String string = "";
        if(cargo==6)
            string += ("Deputados federais eleitos:\n");
        else
            string += ("Deputados estaduais eleitos:\n");

        for(Deputado d: listaDeputadosEleitos) {
            string += (i+" - ");
            if(d.getNumeroDaFederacao()!=-1) {
                string += ("*");
            }
            string += (d+"\n");
            i++;
        }
        string += ("\n");

        return string;
    }
    // retorna string contendo a saída número 3: Candidatos mais votados dentro do número de vagas
    private String retornaMaisVotados(){
            int i=1;

            String string = "";
            string += ("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):\n");

            for(Deputado d: listaDeputados) {
                string += (i+" - ");
                if(d.getNumeroDaFederacao()!=-1) {
                    string += ("*");
                }
                string += (d+"\n");
                listaDeputadosMaisVotados.add(d);

                i++;
                if (i>vagas)
                    break;
            }
            string += ("\n");

            return string;
    }
    // retorna string contendo as saídas:
    private String retornaDiscrepancias() {
        for(Deputado d : listaDeputadosEleitos) {
            if(listaDeputadosMaisVotados.indexOf(d)==-1) {
                listaDeputadosQueDeveriamPerder.add(d);
            }
        }
        for(Deputado d : listaDeputadosMaisVotados) {
            if(listaDeputadosEleitos.indexOf(d)==-1) {
                listaDeputadosQueDeveriamGanhar.add(d);
            }
        }
        // número 4: Candidatos não eleitos e que seriam eleitos se a votação fosse majoritária
        String string = "";
        string += ("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)\n");
        for (Deputado d : listaDeputadosQueDeveriamGanhar) {
            string += ((listaDeputados.indexOf(d)+1)+" - ");
            if(d.getNumeroDaFederacao()!=-1){
                string += ("*");
            }
            string += (d+"\n");
        }
        string += ("\n");
        // número 5: Candidatos eleitos no sistema proporcional vigente, e que não seriam eleitos se a votação fosse majoritária, isto é, pelo número de votos apenas que um candidato recebe diretamente
        string += ("Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)\n");
        for (Deputado d : listaDeputadosQueDeveriamPerder) {
            string += ((listaDeputados.indexOf(d)+1)+" - ");
            if(d.getNumeroDaFederacao()!=-1){
                string += ("*");
            }
            string += (d+"\n");
        }
        string += ("\n");

        return string;
    }
    // retorna string contendo a saída número 6: Votos totalizados por partido e número de candidatos eleitos
    private String retornaVotosDosPartidos() {
        int i=1;

        String string = "";
        string += ("Votação dos partidos e número de candidatos eleitos:\n");
        for (Partido p : listaPartidos) {
            string += (i+" - "+p.votosPartido()+"\n");
            i++;
        }
        string += ("\n");
        return string;
    }
    // retorna string contendo a saída número 8: Primeiro e último colocados de cada partido (com nome da urna, número do candidato e total de votos nominais). Partidos que não possuírem candidatos com um número positivo de votos válidos devem ser ignorados
    private String retornaMaisEMenosVotados(){
        int i=1;
        String string = "";
        string += ("Primeiro e último colocados de cada partido:\n");
        for (Partido p : listaPartidos) {
            if(p.getQuantidadeDeVotosNominais() > 0) {
                string += (i+" - "+p.toStringMaisEMenosVotados()+"\n");
            }
            i++;
        }
        string += ("\n");
        return string;
    }
    // retorna string contendo as saídas:
    private String retornaEstatisticas(){
        // instancia um formatador de números para pt-BR
        Locale brLocale=Locale.forLanguageTag("pt-BR");
        NumberFormat nf=NumberFormat.getInstance(brLocale);
        NumberFormat ni=NumberFormat.getInstance(brLocale);
        nf.setGroupingUsed(true);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        
        String string = "";
        // número 9: Distribuição de eleitos por faixa etária, considerando a idade do candidato no dia da eleição
        string += ("Eleitos, por faixa etária (na data da eleição):\n");
        string += ("      Idade < 30: "+ni.format(idadeMenorQue30)+" ("+nf.format(((float)idadeMenorQue30/(float)vagas)*100)+"%)\n");
        string += ("30 <= Idade < 40: "+ni.format(idadeMenorQue40)+" ("+nf.format(((float)idadeMenorQue40/(float)vagas)*100)+"%)\n");
        string += ("40 <= Idade < 50: "+ni.format(idadeMenorQue50)+" ("+nf.format(((float)idadeMenorQue50/(float)vagas)*100)+"%)\n");
        string += ("50 <= Idade < 60: "+ni.format(idadeMenorQue60)+" ("+nf.format(((float)idadeMenorQue60/(float)vagas)*100)+"%)\n");
        string += ("60 <= Idade     : "+ni.format(idadeMaiorQue60)+" ("+nf.format(((float)idadeMaiorQue60/(float)vagas)*100)+"%)\n");
        string += ("\n");

        // número 10: Distribuição de eleitos por sexo
        string += ("Eleitos, por gênero:\n");
        string += ("Feminino:  "+ni.format(qtdFeminino)+" ("+nf.format(((float)qtdFeminino/(float)vagas)*100)+"%)\n");
        string += ("Masculino: "+ni.format(qtdMasculino)+" ("+nf.format(((float)qtdMasculino/(float)vagas)*100)+"%)\n");
        string += ("\n");

        // número 11: Total de votos, total de votos nominais e total de votos de legenda
        string += ("Total de votos válidos:    "+ni.format(qtdVotosValidos)+"\n");
        string += ("Total de votos nominais:   "+ni.format(qtdVotosNominais)+" ("+nf.format(((float)qtdVotosNominais/(float)qtdVotosValidos)*100)+"%)\n");
        string += ("Total de votos de legenda: "+ni.format(qtdVotosDeLegenda)+" ("+nf.format(((float)qtdVotosDeLegenda/(float)qtdVotosValidos)*100)+"%)\n");

        return string;
    }
    
    // retorna uma string contendo todas as saídas especificadas no relatório
    public String retornaInformacoes(int cargo) {
        qtdVotosValidos=qtdVotosNominais+qtdVotosDeLegenda;
        preencheTodosDeputados();
        preencheTodosPartidos();
        ordenaDeputadosPorQuantidadeDeVotos(listaDeputados);
        ordenaDeputadosPorQuantidadeDeVotos(listaDeputadosEleitos);
        ordenaPartidosPorQuantidadeDeVotos(listaPartidos);
        
        String informacoes = "";
        // retorna string contendo a saída 1: Número de vagas (= número de eleitos)
        informacoes += ("Número de vagas: "+vagas+"\n\n");
        informacoes += retornaEleitos(cargo);
        informacoes += retornaMaisVotados();
        informacoes += retornaDiscrepancias();
        informacoes += retornaVotosDosPartidos();
        ordenaPartidosPorDeputadoMaisVotado(listaPartidos);
        informacoes += retornaMaisEMenosVotados();
        informacoes += retornaEstatisticas();

        return informacoes;
}
}
