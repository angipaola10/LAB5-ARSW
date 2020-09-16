/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

/**
 *
 * @author Angi
 */
public class CinemaModelException extends Exception{

    public CinemaModelException(String message) {
        super(message);
    }

    public CinemaModelException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
