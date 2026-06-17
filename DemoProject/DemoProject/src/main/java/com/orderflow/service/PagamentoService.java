package com.orderflow.service;

import com.orderflow.model.Pagamento;
import com.orderflow.model.Pedido;
import com.orderflow.repository.PagamentoRepository;
import com.orderflow.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Registrar pagamento
    public Pagamento registrarPagamento(Pagamento pagamento) {

        pagamento.setStatusPagamento("PAGO");
        pagamento.setDataPagamento(LocalDateTime.now());

        Pedido pedido = pedidoRepository.findById(pagamento.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusAtual("EM_PREPARACAO");
        pedidoRepository.save(pedido);

        return pagamentoRepository.save(pagamento);
    }

    // Listar todos
    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Pagamento> buscarPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    // Atualizar pagamento
    public Pagamento atualizar(Integer id, Pagamento pagamentoAtualizado) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setMetodoPagamento(pagamentoAtualizado.getMetodoPagamento());
        pagamento.setValor(pagamentoAtualizado.getValor());
        pagamento.setStatusPagamento(pagamentoAtualizado.getStatusPagamento());
        pagamento.setIdPedido(pagamentoAtualizado.getIdPedido());

        return pagamentoRepository.save(pagamento);
    }

    // Excluir
    public void deletar(Integer id) {
        pagamentoRepository.deleteById(id);
    }
}