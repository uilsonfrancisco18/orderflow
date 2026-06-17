package com.orderflow.service;

import com.orderflow.model.Contem;
import com.orderflow.repository.ContemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContemService {

    @Autowired
    private ContemRepository contemRepository;

    public List<Contem> listarTodos() {
        return contemRepository.findAll();
    }

    public Contem salvar(Contem contem) {
        return contemRepository.save(contem);
    }
    public Contem buscarPorId(Integer id) {
        return contemRepository.findById(id).orElse(null);
    }

    public void excluir(Integer id) {
        contemRepository.deleteById(id);
    }
}