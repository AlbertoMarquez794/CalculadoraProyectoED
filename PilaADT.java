/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editorf.
 */
package calculadora;

/**
 * La interface que contiene los métodos y funciones que debe tener una PilaADT.
 * @author Danya, Brayan, Luis, Santiago.
 * @version 1.0
 * @see PilaArre.java
 */
public interface PilaADT <T>{
    
 /**
 * Método para agregar un elemento a la pila.
 * @param dato para agregar a la pila. 
 */
    public void push (T dato);
/**
 * Método para eliminar el último elemento agregado la pila.
 * @return dato que es quitado de la pila
 */
    public T pop();
/**
 * Método para conocer si la pila está vacia.
 * @return <ul>
 *         <li> true: Sí la pila está vacía. </li>
 *         <li> false: Sí la pila no está vacía. </li>
 *         </ul>
 */
    public boolean isEmpty();
 /**
 * Método para conocer el último elemento agregado a la pila.
 * @return T: Dato en el tope de la pila
 */
    public T peek();
}