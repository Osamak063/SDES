/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdes;

import java.util.Scanner;

/**
 *
 * @author Osama Khalid
 */
public class SDES {
    public static int[] p10={3,5,2,7,4,10,1,9,8,6};
    public static int[] ip={2,6,3,1,4,8,5,7};
    public static int[] ep={4,1,2,3,2,3,4,1};
    public static int[] p8={6,3,7,4,8,5,10,9};
    public static int[] p4={2,4,3,1};
    public static int[] ip_inverse={4,1,3,5,7,2,8,6};
    public static String[][] S0={{"01","00","11","10"},
                                 {"11","10","01","00"},
                                 {"00","10","01","11"},
                                 {"11","01","11","10"}};
    public static String[][] S1={{"00","01","10","11"},
                                 {"10","00","01","11"},
                                 {"11","00","01","00"},
                                 {"10","01","00","11"}};
    /**
     * @param args the command line arguments
     */
    public static String applyTable(String data,String tableType){
        char[] dataArray=data.toCharArray();
        String returnValue;
        if(tableType.equals("p10")){
            char[] returnArray= new char[p10.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[p10[i]-1];
            }
            return new String(returnArray); 
        }
        else if(tableType.equals("p8")){
            char[] returnArray= new char[p8.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[p8[i]-1];
            }
            return new String(returnArray); 
        }
        else if(tableType.equals("p4")){
            char[] returnArray= new char[p4.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[p4[i]-1];
            }
            return new String(returnArray); 
        }
        else if(tableType.equals("ip")){
            char[] returnArray= new char[ip.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[ip[i]-1];
            }
            return new String(returnArray); 
        }
        else if(tableType.equals("ep")){
            char[] returnArray= new char[ep.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[ep[i]-1];
            }
            return new String(returnArray); 
        }
        else if(tableType.equals("ip_inverse")){
            char[] returnArray= new char[ip_inverse.length];
            for(int i=0;i<returnArray.length;i++){
                returnArray[i]=dataArray[ip_inverse[i]-1];
            }
            return new String(returnArray); 
        }
        return "empty";
    }
    public static String apply_STable(String data,String tableType){
        char[] dataArray=data.toCharArray();
        String row,col;
        if(tableType.equals("S0")){
            row=Character.toString(dataArray[0])+Character.toString(dataArray[3]);
            col=Character.toString(dataArray[1])+Character.toString(dataArray[2]);
            return S0[binToInt(row)][binToInt(col)];
        }
        else if(tableType.equals("S1")){
            row=Character.toString(dataArray[0])+Character.toString(dataArray[3]);
            col=Character.toString(dataArray[1])+Character.toString(dataArray[2]);
            return S1[binToInt(row)][binToInt(col)];
        }
        return "0";
    }
    public static int binToInt(String data){
        if(data.equals("00")){
            return 0;
        }
        else if(data.equals("01")){
            return 1;
        }
        else if(data.equals("10")){
            return 2;
        }
        else if(data.equals("11")){
            return 3;
        }
        return 0;
    }
    public static String leftShift(String data){
        char[] dataArray=data.toCharArray();
        char [] returnArray= new char[dataArray.length];
        returnArray[returnArray.length-1]=dataArray[0];
        for(int i=0;i<dataArray.length-1;i++){
            returnArray[i]=dataArray[i+1];
        }
        return new String(returnArray);
    }
    public static String[] divider(String data){
        char[] keyP10Array;
        String leftLCS1="",rightLCS1="";
        keyP10Array=data.toCharArray();
        for(int i=0;i<keyP10Array.length;i++){
            int divider=keyP10Array.length/2;
            if(i<=divider-1){
                leftLCS1+=keyP10Array[i];
                }
            else
                rightLCS1+=keyP10Array[i];
    }
        String[] returnArray= {leftLCS1,rightLCS1};
        return returnArray;
    }
    public static String XOR(String op1,String op2){
    char[] op1Array=op1.toCharArray();
    char[] op2Array=op2.toCharArray();
    char[] returnArray=new char[op1Array.length];
    for(int i=0;i<op1Array.length;i++){
        if(op1Array[i]==op2Array[i]){
            returnArray[i]='0';
        }
        else{
            returnArray[i]='1';
        }
    }
    return new String(returnArray);
    }
    public static void main(String[] args) {
    String keyP10,leftLCS1="",rightLCS1="",leftLCS2="",rightLCS2="",k1,k2,PT_IP,left,right,
            Ep,leftSave,round1,round2,rightSave,CT,DT;
    String[] dividerReturn;
    Scanner in = new Scanner(System.in);
    String plainText,key;
    System.out.println("Enter Plain Text:");
    plainText=in.nextLine();
    System.out.println("Enter Key:");
    key=in.nextLine();
    keyP10=applyTable(key,"p10");
    dividerReturn=divider(keyP10);
    left=dividerReturn[0];
    right=dividerReturn[1];
    leftLCS1=leftShift(left);
    rightLCS1=leftShift(right);
    leftLCS2=leftShift(leftLCS1);
    leftLCS2=leftShift(leftLCS2);
    rightLCS2=leftShift(rightLCS1);
    rightLCS2=leftShift(rightLCS2);
    k1=leftLCS1+rightLCS1;
    k1=applyTable(k1,"p8");
    k2=leftLCS2+rightLCS2;
    k2=applyTable(k2,"p8");
    PT_IP=applyTable(plainText,"ip");
    dividerReturn=divider(PT_IP);
    leftSave=dividerReturn[0];
    rightSave=dividerReturn[1];
    Ep=applyTable(rightSave,"ep");
    dividerReturn=divider(XOR(Ep,k1));
    left=dividerReturn[0];
    right=dividerReturn[1];
    round1=XOR(applyTable(apply_STable(left,"S0")+apply_STable(right,"S1"),"p4"),leftSave);
    Ep=applyTable(round1,"ep");
    dividerReturn=divider(XOR(Ep,k2));
    left=dividerReturn[0];
    right=dividerReturn[1];
    round2=XOR(applyTable(apply_STable(left,"S0")+apply_STable(right,"S1"),"p4"),rightSave);
    CT=round2+round1;
    System.out.println("Cipher Text="+CT);
    dividerReturn=divider(applyTable(applyTable(CT,"ip_inverse"),"ip")); 
    leftSave=dividerReturn[0];
    rightSave=dividerReturn[1];
    Ep=applyTable(rightSave,"ep");
    dividerReturn=divider(XOR(Ep,k2));
    left=dividerReturn[0];
    right=dividerReturn[1];
    round1=XOR(applyTable(apply_STable(left,"S0")+apply_STable(right,"S1"),"p4"),leftSave);
    Ep=applyTable(round1,"ep");
    dividerReturn=divider(XOR(Ep,k1));
    left=dividerReturn[0];
    right=dividerReturn[1];
    round2=XOR(applyTable(apply_STable(left,"S0")+apply_STable(right,"S1"),"p4"),rightSave);
    DT=applyTable(round2+round1,"ip_inverse");
    System.out.println("Decrypted Text="+DT);
    }
}
