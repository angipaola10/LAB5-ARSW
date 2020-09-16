/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.test;

import com.google.gson.Gson;
import edu.eci.arsw.cinema.CinemaAPIApplication;
import edu.eci.arsw.cinema.controllers.CinemaAPIController;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.CinemaModelException;
import edu.eci.arsw.cinema.model.Movie;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author cristian
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CinemaAPIApplication.class})
public class ApplicationServicesTest {
    
    private MockMvc m;
    
    @Autowired
    private CinemaAPIController c;
    
    private final Gson gson = new Gson();
    
    @Before
    public void setUp(){
        m = MockMvcBuilders.standaloneSetup(c).build();
    }

    @Test
    public void deberiaConsultarTodosLosCinemas() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }    
    
    @Test
    public void deberiaConsultarTodasFuncionesDeUnCineEspecifico() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/Cineland")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    } 
    
    @Test
    public void noDeberiaEncontrarFuncionesDeUnCineNoRegistrado() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/Cinland")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void deberiaConsultarFuncionesPorNombreDeCineYFecha() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/Cineland/2020-02-10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }
    
    @Test
    public void noDeberiaConsultarFuncionesEnUnaFechaNoRegistrada() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/Cineland/2020-02-11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void deberiaConsultarFuncionesDeUnCinePorFechaHoraYNombreDePelicula() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/CineMark/2020-02-09 18:51/Avatar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
    }
    
    @Test
    public void noDeberiaEncontrarFuncionesQueTenganUnaPeliculaNoRegistrada() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/CineMark/2020-02-09 18:51/Avatr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void noDeberiaEncontrarFuncionesQueTenganDeterminadaPeliculaEnUnaHoraNoRegistrada() throws Exception {
        m.perform(
                MockMvcRequestBuilders.get("/cinemas/CineMark/2020-02-09 18:50/Avatar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void deberiaCrearUnaFuncionEnDeterminadoCinema() throws Exception {
        m.perform(
                MockMvcRequestBuilders.post("/cinemas/Cineland")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Avengers", "Accion", "2020-02-09 16:30")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void noDeberiaCrearUnaFuncionEnUnCinemaNoExistente() throws Exception {
        m.perform(
                MockMvcRequestBuilders.post("/cinemas/cinelan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Avengers", "Accion", "2020-02-09 16:30")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    
    @Test
    public void noDeberiaCrearUnaFuncionYaExistenteEnUnCinema() throws Exception {
        m.perform(
                MockMvcRequestBuilders.post("/cinemas/CineMark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Avatar","Science fiction","2020-02-09 18:51")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
    
    @Test
    public void noDeberiaCrearUnaFuncionNoValida() throws Exception {
        m.perform(
                MockMvcRequestBuilders.post("/cinemas/CineMark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Avatar","Science fiction","2020-02-09 18:51").getDate()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
    private CinemaFunction crearFuncion(String nombre, String genero, String date) {
        Movie movie = new Movie(nombre, genero);
        return new CinemaFunction(movie, date);
    }
    
    @Test
    public void deberiaActualizarDeterminadaFuncion() throws Exception {
        m.perform(
                MockMvcRequestBuilders.put("/cinemas/Cineland")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Avengers", "Accion", "2020-02-09 16:30")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void deberiaCrearFuncionAlActualizarSiNoExiste() throws Exception {
        m.perform(
                MockMvcRequestBuilders.put("/cinemas/Cineland")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(crearFuncion("Ted", "Fantasía", "2020-02-09 16:30")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
