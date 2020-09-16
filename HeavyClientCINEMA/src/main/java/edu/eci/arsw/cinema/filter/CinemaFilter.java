/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.filter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Angi
 */
@Service
public interface CinemaFilter {
    
    public List<CinemaFunction> filter(List<CinemaFunction> funcions, String p) throws CinemaException;
    
}
