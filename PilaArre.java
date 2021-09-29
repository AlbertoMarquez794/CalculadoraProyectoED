/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editorff.
 */
package calculadora;

/**
 * Clase PilaArre implementa la interface PilaADT para establecer la funcionalidad de una pila.
 * @author Danya, Brayan, Luis, Santiago.
 * @version 1.0
 */

public class PilaArre<T> implements PilaADT<T>{

    private T datos [];
    private int tope;
    private final int MAX = 10;
 
/**
 * Constructor vacio para la clase PilaArre, crea un nuevo arreglo de MAX elementos y establece el tope en -1.
 */
    public PilaArre (){
       datos = (T[]) new Object[MAX];
       tope = -1;
    }
    
/**
 * Constructor vacio para la clase PilaArre, crea un nuevo arreglo de tamano elementos y establece el tope en -1.
 * @param tamano: el tamaño que tendrá la pila.
 */
    public PilaArre (int tamano){
        datos = (T[]) new Object[tamano];
        this.tope = -1; //Palabra Vacia
    }

/**
 * Método utilizado para insertar un elemento en la pila. 
 * @see aumentaPila
 * @param nuevo: el elemento que se agregará a la pila.
 */
    public void push(T nuevo) { 
        
        if (tope+1 == datos.length){
            aumentaPila();
        }
        tope++;
        datos[tope] = nuevo;
    }
    
/**
 * Método utilizado para aumentar el tamaño de la pila.
 */
    private void aumentaPila(){
       T [] masGrande = (T[]) new Object[datos.length*2];
       
       for (int i=0; i <=tope; i++){
           masGrande[i] = datos[i];
       }
       datos = masGrande;
    }

 /**
 * Función utilizada para conocer si una pila está vacia.
 * @return <ul>
 *         <li> True: Sí la pila está vacia. </li>
 *         <li> False: No está vacia la pila. </li>
 *         </ul> 
 */ 
    @Override
    public boolean isEmpty() { //¿Está vacía? 
        return tope == -1;
    }
    
 /**
 * Función utilizada para ver el último elemento agregado a la pila.
 * @return <ul>
 *         <li> T: Regresa el valor del tope </li>
 *         <li> null: No hay elementos de la pila. </li>
 *         </ul> 
 */
    public T peek(){ //Version A (¿Que hay en el tope de la pila?
        T resp = null; 
        
        if (!isEmpty()){
            resp = datos[tope];
        }
        
        return resp;
    }

 /**
 * Método utilizado para retirar el último elemento agregado a la pila.
 * @return <ul>
 *         <li> ExcepcionColeccionVacia: Sí la pila está vacía. 
 *         <li> T: Sí el elemento lo quita en la pila</li>
 *         </ul> 
 */
    public T pop(){
        T dato;
        
        if (isEmpty()){
            throw new ExcepcionColeccionVacia ("Esta vacía la pila!!!!");
        }
        else {
           dato = datos[tope];
           datos[tope] = null;
           tope--;  
           return dato;
        }
    }
      
}