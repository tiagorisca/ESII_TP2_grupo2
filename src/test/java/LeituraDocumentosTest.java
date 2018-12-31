import Core.Pesquisa;
import LeituraFicheiros.LeituraDocumentos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeituraDocumentosTest {

    //Testar se a leitura é feita corretamente
    @Test
    public void testId_TP1() throws IOException {

        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/");
        String[] ler = ld.lerDocumentos();
            assertEquals("Gosto de engenharia de software", ler[0]);
            assertEquals("Nao gosto de engenharia de software", ler[1]);
    }

    //Testar se devolve null se não houver documentos para ler
    @Test
    public void testId_TP2() throws IOException {

        LeituraDocumentos ld = new LeituraDocumentos("files/folderTest/");
        String[] ler = ld.lerDocumentos();
        assertEquals(null, ler);
    }
}
