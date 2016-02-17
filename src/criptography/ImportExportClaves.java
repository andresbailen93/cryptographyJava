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
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * En esta clase revolveremos el problema de crear una claves asimetricas, guardarlas en un fichero y volver a leerlas
 * @author andresbailen93
 */
public class ImportExportClaves {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        
        
        //Generamos el par de claves RSA prublica y privada
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        
        //Imprimimos por pantalla el par de claves para comprobar luego
        System.out.println(bytesToHexString(priv.getEncoded()));
        System.out.println(bytesToHexString(pub.getEncoded()));

        //EScribimos la clave publica en un fichera
        try (FileOutputStream fospriv = new FileOutputStream("clavepublica")) {
            fospriv.write(pub.getEncoded());
        }
        //Escribimos la clave publica en un fichero
        try (FileOutputStream fospub = new FileOutputStream("claveprivada")) {
            fospub.write(priv.getEncoded());
        }

        FileInputStream fis = new FileInputStream("clavepublica");
        byte[] desKeyDataPriv = new byte[2048];
        int i;
        i = fis.read(desKeyDataPriv);
        byte[] desKeyDataPriv2 = new byte[i];
        int c;
        
        for (c = 0; c < i; c++) {
            desKeyDataPriv2[c] = desKeyDataPriv[c];
        }
        
        //
        FileInputStream fis2 = new FileInputStream("claveprivada");
        byte[] desKeyDataPub = new byte[2048];
        int j = fis2.read(desKeyDataPub);
        byte[] desKeyDataPub2 = new byte[j];
        int d;
        
        for (d = 0; d < j; d++) {
            desKeyDataPub2[d] = desKeyDataPub[d];
        }

        X509EncodedKeySpec puSpec = new X509EncodedKeySpec(desKeyDataPub2);
        PKCS8EncodedKeySpec prSpec = new PKCS8EncodedKeySpec(desKeyDataPriv2);
        
        System.out.println(bytesToHexString(puSpec.getEncoded()));
        System.out.println(bytesToHexString(prSpec.getEncoded()));
        
    }

}
