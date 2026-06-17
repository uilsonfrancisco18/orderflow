package com.orderflow.service;

import com.orderflow.model.HistoricoStatus;
import com.orderflow.model.Pedido;
import com.orderflow.repository.HistoricoStatusRepository;
import com.orderflow.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private HistoricoStatusRepository historicoStatusRepository;

    // Criar pedido
    public Pedido criarPedido(Pedido pedido) {

        // Regra de negócio
        pedido.setStatusAtual("PENDENTE");
        pedido.setValorTotal(0.0);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Regra de negócio: registrar histórico do status inicial
        registrarHistorico(pedidoSalvo.getIdPedido(), "PENDENTE");

        return pedidoSalvo;
    }

    // Listar pedidos
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    // Atualizar status
    public Pedido atualizarStatus(Integer id, String novoStatus) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusAtual(novoStatus);

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        // Regra de negócio: registrar histórico a cada mudança de status
        registrarHistorico(id, novoStatus);

        return pedidoAtualizado;
    }

    // Excluir pedido
    public void deletar(Integer id) {
        pedidoRepository.deleteById(id);
    }

    // Método auxiliar: registra automaticamente o histórico de status
    private void registrarHistorico(Integer idPedido, String status) {

        HistoricoStatus historico = new HistoricoStatus();
        historico.setIdPedido(idPedido);
        historico.setStatus(status);
        historico.setDataHora(LocalDateTime.now());

        historicoStatusRepository.save(historico);
    }
}