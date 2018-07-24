/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firmarsobrexml;

import ayungan.com.firmas.XAdESBESSignature;
import java.io.IOException;
import java.security.cert.CertificateException;

/**
 *
 * @author Alex
 */
public class FirmarSobreXMl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CertificateException, IOException {
        String xmlPath = "D:\\development\\App Java\\NetBeansProjects\\firmarSobreXMl\\factura.xml";
        String pathSignature = "mycert.p12";
        String passSignature = "12345678";
        String pathOut = "E:\\";
        String nameFileOut = "factura_sign.xml";

        System.out.println("Ruta del XML de entrada: " + xmlPath);
        System.out.println("Ruta Certificado: " + pathSignature);
        System.out.println("Clave del Certificado: " + passSignature);
        System.out.println("Ruta de salida del XML: " + pathOut);
        System.out.println("Nombre del archivo salido: " + nameFileOut);

        try {
            XAdESBESSignature.firmar(xmlPath, pathSignature, passSignature, pathOut, nameFileOut);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

}
