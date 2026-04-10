package com.agencia.painel_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agencia.painel_admin.service.ClienteService;
import com.agencia.painel_admin.service.FinanceiroService;

@Controller
public class DashboardController {

    private final ClienteService service;
    private final FinanceiroService financeiroService;

    // Injeção de dependência dupla
    public DashboardController(ClienteService service, FinanceiroService financeiroService) {
        this.service = service;
        this.financeiroService = financeiroService;
    }

    @GetMapping("/dashboard")
    public String dashboard(
        @RequestParam(required = false, defaultValue = "2026") int ano,
        Model model) {

        model.addAttribute("ano", ano);
        model.addAttribute("totalClientes", service.listar().size());

        // Dados do Gráfico de Clientes (Garante 12 meses)
        int[] dadosClientes = service.clientesPorMes();
        if (dadosClientes == null || dadosClientes.length < 12) {
            dadosClientes = new int[12];
        }
        model.addAttribute("dados", service.clientesPorAno(ano)); 

        // Dados da Receita 
        model.addAttribute("receita", service.receitaPorAno(ano));
        
        model.addAttribute("receitaBruta", financeiroService.somarReceitaBruta());

        model.addAttribute("lucroTotal", financeiroService.calcularLucro());

        
        model.addAttribute("conteudo", "dashboard :: conteudo");

        return "layout";
    }
}