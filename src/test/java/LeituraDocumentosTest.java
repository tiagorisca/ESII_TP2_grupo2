import Core.Pesquisa;
import LeituraFicheiros.LeituraDocumentos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeituraDocumentosTest {

    /**
     * Testar se devolve null se não houver documentos para ler
     * Criar diretoria na pasta files/filesTestes/ chamada folderTest
     * @throws IOException

    @Test
    public void testId_TP1() throws IOException {
        LeituraDocumentos ld = new LeituraDocumentos("files/folderTest/");
        String[] ler = ld.lerDocumentos();

        assertEquals(null, ler);
    }
     */

    /**
     * Testar se a leitura é feita corretamente com vários tipos de ficheiros
     *
     * @throws IOException
     */
    @Test
    public void testId_TP2() throws IOException {
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TP/");
        String[] ler = ld.lerDocumentos();

        assertEquals("Testar documento do tipo: txt", ler[0]);
        assertEquals("Testar documento do tipo: docx", ler[1]);
    }


    /**
     * Testar se a leitura é feita corretamente com ficheiros txt
     *
     * @throws IOException
     */
    @Test
    public void testId_TB1() {
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TB1/");
        String[] ler = new String[0];
        try {
            ler = ld.lerDocumentos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("Gosto de engenharia de software", ler[0]);
    }

    /**
     * Testar se a leitura é feita corretamente com ficheiros docx
     *
     * @throws IOException
     */
    @Test
    public void testId_TB2() throws IOException {
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TB2/");
        String[] ler = ld.lerDocumentos();

        assertEquals("Ficheiro teste: docx", ler[0]);
    }


    /**
     * Testar se a leitura é feita corretamente com ficheiros que não sejam do tipo txt, docx
     *
     * @throws IOException
     */
    @Test
    public void testId_TB3() throws IOException {
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TB4/");
        String[] ler = ld.lerDocumentos();

        assertEquals("", ler[0]);
    }
}
