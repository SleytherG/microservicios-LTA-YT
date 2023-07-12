package com.usuario.service.controller;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/carros/{idUsuario}")
    public ResponseEntity<List<Carro>> listarCarros(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarros(idUsuario);
        return ResponseEntity.ok(carros);
    }

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
        if ( usuario == null ){
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.obtenerCarros(idUsuario);
        return ResponseEntity.ok(carros);
    }

    @PostMapping("/carro/{idUsuario}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable Integer idUsuario, @RequestBody Carro carro) {
        Carro nuevoCarro = usuarioService.saveCarro(idUsuario, carro);
        return ResponseEntity.ok(nuevoCarro);
    }


    @GetMapping("/moto/usuario/{idUsuario}")
    public ResponseEntity<List<Moto>> obtenerMotos(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if ( usuario == null ){
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.obtenerMotos(idUsuario);
        return ResponseEntity.ok(motos);
    }

    @PostMapping("/moto/{idUsuario}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable Integer idUsuario, @RequestBody Moto moto) {
        Moto nuevaMoto = usuarioService.saveMoto(idUsuario, moto);
        return ResponseEntity.ok(nuevaMoto);
    }

    @GetMapping("/obtenerVehiculos/{idUsuario}")
    public ResponseEntity<Map<String, Object>> obtenerVehiculosPorUsuario(@PathVariable Integer idUsuario) {
        Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(idUsuario);
        return ResponseEntity.ok(resultado);
    }

}
