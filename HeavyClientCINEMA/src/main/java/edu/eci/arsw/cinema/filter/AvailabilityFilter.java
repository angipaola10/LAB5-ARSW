/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Angi
 */
@Service("AvailabilityFilter")
public class AvailabilityFilter implements CinemaFilter{
    
    @Override
    public List<CinemaFunction> filter(List<CinemaFunction> functions, String p)throws CinemaException{
        int s = 0;
        try{
            s = Integer.parseInt(p);
        }catch(NumberFormatException e){
            throw new CinemaException("El número de sillas no es válido");
        } 
        List<CinemaFunction> result = new ArrayList<>();
        int disponibles = 0;
        synchronized(functions){
            for(CinemaFunction cf: functions){
                for(List<Boolean> f: cf.getSeats()){  
                    for(Boolean cc: f){
                        if(cc){
                            disponibles++;
                        }
                    }
                }
                if(disponibles >= s){
                    result.add(cf);
                }
            }
            if(result.size() == 0){
                throw new CinemaException("Ninguna función tiene esa cantidad de sillas");
            }
            return result; 
        }
    }
}
