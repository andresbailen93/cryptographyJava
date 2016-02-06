/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptography;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * En esta clase estan implementada la primera practica donde calculamos los
 * hash MD5 y SHA-1 y la medicion de tiempos
 *
 * @author andresbailen93
 */
public class HashMain {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        //Asignamos un buffer para el archivo ya que podemos leerlo trozo a trozo para no forzar la memoria
        byte[] block = new byte[256];
        int i;
        String cleartextFile = "Tunnelblick_Uninstaller_1.8_build_4405.dmg";
        //Leemos el fichero
        FileInputStream fis = new FileInputStream(cleartextFile);
        
        //Actualizamos el hash con cada uno de los bloques que usamos
        MessageDigest md = MessageDigest.getInstance("SHA-1");    //SHA-1 //MD5
        while ((i = fis.read(block)) != -1) {
            md.update(block);
        }
        
        long time1 = System.nanoTime();
        //Calculamos el hash con el ultimo bloque de codigo leido
        byte[] digest = md.digest();
        long time2 = System.nanoTime();
        //Comprobamos la diferencia de tiempos
        long time3 = time2 - time1;

        System.out.println(time3);
        System.out.println(bytesToHexString(digest));
        
        
        //Repetimos el proceso anterior pero con el algoritmo MD5
        MessageDigest md2 = MessageDigest.getInstance("MD5");    //SHA-1 //MD5
        while ((i = fis.read(block)) != -1) {
            md2.update(block);
        }
        long time12 = System.nanoTime();
        byte[] digest2 = md2.digest();
        long time22 = System.nanoTime();

        long time32 = time22 - time12;

        System.out.println(time32);
        System.out.println(bytesToHexString(digest2));
    }
    // Metodo par convertir bytes a Hexadecimal
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    //Metodo para convertir Hexadecimal a bytes
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
