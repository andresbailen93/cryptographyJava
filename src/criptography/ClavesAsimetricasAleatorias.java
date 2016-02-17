/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author andresbailen93
 */
public class ClavesAsimetricasAleatorias {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeySpecException {
        
        
        //LECTURA DE UNA CLAVE PRIVADA 
        // FALLIDO
        String privatepassFile = "localhost.pk8";

        byte[] buf;
        try (RandomAccessFile raf = new RandomAccessFile(privatepassFile, "r")) {
            buf = new byte[(int) raf.length()];
            raf.readFully(buf);
        }

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(buf);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        //PrivateKey privKey = kf.generatePrivate(spec);
        //System.out.println(bytesToHexString(privKey.getEncoded()));
        
        

        //GENERACION DE UN PAR DE CLAVES ALEATORIAS
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        System.out.println(bytesToHexString(priv.getEncoded()));
        System.out.println(priv);

        System.out.println(bytesToHexString(pub.getEncoded()));
        System.out.println(pub);
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
