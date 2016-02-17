/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author andresbailen93
 */
public class Firma {

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        
        //Generamos el par de claves publica y privada
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        Signature mySign = Signature.getInstance("MD5withRSA");
        mySign.initSign(priv);
        mySign.update("Texto Cifrado".getBytes());
        
        //Escribimos las claves en un archivo
        byte[] byteSignedData = mySign.sign();
        try (FileOutputStream fospriv = new FileOutputStream("clavepublica")) {
            fospriv.write(pub.getEncoded());
        }
        try (FileOutputStream fospub = new FileOutputStream("claveprivada")) {
            fospub.write(priv.getEncoded());
        }
        
        //Leemos las claves
        FileInputStream fis = new FileInputStream("clavepublica");
        byte[] KeyDataPriv = new byte[2048];
        int i;
        i = fis.read(KeyDataPriv);
        byte[] KeyDataPriv2 = new byte[i];
        int c;
        for (c = 0; c < i; c++) {
            KeyDataPriv2[c] = KeyDataPriv[c];
        }
        FileInputStream fis2 = new FileInputStream("claveprivada");
        byte[] KeyDataPub = new byte[2048];
        int j = fis2.read(KeyDataPub);
        byte[] KeyDataPub2 = new byte[j];
        int d;
        for (d = 0; d < j; d++) {
            KeyDataPub2[d] = KeyDataPub[d];
        }
        X509EncodedKeySpec puSpec = new X509EncodedKeySpec(KeyDataPub2);
        PKCS8EncodedKeySpec prSpec = new PKCS8EncodedKeySpec(KeyDataPriv2);
        
        //Firmamos el texto y comprobamos si esta correctamente firmado
        Signature myVerifySign = Signature.getInstance("MD5withRSA");
        myVerifySign.initVerify(pub);
        myVerifySign.update("Texto Cifrado".getBytes());
        boolean verifySign = myVerifySign.verify(byteSignedData);
        
        if (verifySign == false) {
            System.out.println("No se ha validado la firma");
        } else {
            System.out.println("Firma validada correctamente");
        }
    }
}
