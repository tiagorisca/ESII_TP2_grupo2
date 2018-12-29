package LeituraFicheiros;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class LeituraFicheiros {
    int numDocs ;
    String path ;

    public LeituraFicheiros(String path) {
        this.path = path;
        File folder = new File(this.path);
        File[] list = folder.listFiles();
        numDocs = list.length;
    }

    public int getNumDocs() {
        return numDocs;
    }

    public String[] lerDocumentos() throws IOException {
        //Lista de ficheiros
        File folder = new File(this.path);
        File[] list = folder.listFiles();

        //Lista de strings com os conteudos dos ficheiros
        String[] lista = new String[this.numDocs];

        for(int i=0; i<list.length; i++){
            String name = list[i].getName();
            System.out.println(name);
            InputStream is = new FileInputStream(path + name);
            BufferedReader buf = new BufferedReader((new InputStreamReader(is)));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while(line != null){
                sb.append(line);
                line = buf.readLine();
            }

            lista[i] = sb.toString();
            System.out.println("Contents: " + lista[i]);
        }


        return lista;
    }
}
