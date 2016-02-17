/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author andresbailen93
 */
public class ClavesSimetricasAleatorias {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        
        //************************GENERADOR DE CLAVES SIMETRICAS
        // Creamos nuestra clave
        String sk = "0123456789ABCDEF0123456789ABCDEF";
        //Imprimimos la clave para compararla en consola
        System.out.println(sk);
        //Creamos nuestra clave pasandola primero a bytes y le decimos que nos cree una clave AES
        SecretKeySpec secretKey = new SecretKeySpec(hexStringToBytes(sk), "AES");

        //imprimimos la clave de nuevo para ver que es la misma clave que hemos creado
        System.out.println(bytesToHexString(secretKey.getEncoded()));

        //***********************GENERADOR DE CLAVES SIMETRICAS ALEATORIAS

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom(); 
        keyGen.init(random);
        //keyGen.init(256); // for example
        SecretKey secretKeyrand = keyGen.generateKey();

        System.out.println(bytesToHexString(secretKeyrand.getEncoded()));
    }

    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
