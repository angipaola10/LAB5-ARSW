/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Angi
 */
@Service("GenderFilter")
public class GenderFilter implements CinemaFilter{
    
    @Override
    public List<CinemaFunction> filter(List<CinemaFunction> functions, String p) throws CinemaException{
        List<CinemaFunction> result = new ArrayList<>();
        for (CinemaFunction cf: functions){
            if(cf.getMovie().getGenre().equals(p)){
                result.add(cf);
            }
        }
        if(result.size() == 0){
            throw new CinemaException("No existen funciones con el género ingresado");
        }
        return result;
    }
    
}
