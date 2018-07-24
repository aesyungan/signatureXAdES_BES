/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ayungan.com.signature;

import ayungan.com.firmas.XAdESBESSignature;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author Alex
 */
public class SignatureXAdESBES extends SignatureXML {

    private byte[] dataOriginal;

    public SignatureXAdESBES(byte[] dataOriginal) {
        this.dataOriginal = dataOriginal;
    }

    public static byte[] firmarByteData(byte[] xmlOriginal, String pathSignature, String passSignature)
            throws CertificateException, IOException {
        SignatureXAdESBES signature = new SignatureXAdESBES(xmlOriginal);
        signature.setPassSignature(passSignature);
        signature.setPathSignature(pathSignature);
        return signature.execute();
    }

    @Override
    protected DataToSign createDataToSign() {
        DataToSign datosAFirmar = new DataToSign();

        datosAFirmar.setXadesFormat(EnumFormatoFirma.XAdES_BES);

        datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosAFirmar.setXMLEncoding("UTF-8");
        datosAFirmar.setEnveloped(true);
        datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosAFirmar.setParentSignNode("comprobante");

        Document docToSign = null;
        try {
            docToSign = getDocumentFromByte(this.dataOriginal);
        } catch (IOException ex) {
            Logger.getLogger(XAdESBESSignature.class.getName()).log(Level.SEVERE, null, ex);
        }
        datosAFirmar.setDocument(docToSign);

        return datosAFirmar;
    }
}
