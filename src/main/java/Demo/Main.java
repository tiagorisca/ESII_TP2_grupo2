package Demo;


import JFrames.PesquisaJFrame;
import Core.Pesquisa;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, NullPointerException {
        Pesquisa p = new Pesquisa("files/documentos/");
        PesquisaJFrame pf = new PesquisaJFrame(p);
        pf.setVisible(true);
    }
}