package firmarsobrexml;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.w3c.dom.*;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Firma un documento XML
 *
 * @author Alex
 */
public class CreateSignature {

    private static final String KEYSTORE_TYPE = "JKS";
    private static final String KEYSTORE_FILE = "facturacion.jks";
    private static final String KEYSTORE_PASSWORD = "abc12345";
    private static final String PRIVATE_KEY_PASSWORD = "abc1234";
    private static final String PRIVATE_KEY_ALIAS = "facturacion";

    /**
     * Punto de entrada al ejemplo
     */
    public static void main(String args[]) throws Exception {
        org.apache.xml.security.Init.init();

        Document doc = DOMUtils.createSampleDocument();

        Constants.setSignatureSpecNSprefix("");	// Sino, pone por defecto como prefijo: "ns"

        // Cargamos el almacen de claves
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        ks.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        // Obtenemos la clave privada, pues la necesitaremos para encriptar.
        PrivateKey privateKey = (PrivateKey) ks.getKey(PRIVATE_KEY_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());
        File signatureFile = new File("signature.xml");
        String baseURI = signatureFile.toURL().toString();	// BaseURI para las URL Relativas.

        // Instanciamos un objeto XMLSignature desde el Document. El algoritmo de firma será DSA
        XMLSignature xmlSignature = new XMLSignature(doc, baseURI, XMLSignature.ALGO_ID_SIGNATURE_DSA);

        // Añadimos el nodo de la firma a la raiz antes de firmar.
        // Observe que ambos elementos pueden ser mezclados en una forma con referencias separadas
        doc.getDocumentElement().appendChild(xmlSignature.getElement());

        // Creamos el objeto que mapea: Document/Reference
        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);

        // Añadimos lo anterior Documento / Referencia
        // ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
        xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

        // Añadimos el KeyInfo del certificado cuya clave privada usamos
        X509Certificate cert = (X509Certificate) ks.getCertificate(PRIVATE_KEY_ALIAS);
        xmlSignature.addKeyInfo(cert);
        xmlSignature.addKeyInfo(cert.getPublicKey());

        // Realizamos la firma
        xmlSignature.sign(privateKey);

        // Guardamos archivo de firma en disco
        DOMUtils.outputDocToFile(doc, signatureFile);
    }
}
