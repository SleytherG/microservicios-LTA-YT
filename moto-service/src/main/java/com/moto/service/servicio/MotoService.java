package com.moto.service.servicio;

import com.moto.service.entidades.Moto;
import com.moto.service.repositorio.MotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepositorio motoRepositorio;

    public List<Moto> getAll() {
        return motoRepositorio.findAll();
    }

    public Moto getMotoById(int id) {
        return motoRepositorio.findById(id).orElse(null);
    }

    public Moto save(Moto moto) {
        Moto nuevaMoto = motoRepositorio.save(moto);
        return nuevaMoto;
    }

    public List<Moto> findByUsuarioId(int idUsuario) {
        return motoRepositorio.findByIdUsuario(idUsuario);
    }
}
