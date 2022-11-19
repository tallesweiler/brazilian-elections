import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {

    //números 95, 96, 97, 98 representam casos de votos em branco, nulos ou anulados

    public static void geraArquivoDeputados(String nomeArquivo, String sigla) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testesDeputados/teste"+sigla+".txt"));

            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    buffWrite.append(fazLinhaDeputados(scan) + "\n");
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println("a");
                }
            }
            buffWrite.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("b");
        } catch (IOException e) {
            System.out.println("c");
        }
    }

    public static void geraArquivoVotos(String nomeArquivo, String sigla) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            Politico politico=new Politico(11122);
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testesVotos/teste"+sigla+".txt"));
            
            String linha=br.readLine();
            linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    buffWrite.append(fazLinhaVotos(scan, politico) + "\n");
                    linha=br.readLine();
                } catch(NoSuchElementException e) {
                    System.out.println("aa");
                }
            }
            System.out.println(politico.getQuantidadeDeVotos());
            buffWrite.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("bb");
        } catch (IOException e) {
            System.out.println("cc");
        }
    }

    public static void passaDireto(int i, Scanner scan) {
        for (int aux=0;aux<i;aux++) {
            scan.next();
        }
    }

    public static String fazLinhaDeputados(Scanner scan) {
        String CD_CARGO;
        String NR_CANDIDATO;
        String NM_CANDIDATO;
        String NM_URNA_CANDIDATO;
        String NR_PARTIDO;
        String SG_PARTIDO;
        String NR_FEDERACAO;
        String DT_NASCIMENTO;
        String CD_GENERO;
        String CD_SIT_TOT_TURNO;
        String NM_TIPO_DESTINACAO_VOTOS;
        String CD_SITUACAO_CANDIDATO_TOT;

        scan.useDelimiter(";");
        passaDireto(13, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.next();
        scan.useDelimiter(";");
        passaDireto(3, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_CANDIDATO=scan.next();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NM_CANDIDATO=scan.next();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NM_URNA_CANDIDATO=scan.next();
        scan.useDelimiter(";");
        passaDireto(9, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_PARTIDO=scan.next();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        SG_PARTIDO=scan.next();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_FEDERACAO=scan.next();
        scan.useDelimiter(";");
        passaDireto(12, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        DT_NASCIMENTO=scan.next();
        scan.useDelimiter(";");
        passaDireto(3, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_GENERO=scan.next();
        scan.useDelimiter(";");
        passaDireto(11, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_SIT_TOT_TURNO=scan.next();
        scan.useDelimiter(";");
        passaDireto(11, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NM_TIPO_DESTINACAO_VOTOS=scan.next();
        scan.useDelimiter(";");
        passaDireto(1, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_SITUACAO_CANDIDATO_TOT=scan.next();
        scan.nextLine();

        return CD_CARGO+";"+NR_CANDIDATO+";"+NM_CANDIDATO+";"+NM_URNA_CANDIDATO+";"+NR_PARTIDO+";"+SG_PARTIDO+";"+NR_FEDERACAO+";"+DT_NASCIMENTO+";"+CD_GENERO+";"+CD_SIT_TOT_TURNO+";"+NM_TIPO_DESTINACAO_VOTOS+";"+CD_SITUACAO_CANDIDATO_TOT+";";
    }

    public static String fazLinhaVotos(Scanner scan, Politico politico) {
        String CD_CARGO;
        String NR_VOTAVEL;
        String QT_VOTOS;

        scan.useDelimiter(";");
        passaDireto(17, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_CARGO=scan.next();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        NR_VOTAVEL=scan.next();
        scan.useDelimiter(";");
        passaDireto(2, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        QT_VOTOS=scan.next();
        scan.useDelimiter(";");
        passaDireto(4, scan);
        scan.nextLine();

        int teste = Integer.parseInt(NR_VOTAVEL);

        if (teste==politico.getNumeroDoCandidato())
            politico.setQuantidadeDeVotos(politico.getQuantidadeDeVotos()+Integer.parseInt(QT_VOTOS));

        return CD_CARGO+";"+NR_VOTAVEL+";"+QT_VOTOS+";";
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);
            String sigla=scan.nextLine();
            geraArquivoDeputados("consulta_cand_2022/consulta_cand_2022_"+sigla+".csv", sigla);
            geraArquivoVotos("arquivos_de_votacao/votacao_secao_2022_"+sigla+".csv", sigla);
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("d");
        } catch (IOException e) {
            System.out.println("e");
        }
    }
}
