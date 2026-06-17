package com.orderflow.service;

import com.orderflow.model.Pedido;
import com.orderflow.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Criar pedido
    public Pedido criarPedido(Pedido pedido) {

        // Regra de negócio
        pedido.setStatusAtual("PENDENTE");
        pedido.setValorTotal(0.0);

        return pedidoRepository.save(pedido);
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

        return pedidoRepository.save(pedido);
    }

    // Excluir pedido
    public void deletar(Integer id) {
        pedidoRepository.deleteById(id);
    }
}