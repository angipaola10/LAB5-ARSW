/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.CinemaModelException;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service("InMemoryCinemaPersistence")
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new ConcurrentHashMap<String,Cinema>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new CopyOnWriteArrayList<CinemaFunction>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        functions.add(funct1); functions.add(funct2);
        Cinema c = new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
        
        functionDate = "2020-02-09 18:51";
        functions= new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("Siempre a tu lado","Drama"),functionDate);
        funct2 = new CinemaFunction(new Movie("Avatar","Science fiction"),functionDate);
        functions.add(funct1); functions.add(funct2);
        c = new Cinema("CineMark",functions);
        cinemas.put("CineMark", c);
        
        functionDate = "2020-02-10 18:55";
        functions= new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("Rapidos y furiosos 7","Action"),functionDate);
        funct2 = new CinemaFunction(new Movie("Animales fantásticos","Science fiction"),functionDate);
        functions.add(funct1); functions.add(funct2);
        c = new Cinema("Cineland",functions);
        cinemas.put("Cineland", c);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaPersistenceException {
        try{ 
            Cinema c = getCinema(cinema);
            CinemaFunction cf = c.getFunctionByMovieAndDate(movieName, date);
            cf.buyTicket(row, col);
        }catch(CinemaModelException e){
            throw new CinemaPersistenceException(e.getMessage());
        }
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException {
        try {
            Cinema c;
            c = getCinema(cinema);
            return c.getFunctionsByDate(date);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage());
        }
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        if (cinemas.containsKey(name)){
            return cinemas.get(name);
        }
        throw new CinemaPersistenceException("No exists a cinema with name: "+name);
    }
    
    @Override
    public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
        Set<Cinema> allCinemas = new HashSet(cinemas.values());
        if (allCinemas.size() == 0){
            throw new CinemaPersistenceException("Cinemas not found");
        }
        return allCinemas;
    }
    
    @Override
    public CinemaFunction getFunctionbyCinemaDateAndMovie(String cinema, String date, String movie) throws CinemaPersistenceException {
        try {
            Cinema c;
            c = getCinema(cinema);
            return c.getFunctionByMovieAndDate(movie, date);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage());
        }
    }
    
    @Override
     public void addFunction(String cinema, CinemaFunction cf) throws CinemaPersistenceException{
        try {
            getCinema(cinema).addFunction(cf);
        } catch (CinemaModelException e) {
            throw new CinemaPersistenceException(e.getMessage());
        }
     }
     
     @Override
     public void setFunction(String cinema, CinemaFunction cf) throws CinemaPersistenceException{
        getCinema(cinema).setFunction(cinema, cf);
     }

}
