package com.carro.service.servicio;

import com.carro.service.entidades.Carro;
import com.carro.service.repositorio.CarroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepositorio carroRepositorio;

    public List<Carro> getAll() {
        return carroRepositorio.findAll();
    }

    public Carro getCarroById(int id) {
        return carroRepositorio.findById(id).orElse(null);
    }

    public Carro save(Carro carro) {
        Carro nuevoCarro = carroRepositorio.save(carro);
        return nuevoCarro;
    }

    public List<Carro> findByUsuarioId(int idUsuario) {
        return carroRepositorio.findByIdUsuario(idUsuario);
    }
}
