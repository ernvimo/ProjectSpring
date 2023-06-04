package es.ieslavereda.projectspring.controller;

import es.ieslavereda.projectspring.repository.model.Oficio;
import es.ieslavereda.projectspring.service.OficioService;
import es.ieslavereda.projectspring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apidb")
public class OficioController {
    @Autowired
    private OficioService service;
    @GetMapping("/oficios/")
    public ResponseEntity<?> getAllOficios(){
        try {
            return new ResponseEntity<>(service.getAllOficios(), HttpStatus.OK);
        } catch(SQLException e){
            Map<String,Object> response = new HashMap<>();
            response.put("code",e.getErrorCode());
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/oficios/img")
    public boolean getImg(@RequestBody Oficio oficio) throws SQLException {
        return true;
    }
}
