package com.usuario.service.servicio;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Usuario> getAll() {
        return usuarioRepositorio.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
        return nuevoUsuario;
    }


    public List<Carro> getCarros(int idUsuario) {
//        List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + idUsuario, List.class);
        List<Carro> carros = restTemplate.getForObject("http://carro-service/carro/usuario/" + idUsuario, List.class);
        return carros;
    }

    public List<Moto> getMotos(int idUsuario) {
//        List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + idUsuario, List.class);
        List<Moto> motos = restTemplate.getForObject("http://moto-service/moto/usuario/" + idUsuario, List.class);
        return motos;
    }

    public Carro saveCarro(int idUsuario, Carro carro) {
        carro.setIdUsuario(idUsuario);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    public List<Carro> obtenerCarros(int idUsuario) {
        return carroFeignClient.getCarros(idUsuario);
    }

    public Moto saveMoto(int idUsuario, Moto moto) {
        moto.setIdUsuario(idUsuario);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public List<Moto> obtenerMotos(int idUsuario) {
        return motoFeignClient.getMotos(idUsuario);
    }

    public Map<String, Object> getUsuarioAndVehiculos(int idUsuario) {
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);

        if ( usuario == null) {
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("Usuario", usuario);
        List<Carro> carros = carroFeignClient.getCarros(idUsuario);
        if ( carros.isEmpty()) {
            resultado.put("Carros", "El usuario no tiene carros");
        } else {
            resultado.put("Carros", carros);
        }
        List<Moto> motos = motoFeignClient.getMotos(idUsuario);
        if ( motos.isEmpty()) {
            resultado.put("Motos", "El usuario no tiene motos");
        } else {
            resultado.put("Motos", motos);
        }
        return resultado;
    }
}
