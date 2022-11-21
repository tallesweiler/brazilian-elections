package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;
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
    
    public void preencheDeputados(String nomeArquivo, int cargo, LocalDate data) throws FileNotFoundException, IOException {
        try(FileInputStream in=new FileInputStream(nomeArquivo);
            Reader r=new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br=new BufferedReader(r);) 
        {
            String linha = br.readLine(); // consome a primeira linha
            String[] infoCandidato = null;
            while((linha = br.readLine()) != null){
                linha = linha.replaceAll("\"", "");
                //System.out.println(linha);
                infoCandidato = linha.split(";");
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

                if(partidos.get(NR_PARTIDO) == null){
                    partidos.put(NR_PARTIDO, new Partido(NR_PARTIDO, SG_PARTIDO));
                }
                if(CD_CARGO == cargo){
                    if((CD_SITUACAO_CANDIDATO_TOT == 2 || CD_SITUACAO_CANDIDATO_TOT == 16) || NM_TIPO_DESTINACAO_VOTOS.equals("Válido (legenda)")){
                        preencheDeputado(CD_CARGO, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, DT_NASCIMENTO, CD_GENERO, CD_SIT_TOT_TURNO, NM_TIPO_DESTINACAO_VOTOS, CD_SITUACAO_CANDIDATO_TOT, data);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void preencheDeputado(int CD_CARGO, int NR_CANDIDATO, String NM_URNA_CANDIDATO, int NR_PARTIDO, String SG_PARTIDO, int NR_FEDERACAO, String DT_NASCIMENTO, int CD_GENERO, int CD_SIT_TOT_TURNO, String NM_TIPO_DESTINACAO_VOTOS, int CD_SITUACAO_CANDIDATO_TOT, LocalDate data) {

        String[] data2 = DT_NASCIMENTO.split("/");
        Deputado novoDeputado = new Deputado(CD_CARGO, NR_CANDIDATO, NM_URNA_CANDIDATO, NR_PARTIDO, SG_PARTIDO, NR_FEDERACAO, data2[0], data2[1], data2[2], data, CD_GENERO, CD_SIT_TOT_TURNO, NM_TIPO_DESTINACAO_VOTOS, CD_SITUACAO_CANDIDATO_TOT);

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
        deputados.put(NR_CANDIDATO, novoDeputado);
    }

    public void preencheVotosDeputados(String nomeArquivo, int cargo) throws FileNotFoundException, IOException {
        try(FileInputStream in=new FileInputStream(nomeArquivo);
            Reader r=new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br=new BufferedReader(r);) 
        {
            String[] infoVotacao = null;
            String linha = br.readLine();
            while((linha = br.readLine()) != null){
                linha = linha.replaceAll("\"", "");
                //System.out.println(linha);
                infoVotacao = linha.split(";");

                int CD_CARGO    = Integer.parseInt(infoVotacao[17]);
                int NR_VOTAVEL  = Integer.parseInt(infoVotacao[19]);
                int QT_VOTOS    = Integer.parseInt(infoVotacao[21]);

                if(CD_CARGO == cargo && !(NR_VOTAVEL >= 95 && NR_VOTAVEL <= 98)){
                    preencheVotosDeputado(CD_CARGO, NR_VOTAVEL, QT_VOTOS);
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void preencheVotosDeputado(int CD_CARGO, int NR_VOTAVEL, int QT_VOTOS) {
       
        Partido partidoTemp = partidos.get(NR_VOTAVEL);
        if(partidoTemp == null){ // voto em deputado
            Deputado deputadoTemp = deputados.get(NR_VOTAVEL);
            if(deputadoTemp == null){
                return;
            }
            partidoTemp = partidos.get(deputadoTemp.getNumeroDoPartido());
            if(deputadoTemp.getTipoDeVoto().equals("Válido (legenda)")){
                partidoTemp.adicionaVotosDeLegenda(QT_VOTOS);
                qtdVotosDeLegenda+=QT_VOTOS;
            }
            else{
                deputadoTemp.adicionaVotos(QT_VOTOS);
                partidoTemp.adicionaVotosNominais(QT_VOTOS);
                qtdVotosNominais+=QT_VOTOS;
            }
        }
        else{ // voto em partido
            partidoTemp.adicionaVotosDeLegenda(QT_VOTOS);
            qtdVotosDeLegenda+=QT_VOTOS;
        }
    }

    private void preencheTodosDeputados() {
        for(Deputado d : deputados.values()) {
            listaDeputados.add(d);
            partidos.get(d.getNumeroDoPartido()).adicionaDeputado(d);;
        }
    }
    private void preencheTodosPartidos() {
        for(Partido p : partidos.values()) {
            listaPartidos.add(p);
        }
    }

    public void ordenaDeputadosPorQuantidadeDeVotos(LinkedList<Deputado> deputados) {
        Collections.sort(deputados, new Comparator<Deputado>() {
            @Override
            public int compare(Deputado d1, Deputado d2) { 
                return d2.getQuantidadeDeVotos()-d1.getQuantidadeDeVotos(); 
            } 
        });
    }
    public void ordenaPartidosPorQuantidadeDeVotos(LinkedList<Partido> partidos) {
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
    public void ordenaPartidosPorDeputadoMaisVotado(LinkedList<Partido> listaPartidos){
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
    
    public void imprimeEleitos(BufferedWriter arquivo, int cargo) {
        try {
            int i=1;

            if(cargo==6)
                arquivo.append("Deputados federais eleitos:\n");
            else
                arquivo.append("Deputados estaduais eleitos:\n");

            for(Deputado d: listaDeputadosEleitos) {
                arquivo.append(i+" - ");
                if(d.getNumeroDaFederacao()!=-1) {
                    arquivo.append("*");
                }
                arquivo.append(d+"\n");
                i++;
            }
            arquivo.append("\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public void imprimeMaisVotados(BufferedWriter arquivo){
        try {
            int i=1;

            arquivo.append("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):\n");

            for(Deputado d: listaDeputados) {
                arquivo.append(i+" - ");
                if(d.getNumeroDaFederacao()!=-1) {
                    arquivo.append("*");
                }
                arquivo.append(d+"\n");
                listaDeputadosMaisVotados.add(d);

                i++;
                if (i>vagas)
                    break;
            }
            arquivo.append("\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public void imprimeDiscrepancias(BufferedWriter arquivo) {
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
        try {
            arquivo.append("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)\n");
            for (Deputado d : listaDeputadosQueDeveriamGanhar) {
                arquivo.append((listaDeputados.indexOf(d)+1)+" - ");
                if(d.getNumeroDaFederacao()!=-1){
                    arquivo.append("*");
                }
                arquivo.append(d+"\n");
            }
            arquivo.append("\n");

            arquivo.append("Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)\n");
            for (Deputado d : listaDeputadosQueDeveriamPerder) {
                arquivo.append((listaDeputados.indexOf(d)+1)+" - ");
                if(d.getNumeroDaFederacao()!=-1){
                    arquivo.append("*");
                }
                arquivo.append(d+"\n");
            }
            arquivo.append("\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public void imprimeVotosDosPartidos(BufferedWriter arquivo) {
        try {
            int i=1;

            arquivo.append("Votação dos partidos e número de candidatos eleitos:\n");
            for (Partido p : listaPartidos) {
                arquivo.append(i+" - "+p.votosPartido()+"\n");
                i++;
            }
            arquivo.append("\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public void imprimeMaisEMenosVotados(BufferedWriter arquivo){
        try {
            int i=1;

            arquivo.append("Primeiro e último colocados de cada partido:\n");
            for (Partido p : listaPartidos) {
                if(p.getQuantidadeDeVotosNominais() > 0) {
                    arquivo.append(i+" - "+p.maisEMenosVotados()+"\n");
                }
                i++;
            }
            arquivo.append("\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    public void imprimeEstatisticas(BufferedWriter arquivo){
        try {
            Locale brLocale=Locale.forLanguageTag("pt-BR");
            NumberFormat nf=NumberFormat.getInstance(brLocale);
            NumberFormat ni=NumberFormat.getInstance(brLocale);
            nf.setGroupingUsed(true);
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            
            arquivo.append("Eleitos, por faixa etária (na data da eleição):\n");
            arquivo.append("      Idade < 30: "+ni.format(idadeMenorQue30)+" ("+nf.format(((float)idadeMenorQue30/(float)vagas)*100)+"%)\n");
            arquivo.append("30 <= Idade < 40: "+ni.format(idadeMenorQue40)+" ("+nf.format(((float)idadeMenorQue40/(float)vagas)*100)+"%)\n");
            arquivo.append("40 <= Idade < 50: "+ni.format(idadeMenorQue50)+" ("+nf.format(((float)idadeMenorQue50/(float)vagas)*100)+"%)\n");
            arquivo.append("50 <= Idade < 60: "+ni.format(idadeMenorQue60)+" ("+nf.format(((float)idadeMenorQue60/(float)vagas)*100)+"%)\n");
            arquivo.append("60 <= Idade     : "+ni.format(idadeMaiorQue60)+" ("+nf.format(((float)idadeMaiorQue60/(float)vagas)*100)+"%)\n");
            arquivo.append("\n");

            arquivo.append("Eleitos, por gênero:\n");
            arquivo.append("Feminino:  "+ni.format(qtdFeminino)+" ("+nf.format(((float)qtdFeminino/(float)vagas)*100)+"%)\n");
            arquivo.append("Masculino: "+ni.format(qtdMasculino)+" ("+nf.format(((float)qtdMasculino/(float)vagas)*100)+"%)\n");
            arquivo.append("\n");

            arquivo.append("Total de votos válidos:    "+ni.format(qtdVotosValidos)+"\n");
            arquivo.append("Total de votos nominais:   "+ni.format(qtdVotosNominais)+" ("+nf.format(((float)qtdVotosNominais/(float)qtdVotosValidos)*100)+"%)\n");
            arquivo.append("Total de votos de legenda: "+ni.format(qtdVotosDeLegenda)+" ("+nf.format(((float)qtdVotosDeLegenda/(float)qtdVotosValidos)*100)+"%)\n");

        } catch(IOException e) {
            System.out.println(e);
        }
    }

    /*
    public void imprimePartidos(){
        System.out.println("Número de vagas: "+vagas+"\n");
        for(Partido p : partidos.values()) {
            System.out.println(p);
            for(Deputado d : p.getDeputados()) {
                System.out.println(d);
            }
            System.out.println();
        }
    }
    */
    
    public void imprimeInformacoes(BufferedWriter arquivo, int cargo) {
        qtdVotosValidos=qtdVotosNominais+qtdVotosDeLegenda;
        try {
            Locale brLocale=Locale.forLanguageTag("pt-BR");
            NumberFormat ni=NumberFormat.getInstance(brLocale);
            arquivo.append("Número de vagas: "+ni.format(vagas)+"\n\n");

            preencheTodosDeputados();
            preencheTodosPartidos();
            ordenaDeputadosPorQuantidadeDeVotos(listaDeputados);
            ordenaDeputadosPorQuantidadeDeVotos(listaDeputadosEleitos);
            ordenaPartidosPorQuantidadeDeVotos(listaPartidos);
            imprimeEleitos(arquivo, cargo);
            imprimeMaisVotados(arquivo);
            imprimeDiscrepancias(arquivo);
            imprimeVotosDosPartidos(arquivo);
            ordenaPartidosPorDeputadoMaisVotado(listaPartidos);
            imprimeMaisEMenosVotados(arquivo);
            imprimeEstatisticas(arquivo);

        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
