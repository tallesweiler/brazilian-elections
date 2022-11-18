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
    public static void geraArquivo(String nomeArquivo, String sigla) throws FileNotFoundException, IOException {
        try(FileInputStream in = new FileInputStream(nomeArquivo);
            Reader r = new InputStreamReader(in, "ISO-8859-1");
            BufferedReader br = new BufferedReader(r);) 
        {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter("testes/teste"+sigla+".txt"));

            String linha=br.readLine();

            while(linha!=null) {
                try (Scanner scan = new Scanner(linha)) {
                    buffWrite.append(fazLinha(scan) + "\n");
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

    public static void passaDireto(int i, Scanner scan) {
        for (int aux=0;aux<i;aux++) {
            scan.next();
        }
    }

    public static String fazLinha(Scanner scan) {
        String CD_CARGO;
        String NR_CANDIDATO;
        String NM_CANDIDATO;
        String NM_URNA_CANDIDATO;
        String CD_DETALHE_SITUACAO_CAND;
        String NR_PARTIDO;
        String SG_PARTIDO;
        String NR_FEDERACAO;
        String DT_NASCIMENTO;
        String CD_GENERO;
        String CD_SIT_TOT_TURNO;

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
        passaDireto(6, scan);
        scan.useDelimiter("\"");
        passaDireto(1, scan);
        CD_DETALHE_SITUACAO_CAND=scan.next();
        scan.useDelimiter(";");
        passaDireto(3, scan);
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
        passaDireto(14, scan);
        scan.nextLine();

        return CD_CARGO+";"+NR_CANDIDATO+";"+NM_CANDIDATO+";"+NM_URNA_CANDIDATO+";"+CD_DETALHE_SITUACAO_CAND+";"+NR_PARTIDO+";"+SG_PARTIDO+";"+NR_FEDERACAO+";"+DT_NASCIMENTO+";"+CD_GENERO+";"+CD_SIT_TOT_TURNO+";";
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);
            String sigla=scan.nextLine();
            geraArquivo("consulta_cand_2022/consulta_cand_2022_"+sigla+".csv", sigla);
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("d");
        } catch (IOException e) {
            System.out.println("e");
        }
    }
}
