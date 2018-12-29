package Demo;


import JFrames.PesquisaJFrame;
import Core.Pesquisa;
import LeituraFicheiros.LeituraDocumentos;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        PesquisaJFrame pf = new PesquisaJFrame(new Pesquisa());
        pf.setVisible(true);
        LeituraDocumentos file = new LeituraDocumentos("files/");
        String[] lista = file.lerDocumentos();
        for(int i = 0; i<lista.length; i++){
            System.out.println(lista[i]);

        }
    }

}