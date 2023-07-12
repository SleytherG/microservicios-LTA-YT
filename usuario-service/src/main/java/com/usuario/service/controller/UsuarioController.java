package com.usuario.service.controller;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
    @GetMapping("/carros/{idUsuario}")
    public ResponseEntity<List<Carro>> listarCarros(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarros(idUsuario);
        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{idUsuario}")
    public ResponseEntity<List<Moto>> listarMotos(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.getMotos(idUsuario);
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/carro/usuario/{idUsuario}")
    public ResponseEntity<List<Carro>> obtenerCarros(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.obtenerCarros(idUsuario);
        return ResponseEntity.ok(carros);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
    @PostMapping("/carro/{idUsuario}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable Integer idUsuario, @RequestBody Carro carro) {
        Carro nuevoCarro = usuarioService.saveCarro(idUsuario, carro);
        return ResponseEntity.ok(nuevoCarro);
    }


    @GetMapping("/moto/usuario/{idUsuario}")
    public ResponseEntity<List<Moto>> obtenerMotos(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.obtenerMotos(idUsuario);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{idUsuario}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable Integer idUsuario, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioService.saveMoto(idUsuario, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/obtenerVehiculos/{idUsuario}")
    public ResponseEntity<Map<String, Object>> obtenerVehiculosPorUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(idUsuario);
        return ResponseEntity.ok(resultado);
    }

    private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable Integer idUsuario, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + idUsuario + "tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Carro> fallBackSaveCarro(@PathVariable Integer idUsuario, @RequestBody Carro Carro, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + idUsuario + " no tiene dinero para los carros", HttpStatus.OK);
    }

    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable Integer idUsuario, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + idUsuario + " no tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable Integer idUsuario, @RequestBody Moto moto, RuntimeException exception) {
        return new ResponseEntity("El usuario: " + idUsuario + " no tiene dinero para las motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable Integer idUsuario, RuntimeException exception) {
        return new ResponseEntity("El usuario : " + idUsuario + " tiene los vehiculos en el taller", HttpStatus.OK);
    }

}
