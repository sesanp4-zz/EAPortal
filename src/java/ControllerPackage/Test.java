/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;

/**
 *
 * @author centricgateway
 */
public class Test {
    public static void main(String[] args) {
        int num=500;
        for(int count=0; count<num;count++){
          int randomPIN = (int)(Math.random()*9000)+1000;
          System.out.println("Number "+count+"..."+randomPIN);
        }
        
        
    }
    
}
