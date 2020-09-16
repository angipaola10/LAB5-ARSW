/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {
    
    @Autowired
    CinemaServices cs;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas() throws ResourceNotFoundException {
        try{
            return new ResponseEntity<>(cs.getAllCinemas(),HttpStatus.ACCEPTED);
        }catch(CinemaException e){
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
	    if (e.getMessage().equals("Cinemas not found")){
               throw new ResourceNotFoundException(e.getMessage());
            } return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    } 
    
    @RequestMapping(value="/{name}", method= RequestMethod.GET)
    public ResponseEntity<?> getCinemaByName(@PathVariable("name") String name) throws ResourceNotFoundException{
    	try {
            return new ResponseEntity<>(cs.getCinemaByName(name),HttpStatus.ACCEPTED);
        }catch (CinemaException e) {
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
	    if (e.getMessage().equals("Cinemas not found")){
               throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a cinema with name: "+name)){
                throw new ResourceNotFoundException(e.getMessage());
            } return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    }
    
    @RequestMapping(value="/{name}/{date}", method= RequestMethod.GET)
    public ResponseEntity<?> getFunctionsByCinemaAndDate(@PathVariable("name") String name, @PathVariable("date") String date) throws ResourceNotFoundException{
    	try {
            return new ResponseEntity<>(cs.getFunctionsbyCinemaAndDate(name, date),HttpStatus.ACCEPTED);
        }catch (CinemaException e) {
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
	    if (e.getMessage().equals("Cinemas not found")){
               throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a cinema with name: "+name)){
                throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a function with that date")){
                throw new ResourceNotFoundException(e.getMessage());
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value="/{name}/{date}/{movieName}", method= RequestMethod.GET)
    public ResponseEntity<?> getFunctionByCinemaDateAndMovie(@PathVariable("name") String name, @PathVariable("date") String date, @PathVariable("movieName") String movieName) throws ResourceNotFoundException{
    	try {
            return new ResponseEntity<>(cs.getFunctionByCinemaDateAndMovie(name, date, movieName),HttpStatus.ACCEPTED);
        }catch (CinemaException e) {
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
	    if (e.getMessage().equals("Cinemas not found")){
               throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a cinema with name: "+name)){
                throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a function with that date")){
                throw new ResourceNotFoundException(e.getMessage());
            }else if (e.getMessage().equals("No exists a function with that movie name")){
                throw new ResourceNotFoundException(e.getMessage());
            }return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value= "/{name}", method = RequestMethod.POST)	
    public ResponseEntity<?> postCinemaFunction(@PathVariable("name") String name, @RequestBody CinemaFunction cf) throws ResourceNotFoundException{
        try {
            cs.addFunction(name, cf);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException e) {
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
            if (e.getMessage().equals("No exists a cinema with name: "+name)) 
                throw new ResourceNotFoundException(e.getMessage());
            else if (e.getMessage().equals("This function already exists")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            } return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    }
    
    @RequestMapping(value= "/{name}", method = RequestMethod.PUT)	
    public ResponseEntity<?> putCinemaFunction(@PathVariable("name") String name, @RequestBody CinemaFunction cf) throws ResourceNotFoundException{
        try {
            cs.setFunction(name, cf);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CinemaException e) {
            Logger.getLogger(RestController.class.getName()).log(Level.SEVERE, null, e);
            if (e.getMessage().equals("No exists a cinema with name: "+name)) {
                throw new ResourceNotFoundException(e.getMessage());
            } return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);         
        }        
    }
 
}
