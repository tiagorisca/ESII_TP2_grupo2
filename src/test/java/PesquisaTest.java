import Core.Pesquisa;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PesquisaTest {

    //Testar se a remoção de caracteres de pontuação do método eliminarCaracteresPontuacao funciona
    @Test
    public void testId_TP3() throws IOException {

        Pesquisa p = new Pesquisa();
        String input = "olá tudo bem?";
        String output_esperado = "ola tudo bem?";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }
    //Testar se a string é transformada em minusculas no método eliminarCaracteresPontuacao
    @Test
    public void testId_TP4() throws IOException {

        Pesquisa p = new Pesquisa();
        String input = "Ola tudo bem? Sim";
        String output_esperado = "ola tudo bem? sim";
        String output = p.eliminarCaracteresPontuacao(input);
        assertEquals(output_esperado, output);
    }
}