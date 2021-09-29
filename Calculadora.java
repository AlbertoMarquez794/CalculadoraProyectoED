/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editorddd.
 */

package calculadora;

/**
 * Clase del objeto Calculadora usado para resolver una expresión en un resultado número
 * @author Danya, Brayan, Luis, Santiago.
 * @version 1.0
 */

public class Calculadora {

 private String expresion;

 /**
 * Constructor vacio para la clase Calculadora.
 */
    public Calculadora() {

    }

 /**
 * Constructor para la clase Calculadora.
 * @param expresion: la expresión a calcular expresada como String.
 */
    public Calculadora(String expresion) {
        this.expresion = expresion;
    }

 /**
    * Getter para obtener la expresión
    * @return la expresión en formato String.
 */
    public String getExpresion() {
        return expresion;
    }
  
 /**
   * Conocer sí un caracter es operador
   * @param a: Es un caracter que recibe como referencia para el método. 
   * @return <ul>
   *         <li> true: Sí es operador. </li>
   *         <li> false: No es operador. </li>  
   *         </ul>   
 */  
     
    public boolean esOperador(char a){
        boolean resp;
        
        switch(a){
            case '+':
            case '-':
            case '/':
            case '*':
            case '^':
            case '(':
            case ')':
                resp = true;
            break;
            
            default:
                resp= false;
            break;
        }
        
        return resp;
    }
    
 /**
   * Conocer sí un caracter es número
   * @param n: Es un caracter que recibe un caracter. 
   * @return <ul>
   *         <li> true: Sí ee un número. </li>
   *         <li> false: No es un número. </li>  
   *         </ul>   
 */      

    public boolean esNumero (char n){
        boolean resp;
        
        switch(n){
            
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                resp = true;
            break;
            
            default:
                resp= false;
            break;
        }
        
        return resp;
    }
    
/**
 * Setter para modificar la expresion por una nueva. 
 * @param expresion: la expresión en formato String.
 */
    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
    
 /**
 * Revisa la sisntaxis de una notación postfija para poder ver sí está bien escrito, y no tenga ningún tipo de error.
 * @see esOperador
 * @return <ul>
 *         <li> true: Sí la expresión de la calculadora este bien escrita, sin ningún error. </li>
 *         <li> false: Sí la expresión tiene un error de símbología o no haya equivalencias de parentesis. </li>
 *         </ul>
 */
    public boolean revisionSintaxis() {
        boolean res;
        PilaADT<Character> parentesis = new PilaArre();
        boolean punto = false;
        int i=0, n = 0;
        char xC, ch;
        
        if (expresion == null || expresion.length()==0){
            res = false;
        }
        else {
            res = true;
            n = expresion.length();
        }
        
        while ( i < n && res){
            ch = expresion.charAt(i);
            if (i==0 && esOperador(ch) && ch != '(' && ch !='-'){ //res = false syss es "+" o "/" + "*"
                res = false;
            }
            switch(ch){
                case '(':
                    if(i+1 < n){
                        xC = expresion.charAt(i+1);

                        if( xC ==')'|| xC =='+'|| xC =='*' || xC =='/' || xC =='^') {
                            res = false;
                        }
                        else {
                            parentesis.push('(');
                        }   
                    }  
                    else{
                        res = false;
                    }
                        
                    punto = false;
                break;

                case ')':
                    if(!parentesis.isEmpty()){
                        parentesis.pop();
                        if(i+1<n){
                            xC = expresion.charAt(i+1);
                            if(esNumero(xC)){
                                res = false;
                            }
                        }
                    }
                    else{
                        res = false;
                    }
                    
                    punto = false;
                break; 

                case '+':               
                case '*':
                case '/':
                case '^':    
                    if(i+2<n && expresion.charAt(i+1)=='-' && expresion.charAt(i+2)=='('){
                        res = false;
                    }
                    if(i+1<n){
                        xC = expresion.charAt(i+1);
                        if( xC =='+' || xC =='^' || xC =='*' || xC =='/' || xC ==')'){
                            res = false;
                        }
                    }
                    else
                        res=false;

                    punto=false;
                break;
                
                case'-':
                    if(i+2 < n && expresion.charAt(i+1) == '-' && expresion.charAt(i+2)=='('){
                        res=false;
                    }
                    if(i+1<n){
                        xC = expresion.charAt(i+1);
                        
                        if(xC == '+'|| xC == '^' || xC == '*' || xC == '/' || xC == ')'){
                            res = false;
                        }
                        else{
                            if(!esNumero (xC) && xC !='('){
                                if(i>0 && xC =='-' && i+2<n){
                                    xC = expresion.charAt(i+2);
                                    if(!esNumero(xC) && xC !='.')
                                        res=false;
                                    i++;
                                }
                                else
                                    if(xC != '.')
                                        res=false;
                            }
                        }
                    }
                    else {
                        res=false;
                    }
                    punto=false;
                break;

                case '.':
                    if(punto){
                        res =false;
                    }
                    else{
                        punto=true;
                        if(i+1<n){
                            xC = expresion.charAt(i+1);
                            if(!esNumero(xC)){
                                res = false;
                            }
                        }
                        else
                            res = false;
                    }
                break;
            }
            i++;
        }
        
        if(!parentesis.isEmpty()){
            res = false;
        }
           
        
        return res;
    }  

 /**
   * Verifica la jerarquía de Operaciones de dos operados
   * @param operando recibe como parametro la '^', '*', '/', '+'
   * @param operando2 recibe como parametro la '+', '/','*', '^'
   * @return <ul>
   *         <li> true: Sí el operando2 es mayor jerarquicamente al operando. </li>
   *         <li> false: Sí el operando es menor o igual jerarquicamente al operando. </li>
   *         </ul>   
 */        
    public boolean jerarquiaOperandos(char operando, char operando2){
        boolean res = true;
        
        switch(operando){
            case '+':
                if(operando2 == '+')
                    res =false;
            break;
            
            case '*':
                if(operando2 != '^')
                    res = false;
            break;
            
            case '^':
                 res = false;
            break;  
            
            case '/':
                if(operando2 != '^')
                    res = false;
            break;
            
            
            default:
                res = false;
            break;
        }
        
        return res;
    }
       
  /**
 * Esta función convierte en expresión post-fija si el sintaxis es correcto.
 * @see jerarquiaOperandos 
 * @see esOperador
 * @see revisiónSintaxis
 * @return <ul>
 *         <li> E R R O R: Sí la expresión tiene un error de sintaxis. </li>
 *         <li> Expresión en Post-Fija: Sí la expresión no tiene un error de sisntaxis, regresará una cadena con la expresión en Post-Fija. </li>  
 *         </ul> 
 */   
    public String expresionCalculadora() {
        PilaADT<Character> aux = new PilaArre();
        StringBuilder cadPostFija = new StringBuilder();
        int n,i;
        char a, ultimo;
        String operaciones;
       
        
        if(revisionSintaxis()){
            
            i = 0;
            n = expresion.length();
            
            while(i < n){
                a = expresion.charAt(i);
                if(!esOperador(a)){
                    cadPostFija.append(a);
                }
                else {
                    if(cadPostFija.length()>0){
                        ultimo = cadPostFija.charAt(cadPostFija.length()-1);
                        if(!esOperador(ultimo) && ultimo != 'S' && ultimo !='N'){
                            cadPostFija.append("N");
                        }
                    }
                    switch(a){
                        case '(':
                            if(i>0 && esNumero(expresion.charAt(i-1)))
                                aux.push('*');
                            aux.push(a);
                            break;
                        case ')':
                            while(!aux.isEmpty() && !aux.peek().equals('('))
                                cadPostFija.append(aux.pop());
                            aux.pop();
                            if(!aux.isEmpty()&& aux.peek()=='-'){
                                cadPostFija.append(aux.pop());
                                cadPostFija.append('S');
                            }
                            if(i+1<n && expresion.charAt(i+1)=='('){
                                aux.push('*');
                            }
                              
                        break;

                        case '-':
                            switch(expresion.charAt(i+1) ){
                                case '(':
                                    aux.push(a);
                                    break;
                                case '-':
                                    while(!aux.isEmpty()&& aux.peek()!='(' && jerarquiaOperandos('+',aux.peek()))
                                        cadPostFija.append(aux.pop());
                                    aux.push('+');
                                    i++;
                                break;
                                default:
                                    if(i>0 && (esNumero(expresion.charAt(i-1)) || expresion.charAt(i-1)==')')){
                                        while(!aux.isEmpty()&& aux.peek()!='('&& jerarquiaOperandos('+',aux.peek()))
                                            cadPostFija.append(aux.pop());
                                        aux.push('+');
                                    }
                                    cadPostFija.append('-');
                                break;
                            }   
                        break;
                        default:
                            while(!aux.isEmpty() && jerarquiaOperandos(a,aux.peek()) && aux.peek()!='('){
                                cadPostFija.append(aux.pop());
                            }
                            aux.push(a);
                            if(!aux.isEmpty() && aux.peek()=='-'){
                                cadPostFija.append(aux.pop());
                                cadPostFija.append('S');
                            }
                        break;
                    }
                }
                i++;
            }

            ultimo = cadPostFija.charAt(cadPostFija.length()-1);
            if(!esOperador(ultimo)&& ultimo != 's' && ultimo != 'N'){
                cadPostFija.append("N");
            }       

            while(!aux.isEmpty()){
                cadPostFija.append(aux.pop());
            }

            operaciones = cadPostFija.toString();
        }
        else {
            operaciones = "E R R O R";
        }
        
        return operaciones;
    }
    
 /**
    * Reseuelve la expresión en PostFija 
    * @see expresionCalculadora, esOperador.
    * @return <ul> 
    *         <li> Numero: Sí se pudo resolver la expresión, y no hubo ninguna división entre 0. </li> 
    *         <li> E R R O R: Sí la expresión tiene división entre 0. </li>   
    *         </ul>
 */
    public String resuelveExpresion(){
        String posT, resultado;
        int i, j;
        int n;
        PilaADT<Double> numeros = new PilaArre();
        double x=0,y;
        boolean error=false;
        
        posT = this.expresionCalculadora();
        n = posT.length();
        
        if (!posT.equals("E R R O R")){
            i = 0;
            while(i < n && !error){
                if(posT.charAt(i) == 'S') {
                    i++;
                }
                if(!esOperador(posT.charAt(i)) || (posT.charAt(i)=='-' && i+1<n && posT.charAt(i+1)!='N')){
                    j = i;
                    while(i < n && posT.charAt(i) != 'N'){
                        i++;
                    }
                    numeros.push( Double.parseDouble( posT.substring(j,i)));
                    i++;	 
                }
                else{
                    while(i<n && esOperador(posT.charAt(i)) && !(posT.charAt(i)=='-' && i+1<n && posT.charAt(i+1)!='S')){
                        switch(posT.charAt(i)){
                            case'-':
                                x=numeros.pop();
                                if(numeros.isEmpty())
                                    x=0-x;
                                else{
                                    y=numeros.pop();
                                    x=y-x;
                                }
                                
                                break;
                            case'+':
                                y = numeros.pop();
                                x = numeros.pop();
                                x = x + y;
                            break;
                            
                            case'*':
                                y=numeros.pop();
                                x=numeros.pop();
                                x=x*y;
                            break;
                            
                            case'/':
                                y=numeros.pop();
                                x=numeros.pop();
                                if(y==0)
                                    error=true;
                                else
                                    x=x/y;
                                break;
                            case'^':
                                y=numeros.pop();
                                x=numeros.pop();
                                if(y==0 && x==0)
                                    error=true;
                                else
                                    x=Math.pow(x, y);
                                break;
                        }
                        numeros.push(x);
                        i++;
                    }
                }
            }    
            
            if(!error){
                resultado=String.valueOf(numeros.pop());
            }
            else
                resultado="E R R O R";
            
        }
        else
          resultado="E R R O R";
         
        return resultado;
    }
    
    public static void main (String [] args){
        Calculadora cal2 = new Calculadora ("(4-9)*87+69/4");
             
        
        System.out.println (cal2.revisionSintaxis());
        System.out.println (cal2.expresionCalculadora());
        System.out.println (cal2.resuelveExpresion());
        
    }
}