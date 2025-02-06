package br.com.paulopinheiro.jsimbaconv;

import br.com.paulopinheiro.jsimbaconv.control.SimbaConvService;
import java.io.File;
import java.io.IOException;

public class Jsimbaconv {

    public static void main(String[] args) throws IOException {
        //File zipFile = new File("P:\\Documentos\\TesteTXTPDF\\ZipTeste.zip");
        File zipFile = new File("/home/paulopinheiro/temp/convertSIMBA/ZipTeste.zip");
        SimbaConvService simbaService = new SimbaConvService(zipFile);
    }
}
