/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ayungan.com.signature;

/**
 *
 * @author Alex
 */
public class SignatureTest {

	public static void main(String[] args) {
		String xmlPath = "factura.xml";
		String pathSignature = "mycert.p12";
		String passSignature = "12345678";

		System.out.println("Ruta del XML de entrada: " + xmlPath);
		System.out.println("Ruta Certificado: " + pathSignature);
		System.out.println("Clave del Certificado: " + passSignature);

		try {
			byte[] bxmlOriginal = ConvertFile.readBytesFromFile(xmlPath);
			byte[] cert = ConvertFile.readBytesFromFile(pathSignature);
			// ConvertFile.saveBytes(bxmlOriginal, "E:\\dacturasaveByte.xml");
			byte[] data2 = SignatureXAdESBES.firmarByteData(bxmlOriginal, cert, passSignature);
			ConvertFile.saveBytes(data2, "D:\\fatura_sign.xml");
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
}
