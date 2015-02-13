package lexico.sintactico;

import java_cup.runtime.*;
import java.io.Reader;
import java.util.HashMap;
      
%% 
%class AnalizadorLexico
%line
%column
%cup
%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
    
    public static HashMap<String, String> errores = new HashMap<>();
%}
   
Salto = \r|\n|\r\n

Espacio = {Salto} | [ \t\f]
   
Entero = 0 | [1-9][0-9]*

Cadena = [a-z][a-z]* | [A-Z][A-Z]* | [a-z][A-Z]* | [A-Z][a-z]*

%%
   
<YYINITIAL> {
   
    
    ";"        { 
    	         return symbol(sym.SEMI); 
    	       }
   
    "+"        {  
                 System.out.print(" SUMA ");
                 return symbol(sym.OP_SUMA); 
               }
   
    "-"        {  
                 System.out.print(" RESTA ");
                 return symbol(sym.OP_RESTA); 
               }

    "*"        {  
                 System.out.print(" MULTIPLICACION ");
                 return symbol(sym.OP_MULT); 
               }

    "("        {  
                 System.out.print(" PARENTESIS ( ");
                 return symbol(sym.PARENTESIS_IZQUIERDO); 
               }
              
     ")"       {  
                 System.out.print(" PARENTESIS ) ");
                 return symbol(sym.PARENTESIS_DERECHO); 
               }
   
    {Entero}   {
    	         System.out.print("ENTERO <"+yytext()+">"); 
                 return symbol(sym.ENTERO, new Integer(yytext())); 
               }

    {Cadena}   {   
	             System.out.print("CADENA <"+yytext()); 
                 System.out.print("("); 
                 for (int i = 0; i < yytext().length()-1; i++) {
                   System.out.print(yytext().codePointAt(i)+":");
                 }
                 System.out.print(yytext().codePointAt(yytext().length()-1));
                 System.out.print(") >"); 	
                 return symbol(sym.CADENA, new StringBuffer(yytext())); 
               }

    {Espacio}  {
    	 
               } 
}

[^] 
{ 
  String mensaje = "";
  mensaje += ("\n************************************");
  mensaje += ("\n****Caracter invalido ==> "+yytext()+" <== ****");
  mensaje += ("\nencontrado en la linea ");
  mensaje += ((yyline + 1) + " y columna "  + (yycolumn + 1));
  mensaje += ("\n************************************\n");
  String keyString = (yyline + 1) + (yycolumn + 1) + "";
  errores.put(keyString, yytext());
  System.err.println(mensaje);
}



















