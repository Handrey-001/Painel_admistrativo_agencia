package com.agencia.painel_admin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.agencia.painel_admin.model.Financeiro; 
import com.agencia.painel_admin.repository.FinanceiroRepository;

@Service
public class FinanceiroService {

    private final FinanceiroRepository repository;

    public FinanceiroService(FinanceiroRepository repository) {
        this.repository = repository;
    }

    // --- MÉTODOS (CRUD) ---

    public List<Financeiro> listar() {
        return repository.findAll();
    }

    public Financeiro salvar(Financeiro f) {
        return repository.save(f);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
    public Double somarReceitaBruta() {
       
      Double total = repository.somarEntradas();
      return (total != null) ? total : 0.0;
    }

    
    public double calcularLucro() {
        Double lucro = repository.calcularLucroTotal();
        // Se o banco estiver vazio, então retorna 0.0
        return (lucro != null) ? lucro : 0.0;
    }
   

    // --- MÉTODO DO GRÁFICO ---
    public int[] receitaMensal() {
        List<Object[]> dados = repository.receitaPorMes();
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