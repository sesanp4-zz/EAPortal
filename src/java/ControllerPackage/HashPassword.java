/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author omoniyikolade
 */
public class HashPassword {
    
    public  String getHashCodeFromString(String str) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(str.getBytes());
    byte byteData[] = md.digest();

    //convert the byte to hex format method 1
    StringBuffer hashCodeBuffer = new StringBuffer();
    for (int i = 0; i < byteData.length; i++) {
        hashCodeBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
    return hashCodeBuffer.toString();
}
    
}
