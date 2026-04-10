package com.agencia.painel_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.agencia.painel_admin.model.Financeiro;
import com.agencia.painel_admin.service.FinanceiroService;

@Controller
@RequestMapping("/financeiro")
public class FinanceiroController {

    private final FinanceiroService service;

    public FinanceiroController(FinanceiroService service) {
        this.service = service;
    }

    //  LISTAR
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("lista", service.listar());
        model.addAttribute("conteudo", "financeiro :: conteudo");

        return "layout";
    }

    //  SALVAR
    @PostMapping
    public String salvar(Financeiro financeiro) {

        service.salvar(financeiro);

        return "redirect:/financeiro";
    }

    //  DELETAR
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {

        service.deletar(id);

        return "redirect:/financeiro";
    }
}