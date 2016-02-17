/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import static criptography.ClavesAsimetricasAleatorias.bytesToHexString;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author andresbailen93
 */
public class HMACverificacion {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        //CLAVE SIMETRICA
        //Generación de claves simétricas a partir de PARÁMETROS
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        SecretKey secretKey2 = keyGen.generateKey();
        FileOutputStream fos = new FileOutputStream("fich");
        fos.write(secretKey2.getEncoded());
        fos.close();

        KeyGenerator kg = KeyGenerator.getInstance("HmacSHA1");
        SecretKey key = kg.generateKey();
        Mac m = Mac.getInstance("HmacSHA1");
        m.init(key);

        byte[] buffer = new byte[1000];
        FileInputStream in = new FileInputStream("textillo2.txt");
        int bytesLeidos = in.read(buffer, 0, 1000);

        while (bytesLeidos != -1) {
            m.update(buffer);
            bytesLeidos = in.read(buffer, 0, 1000);
        }
        byte[] mac = m.doFinal();
        in.close();
//Obtenemos clave simetrica

        FileInputStream fis = new FileInputStream("fich");
        byte[] desKeyData = new byte[256];
        int i = fis.read(desKeyData);
        byte[] desKeyData2 = new byte[i];
        int d;
        
        for (d = 0; d < i; d++) {
            desKeyData2[d] = desKeyData[d];
        }
        
        SecretKeySpec secretKey = new SecretKeySpec(desKeyData2, "DES");
        System.out.println(bytesToHexString(secretKey.getEncoded()));
        System.out.println(mac);
    }

}
