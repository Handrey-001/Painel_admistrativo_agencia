package com.agencia.painel_admin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.agencia.painel_admin.model.Cliente;
import com.agencia.painel_admin.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    // --- MÉTODOS CRUD ---

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + id));
    }

    // --- MÉTODOS DE GRÁFICO ---

    public int[] clientesPorMes() {
        List<Object[]> dados = repository.contarClientesPorMes();
        return processarDadosGrafico(dados);
    }

    // SERVICE COM FILTRO DE CLIENTES
    public int[] clientesPorAno(int ano) {
        List<Object[]> dados = repository.contarClientesPorMesPorAno(ano);
        return processarDadosGrafico(dados);
    }


    
    public int[] receitaPorAno(int ano) {
        List<Object[]> dados = repository.receitaPorAno(ano);
        return processarDadosGrafico(dados);
    }

    // Método auxiliar para evitar repetir código
    private int[] processarDadosGrafico(List<Object[]> dados) {
        int[] resultado = new int[12];
        for (Object[] linha : dados) {
            int mes = ((Number) linha[0]).intValue();
            int total = ((Number) linha[1]).intValue();
            if (mes >= 1 && mes <= 12) {
                resultado[mes - 1] = total;
            }
        }
        return resultado;
    }
}