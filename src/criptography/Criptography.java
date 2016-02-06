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
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author inftel23
 */
public class Criptography {
        final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeySpecException {
        // TODO code application logic here
     
        
        //GENERADOR DE CLAVES SIMETRICAS
        
        String sk = "0123456789ABCDEF0123456789ABCDEF";
        System.out.println(sk);
        hexStringToBytes(sk);
        SecretKeySpec secretKey = new SecretKeySpec(hexStringToBytes(sk), "AES");
        //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
        
        System.out.println(bytesToHexString(secretKey.getEncoded()));
        //SecretKey secretKey = keyFactory.generateSecret(secretKey);
        
        String privatepassFile = "localhost.pk8";
        /*
        FileInputStream fisser = new FileInputStream(privatepassFile);
        
        byte[] block1 = new byte[1000];
        fisser.read(block1);
        
        PKCS8EncodedKeySpec prSpec = new PKCS8EncodedKeySpec (block1);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privkey = kf.generatePrivate(prSpec);
        
        System.out.println(privkey.toString());
        */
        /*
        File f = new File(privatepassFile);
        FileInputStream fisser = new FileInputStream(f);
        byte[] keyBytes;
            try (DataInputStream dis = new DataInputStream(fisser)) {
                keyBytes = new byte[(int) f.length()];
                dis.readFully(keyBytes);
            }

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        System.out.println(kf.generatePrivate(spec));
        */
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        System.out.println(priv);
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
