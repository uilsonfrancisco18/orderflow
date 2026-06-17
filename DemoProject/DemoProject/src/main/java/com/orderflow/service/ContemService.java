package com.orderflow.service;

import com.orderflow.model.Contem;
import com.orderflow.model.Pedido;
import com.orderflow.model.Produto;
import com.orderflow.repository.ContemRepository;
import com.orderflow.repository.PedidoRepository;
import com.orderflow.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContemService {

    @Autowired
    private ContemRepository contemRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Adicionar item ao pedido (com regras de negócio)
    public Contem salvar(Contem contem) {

        // Buscar produto
        Produto produto = produtoRepository.findById(contem.getIdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Regra de negócio: verificar estoque disponível
        if (produto.getEstoque() < contem.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + produto.getEstoque());
        }

        // Regra de negócio: calcular subtotal do item
        double subtotal = produto.getPreco() * contem.getQuantidade();
        contem.setSubtotal(subtotal);

        // Regra de negócio: decrementar estoque do produto
        produto.setEstoque(produto.getEstoque() - contem.getQuantidade());
        produtoRepository.save(produto);

        // Salvar o item
        Contem contemSalvo = contemRepository.save(contem);

        // Regra de negócio: recalcular valor total do pedido
        recalcularValorTotal(contem.getIdPedido());

        return contemSalvo;
    }

    public List<Contem> listarTodos() {
        return contemRepository.findAll();
    }

    public Contem buscarPorId(Integer id) {
        return contemRepository.findById(id).orElse(null);
    }

    // Listar itens de um pedido específico
    public List<Contem> listarPorPedido(Integer idPedido) {
        return contemRepository.findByIdPedido(idPedido);
    }

    // Excluir item e recalcular o valor total do pedido
    public void excluir(Integer id) {

        Contem contem = contemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        // Regra de negócio: devolver estoque ao produto ao excluir item
        Produto produto = produtoRepository.findById(contem.getIdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setEstoque(produto.getEstoque() + contem.getQuantidade());
        produtoRepository.save(produto);

        contemRepository.deleteById(id);

        // Regra de negócio: recalcular valor total do pedido após remoção
        recalcularValorTotal(contem.getIdPedido());
    }

    // Método auxiliar: soma todos os subtotais dos itens e atualiza o pedido
    private void recalcularValorTotal(Integer idPedido) {

        List<Contem> itens = contemRepository.findByIdPedido(idPedido);

        double total = itens.stream()
                .mapToDouble(item -> item.getSubtotal() != null ? item.getSubtotal() : 0.0)
                .sum();

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setValorTotal(total);
        pedidoRepository.save(pedido);
    }
}