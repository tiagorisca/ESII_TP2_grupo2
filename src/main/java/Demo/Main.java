package Demo;


import JFrames.PesquisaJFrame;
import Core.Pesquisa;
import LeituraFicheiros.LeituraFicheiros;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /**
        PesquisaJFrame pf = new PesquisaJFrame(new Pesquisa());
        pf.setVisible(true);**/
        LeituraFicheiros file = new LeituraFicheiros("files/");
        String[] lista = file.lerDocumentos();
        for(int i=0; i<lista.length; i++){
            System.out.println(lista[i]);
        }
    }

}