package com.orderflow.service;

import com.orderflow.model.Entrega;
import com.orderflow.model.Pedido;
import com.orderflow.repository.EntregaRepository;
import com.orderflow.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Registrar envio
    public Entrega registrarEnvio(Entrega entrega) {

        entrega.setDataEnvio(LocalDateTime.now());
        entrega.setStatusEntrega("ENVIADO");

        Pedido pedido = pedidoRepository.findById(entrega.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusAtual("ENVIADO");
        pedidoRepository.save(pedido);

        return entregaRepository.save(entrega);
    }

    // Confirmar entrega
    public Entrega confirmarEntrega(Integer idEntrega) {

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        entrega.setDataEntrega(LocalDateTime.now());
        entrega.setStatusEntrega("ENTREGUE");

        Pedido pedido = pedidoRepository.findById(entrega.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatusAtual("ENTREGUE");
        pedidoRepository.save(pedido);

        return entregaRepository.save(entrega);
    }

    public List<Entrega> listarTodos() {
        return entregaRepository.findAll();
    }

    public Optional<Entrega> buscarPorId(Integer id) {
        return entregaRepository.findById(id);
    }

    // Atualizar entrega
    public Entrega atualizar(Integer id, Entrega entregaAtualizada) {

        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        entrega.setCodigoRastreio(entregaAtualizada.getCodigoRastreio());
        entrega.setTransporte(entregaAtualizada.getTransporte());
        entrega.setStatusEntrega(entregaAtualizada.getStatusEntrega());
        entrega.setIdPedido(entregaAtualizada.getIdPedido());

        return entregaRepository.save(entrega);
    }

    public void deletar(Integer id) {
        entregaRepository.deleteById(id);
    }
}