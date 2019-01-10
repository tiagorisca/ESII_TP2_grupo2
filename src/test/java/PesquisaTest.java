import Core.ContagemPalavra;
import Core.Pesquisa;
import LeituraFicheiros.LeituraDocumentos;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    //Testar se o método definirMatrizQ() guarda as palavras pesquisadas no array
    @Test
    public void testId_TP9() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("ola ola tudo bem");
        String[] expected = {"ola", "tudo", "bem"};
        assertEquals(expected[0], p.getQ()[0].getPalavra());
        assertEquals(expected[1], p.getQ()[1].getPalavra());
        assertEquals(expected[2], p.getQ()[2].getPalavra());
    }


    //Testar se o método definirMatrizQ() guarda a quantidade certa de palavras iguais pesquisadas no array
    @Test
    public void testId_TP10() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizQ("ola ola tudo bem");
        int[] expected = {2, 1, 1};
        assertEquals(expected[0], p.getQ()[0].getContagem());
        assertEquals(expected[1], p.getQ()[1].getContagem());
        assertEquals(expected[2], p.getQ()[2].getContagem());
    }

    //Testar metodo de verificacaoSemelhanca, verificar o calculo de equaçao
    @Test
    public void testId_TP11() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.verificacaoSemelhanca();
        double calculo = p.getM()[0][0].getContagem() * (1.0 + java.lang.Math.log10((3.0) / (2.0)));
        assertEquals(calculo, p.getM()[0][0].getValor());
    }

    //Verificar se o grau de semelhança é superior a 0 se ouver grau de semelhança entre os ficheiros e a pesquisa
    @Test
    public void testId_TP12() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste teste teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertTrue(0 < p.getGrauSim()[0]);
    }

    //Verificar se o grau de semelhança é 0 se não ouver grau de semelhança entre os ficheiros e a pesquisa
    @Test
    public void testId_TP13() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("adoro");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(0 , p.getGrauSim()[2]);
    }

    //Verificar se o grau de semelhança é ordenado
    @Test
    public void testId_TP14() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(p.getLd().getNomesFicheiros()[0] , "ficheiro 4.txt");
    }

    //Verificar se a limitação de ficheiros foi realizada
    @Test
    public void testId_TP15() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        String[] values = p.retornarFicheirosPorLimite(2);
        assertEquals(2, values.length);
    }

    //Verificar se método retornaFicheirosLimiteGrau retorna apenas os ficheiros com grau superior ao indicado
    @Test
    public void testId_TP16() throws IOException {
        Pesquisa p = new Pesquisa("files/filesTestes/TP/");
        p.definirMatrizM();
        p.definirMatrizQ("teste teste teste");
        p.verificacaoSemelhanca();
        p.grauSemelhanca();
        assertEquals(p.retornarFicheirosLimiteGrau(100).length, 1);
    }

}