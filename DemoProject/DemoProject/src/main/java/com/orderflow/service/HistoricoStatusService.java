package com.orderflow.service;

import com.orderflow.model.HistoricoStatus;
import com.orderflow.repository.HistoricoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricoStatusService {

    @Autowired
    private HistoricoStatusRepository historicoStatusRepository;

    // Registrar histórico
    public HistoricoStatus registrarHistorico(HistoricoStatus historico) {

        historico.setDataHora(LocalDateTime.now());

        return historicoStatusRepository.save(historico);
    }

    // Listar todos
    public List<HistoricoStatus> listarTodos() {
        return historicoStatusRepository.findAll();
    }

    // Buscar por ID
    public Optional<HistoricoStatus> buscarPorId(Integer id) {
        return historicoStatusRepository.findById(id);
    }

    // Excluir
    public void deletar(Integer id) {
        historicoStatusRepository.deleteById(id);
    }
}