/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author cristian
 */
public class Cinema {
    private String name;
    private List<CinemaFunction> functions; 
    
    
    public Cinema(){}
    
    public Cinema(String name,List<CinemaFunction> functions){
        this.name=name;
        this.functions=functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CinemaFunction> getFunctions() {
        return this.functions;
    }
    
    public void addFunction(CinemaFunction cf) throws CinemaModelException{
        for(CinemaFunction c: functions){
            if (c.equals(cf)){
                throw new CinemaModelException("This function already exists");
            }
        }
        functions.add(cf);
    }

    public void setSchedule(List<CinemaFunction> functions) {
        this.functions = functions;
    }
    
    public CinemaFunction getFunctionByMovieAndDate(String movie, String date) throws CinemaModelException{
        List<CinemaFunction> cf = getFunctionsByDate(date);
        for (CinemaFunction f: cf){
            if (f.getMovie().getName().equals(movie)){
                return f;
            }
        }
        throw new CinemaModelException("No exists a function with that movie name");
    }
    
    public List<CinemaFunction> getFunctionsByDate(String date) throws CinemaModelException {
        List<CinemaFunction> cinemaFunctions = new ArrayList<>();
        for(CinemaFunction cf: functions){
            if(cf.getDate().split(" ")[0].equals(date.split(" ")[0])){
                if (date.split(" ").length == 2 && cf.getDate().split(" ")[1].equals(date.split(" ")[1])){
                    cinemaFunctions.add(cf);
                }else if (date.split(" ").length == 1){
                    cinemaFunctions.add(cf);
                }
            }
        }
        if (cinemaFunctions.size() == 0){
            throw new CinemaModelException("No exists a function with that date");
        }
        return cinemaFunctions;

    }
    
    public String toString(){
        String cinema =  "Cinema: "+name+"\n  Funciones:";
        for(CinemaFunction f: functions){
            cinema+="\n    "+f.toString();
        }
        return cinema;
    }
    
    public void setFunction(String cinema, CinemaFunction cf){
        int i = 0;
        for(i = 0; i < functions.size(); i++){
            if(functions.get(i).equals(cf)){
                synchronized(functions.get(i)){
                    functions.remove(functions.get(i)); break;
                }
            }
        }
        functions.add(i, cf);
    }
    
}
