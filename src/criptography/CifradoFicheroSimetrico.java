/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import static criptography.ClavesAsimetricasAleatorias.bytesToHexString;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author andresbailen93
 */
public class CifradoFicheroSimetrico {

    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        
//Generación de claves simétricas a partir de PARÁMETROS 
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecureRandom random = new SecureRandom();
        keyGen.init(random);
        SecretKey secretKey2 = keyGen.generateKey();
        
        System.out.println(bytesToHexString(secretKey2.getEncoded()));
        
        FileOutputStream fos = new FileOutputStream("clavepublica.txt");
        fos.write(secretKey2.getEncoded());
        fos.close();
        
        Cipher cifrado = Cipher.getInstance("DES");
        SecretKeySpec key = new SecretKeySpec(secretKey2.getEncoded(), "DES");
        cifrado.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] buffer = new byte[1000];
        byte[] bufferCifrado;
        
        FileInputStream in = new FileInputStream("HolaMundo.txt");
        FileOutputStream out = new FileOutputStream("HolaMundoCifrado.txt");
        
        int bytesLeidos = in.read(buffer, 0, 1000);
        
        while (bytesLeidos != -1) {
            bufferCifrado = cifrado.update(buffer, 0, bytesLeidos);
            out.write(bufferCifrado);
            bytesLeidos = in.read(buffer, 0, 1000);
        }
        bufferCifrado = cifrado.doFinal();
        out.write(bufferCifrado);
        in.close();
        out.close();
        
//Obtenemos clave simetrica
        FileInputStream fis = new FileInputStream("clavepublica.txt");
        byte[] desKeyData = new byte[256];
        int i = fis.read(desKeyData);
        byte[] desKeyData2 = new byte[i];
        int d;
        for (d = 0; d < i; d++) {
            desKeyData2[d] = desKeyData[d];
        }
        
        SecretKeySpec secretKey = new SecretKeySpec(desKeyData2, "DES");
        
        System.out.println(bytesToHexString(secretKey.getEncoded()));
        
        cifrado.init(Cipher.DECRYPT_MODE, secretKey);
        in = new FileInputStream("HolaMundoCifrado.txt");
        out = new FileOutputStream("HolaMundoDescifrado.txt");
        byte[] bufferPlano;
        bytesLeidos = in.read(buffer, 0, 1000);
        
        while (bytesLeidos != -1) {
            bufferPlano = cifrado.update(buffer, 0, bytesLeidos);
            out.write(bufferPlano);
            bytesLeidos = in.read(buffer, 0, 1000);
        }
        
        bufferPlano = cifrado.doFinal();
        out.write(bufferPlano);
        in.close();
        out.close();
    }
}
