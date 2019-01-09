import Core.Pesquisa;
import LeituraFicheiros.LeituraDocumentos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PesquisaTest {

    //Testar se a remoção de caracteres de pontuação do método eliminarCaracteresPontuacao funciona
    @Test
    public void testId_TP3() throws IOException {

        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "olá tudo bem?";
        String output_esperado = "ola tudo bem?";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }
    //Testar se a string é transformada em minusculas no método eliminarCaracteresPontuacao
    @Test
    public void testId_TP4() throws IOException {

        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "Ola tudo bem? Sim";
        String output_esperado = "ola tudo bem? sim";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }

    //Testar se os digitos são removidos no método eliminarDigitos
    @Test
    public void testId_TP5(){
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        String input = "Eu sou 5vezes mais forte";
        String output = p.eliminarDigitos(input);
        assertEquals("Eu sou vezes mais forte", output);
    }

    //Testar se o método contarNumDocsContemPalavra devolve o número de documentos q contem uma palavra especifica
    @Test
    public void testId_TP6() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        LeituraDocumentos ld = new LeituraDocumentos("files/filesTestes/TP/");
        String[] conteudos = ld.lerDocumentos();
        String palavra = "Testar";
        assertEquals(p.contarNumDocsContemPalavra(conteudos, palavra), 2);
    }

    //Testar se o método definirMatrizM() guarda a primeira palavra do primeiro documento no indice certo
    @Test
    public void testId_TP7() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        String palavra = "teste";
        assertEquals(palavra, p.getM()[2][0].getPalavra());
    }

    //Testar se o método definirMatrizM() guarda a contagem da primeira palavra do primeiro documento no indice certo
    @Test
    public void testId_TP8() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        assertEquals(1, p.getM()[0][0].getContagem());
    }

    // Testar se o método definirMatrizM() tem a matriz com o número de colunas igual ao máximo de palavras diferentes
    @Test
    public void testId_TB5() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB5/");
        p.definirMatrizM();
        assertEquals(3, p.getM()[0].length);
    }

    // Testar se o método definirMatrizM() tem a matriz com o número de colunas igual a 1 com a documento com 3 palavras iguais
    @Test
    public void testId_TB6() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB6/");
        p.definirMatrizM();
        assertEquals(1, p.getM()[0].length);
    }

    //Testar se o método definirMatrizM() guarda a contagem de mais que uma palavra igual
    @Test
    public void testId_TB7() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TB6/");
        p.definirMatrizM();
        assertEquals(3, p.getM()[0][0].getContagem());
    }
}