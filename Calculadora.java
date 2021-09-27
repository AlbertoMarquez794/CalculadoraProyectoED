/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editorddd.
 */

package calculadora;

/**
 * Clase del objeto Calculadora usado para resolver la expresión insertada en la GIU.
 * @authors Danya, Brayan, Luis, Santiago.
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
    
    public boolean esOperador(char a){
        boolean resp= true;
        switch(a){
            case '+':
            case '-':
            case '/':
            case '*':
            case '^':
            case '(':
            case ')':
            break;
            default:
                resp= false;
            break;
        }
        return resp;
    }
    
    public boolean esNumero (char a){
        boolean resp= true;
        switch(a){
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
 * Revisa si la sintaxis de la expresión es correcta
 * @return True: sí la expresión está bien escrita, es decir, números y operadores están en la forma correcta.
 * False: Si la sintaxis tiene un error. Por ejemplo, caso donde hay hay más de 2 operadores juntos. Por ejemplo, 8*9++. También está el caso donde no haya equivalencia de los parentesís. 
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

    public boolean jerarquiaOperandos(char operando, char tope){
        boolean resp=true;
        switch(operando){
            case '+':
                if(tope == '+')
                    resp=false;
            break;
            
            case '*':
                if(tope != '^')
                    resp = false;
            break;
            
            case '/':
                if(tope != '^')
                    resp = false;
            break;
            
            case '^':
                 resp=false;
            break;  
            
            default:
                resp=false;
            break;
        }
        
        return resp;
    }
       
  /**
 * Esta función convierte en expresión post-fija si el sintaxis es correcto.
 * @see jerarquiaOperaciones
 * @return <pre>
 * Si la expresión tiene un error de Sintaxis la función regresará "E R R O R S I N T A X I S".
 * Si la expresión no tiene error de sintaxis, regresará una Pila con la expresión Post-Fija.
 *  </pre>
 */
    
    public String expresionCalculadora() {
        
        PilaADT<Character> aux = new PilaArre();
        StringBuilder cadPostFija = new StringBuilder();
        int n,i;
        String operaciones;
        char a, ultimo;
        
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
                                    if(i>0 && (esNumero(expresion.charAt(i-1)) || expresion.charAt(i-1)==')' ) ){
                                        while(!aux.isEmpty()&& aux.peek()!='('&& jerarquiaOperandos('+',aux.peek()))
                                            cadPostFija.append(aux.pop());
                                        aux.push('+');
                                    }
                                    cadPostFija.append('-');
                                    break;
                            }   
                            break;
                        default:
                            while(!aux.isEmpty() && aux.peek()!='('&& jerarquiaOperandos(a,aux.peek()))
                                    cadPostFija.append(aux.pop());
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
 * Realiza las operaciónes 
 * @param pila: Una Pila conteniendo una expresión Post-Fija con el sintaxis correcto.
 * @see revisionSintaxis.
 * @return <pre> 
 * Si existe algún error regresa "E R R O R"
 * Si no existe algún error, regresa un String con el resultado de la operación.
 * </pre>
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
                    numeros.push( Double.parseDouble( posT.substring(j,i) ) );
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
        Calculadora cal = new Calculadora ("(8+9)");
             
        
        System.out.println (cal.resuelveExpresion());
        
    }
}