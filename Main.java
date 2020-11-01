
package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Main {
    public static List<String> Symbols = new ArrayList<>();
    public static List<Integer> Pointers = new ArrayList<Integer>();
    public static String input = "script marian : { foo = 24 ; foo = foo + ( bar / 24 ) } @";
    public static String buffer = "";
    public static List<String> input_sep = Arrays.asList(input.split(""));
    public static List<String> Tokens =new ArrayList<>();
    public static List<String> alf = Arrays.asList( "a", "b", "c","d", "e", "f","g", "h", "i","j", "k", "l","m", "n", "o","p", "q", "r","s","t", "u", "v","w", "x", "y","z");
    public static  Iterator input2 = input_sep.iterator();
    public static String Ch;
    public static List<String> digits = Arrays.asList("1","2","3","4","5","6","7","8","9","0");
    public static List<String> oper = Arrays.asList("=", "+", "-",  ";",     ">",  "<",  "==",   ":",     "*",   "{",   "}",   "(",    ")",    "/", "};");
    public static List<String> arr = Arrays.asList("script","if","else","while","BEGIN", "END", "=", "+", "-",    ";",     ">",  "<",  "==",   ":",     "*",   "{",   "}",   "(",    ")",    "/");
    public static List<String> codes = Arrays.asList("100","101", "102", "103", "104",  "105", "201","202","203", "204", "205", "206", "207",  "208", "209", "210"," 211", "212", "213",  "214");
    public static Iterator Toke;
    public static Integer Token1;
    public static Integer Ok =0;



    public static int Symb (String Symbol,String Token){


        //String startMessage;
        // Initialization of that same String variable
        //startMessage = "Welcome!";
        // Prints the value of startMessage

        //System.out.println(startMessage);
        //Scanner userInput = new Scanner(System.in); // scanner is cout in c++

        // Prints "Enter name: "
           /* System.out.println("Name: ");
            name = userInput.nextLine();

            System.out.println("Token: ");
            token = userInput.nextLine();
*/

        //here it finishes with cout
        int index1 = Symbols.indexOf(Symbol);

        if(Symbols.contains(Symbol)) {
            //System.out.println("item exists in position: "+index1);
            Pointers.add(Symbols.indexOf(Symbol));
            return index1;


        }
        else{
            Symbols.add(Symbol);
            Tokens.add(Token);
            Pointers.add(Symbols.indexOf(Symbol));
            //System.out.println("item added");
            //System.out.println(Name);
            int index2 = Symbols.indexOf(Symbol);
            //System.out.println("item is in position: "+ index2);
            return index2;

        }

    }

    public static int Recognise_Identifier() {
        String buffer = "";
        while(alf.contains(Ch) && input2.hasNext() || digits.contains(Ch)) {
            buffer += Ch;

            Ch = (String) input2.next();
            if ( !input2.hasNext()){
                buffer += Ch;

            }
        }
        if(buffer!="") {
            return Symb(buffer,"1");
        }
        return 0;
    }

    public static int Recognize_Number() {
        String buffer = "";
        while(digits.contains(Ch) && input2.hasNext()) {
            buffer += Ch;

            Ch = (String) input2.next();
            if (!input2.hasNext()){
                buffer += Ch;

            }
        }
        if(buffer!="") {
            return Symb(buffer,"2");
        }
        return 0;

    }

    public static int Recognize_Operator() {
        String buffer = "";
        while(oper.contains(Ch) && input2.hasNext()) {
            buffer += Ch;

            Ch = (String) input2.next();
            if (!input2.hasNext()){
                buffer += Ch;

            }

        }
        if(buffer!="") {
                return Symb(buffer,"3");
        }
        return 0;
    }

    public static void Lex() {
        while(input2.hasNext()) {

            Ch = (String) input2.next();

            if(alf.contains(Ch)) {
                Recognise_Identifier();
            }

            if(digits.contains(Ch)) {
                Recognize_Number();
            }

            if(oper.contains(Ch)) {
                Recognize_Operator();
            }



        }
    }

    public static void error(Integer code){
        Ok ++;
        switch (code){
            case 100:
            case 208:
                System.out.println("all scripts should be declared as 'SCRIPT example :'");
                break;
            case 210:
                System.out.println("'{' expected after starting a script");
                break;
            case 204:
                System.out.println("';' expected after statements");
                break;
            case 201:
                System.out.println("'=' is missing from assignment!");
                break;
            case 212:
            case 213 :
                System.out.println("the if and while statements should be encapsulated in parenthesis; ex: if(a>b), while(a>b)");
                break;
            case 77 :
                System.out.println("the expression after while should always be closed with '}' if started with '{'; ex: while(a>b) {foo=bar;}");
                break;
            case 206 :
                System.out.println("boolean should be either <, >, ==");
                break;
            case 66:
                System.out.println("the expression started with '(' should always be closed with ')'. ex: foo = 3 * (4 + 5) ;");
                break;
            case 55:
                System.out.println("bad factor!");
                break;
            case 12:
                System.out.println("error at: '" +  Token1 + "'");
                break;

        }

    }


    public static Integer Get_Token() {
       Integer res = (Integer) Toke.next();

        if (!Toke.hasNext()){
            System.out.println("the list of tokens is out of bounds");
        }
        System.out.println("The token is: " + Symbols.get(res));
        return Integer.valueOf(Tokens.get(res));


    }

    public static void Z(){
        head();
        if(Token1 != 210) {
            error(210);
        }
        Token1= Get_Token();
        block();
        if(Token1 != 211){
           error(211);
        }
        Token1 = Get_Token();
       /* if(Token1 != 204)
        {
            error(204);
        }*/
        Token1= Get_Token();
    }

    public static void head(){
        if(Token1 != 100){
            error(100);
        }
        Token1= Get_Token();
        if(Token1 != 1 ){
            error(12);
        }
        Token1= Get_Token();
        if(Token1 != 208){
            error(208);
        }
        Token1 = Get_Token();
    }


    public static void block(){
        stm();
        while (Token1 == 204 ){
            Token1= Get_Token();
            stm();
        }
    }

    public static void stm() {
        if (Token1 == 1) {
            Token1 = Get_Token();
            if (Token1 != 201) {
                error(201);
            }
            Token1 = Get_Token();
            expr();
        }
        else if(Token1 == 101) {
            Token1 = Get_Token();
            if(Token1 != 212) {
                error(212);
            }
            Token1 = Get_Token();
            boolex();

             if(Token1 != 213) {
            error(213);
                }
            Token1 = Get_Token();
             stm();
        }
        if(Token1 == 102) {
            Token1=Get_Token();
            stm();
        }
        else if(Token1 == 103) {
            Token1 = Get_Token();
            if(Token1 != 212){
                error(212);
            }
            Token1 = Get_Token();
            boolex();

            if(Token1 != 213){
                error(213);
                Token1 = Get_Token();
                stm();
            }
        }
        else if(Token1 == 210)
        {
            Token1 = Get_Token();
            block();
            if(Token1 != 211){
               error(77);
            }
            Token1 = Get_Token();
        }
        //else error(12);
    }

    public static void boolex(){

        expr();
                if(Token1 != 205 || Token1 != 206 || Token1 != 207){
                    error(206);
                }
           Token1 = Get_Token();
                expr();
    }

    public static void expr(){
        term();
        while (Token1 == 202 || Token1 == 203)
        {
            Token1 = Get_Token();
            term();
        }
    }

    public static void term(){
        factor();
        while (Token1 == 209 || Token1== 214){
            Token1 = Get_Token();
            factor();
        }
    }

    public static void factor(){
        if(Token1 == 1){
            Token1 = Get_Token();
        }
        else if(Token1 == 2){
            Token1  = Get_Token();
        }
        else if(Token1 == 212 ){
            Token1 = Get_Token();
            expr();

        if(Token1 != 213){
                error(66);
            }
            Token1 = Get_Token();}
            else { error(55);}

    }

    public static void main(String[] args) {
        Symbols.addAll(arr);
        Tokens.addAll(codes);
        Lex();
        int size = Symbols.size();
        System.out.println(Symbols);
        System.out.println(Pointers);
        System.out.println(Tokens);
        Toke = Pointers.iterator();
        Token1 =Get_Token();
        Z();
        if (Ok == 0) {
        System.out.println("(no errors)");}
         else System.out.println("(the program found " + Ok + " errors)");

    }


}