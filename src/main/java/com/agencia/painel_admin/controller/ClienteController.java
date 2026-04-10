package com.agencia.painel_admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agencia.painel_admin.model.Cliente;
import com.agencia.painel_admin.service.ClienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/clientes")
public class ClienteController {
    

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("clientes", service.listar());
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("conteudo","clientes::conteudo");

        return  "layout";
    }

    @PostMapping
    public String salvar(@ModelAttribute Cliente cliente) {
        service.salvar(cliente) ;     
        return "redirect:/clientes";
       
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Cliente cliente = service.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("conteudo", "editar-cliente::conteudo");

        return "layout";
    }

    @PostMapping("/atualizar")
    public String atualizar(Cliente cliente) {

        service.salvar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        int totalClientes = service.listar().size();
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("conteudo", "dashboard :: conteudo");
        
        return "layout";
    }
    
    
    
    
    
    
}
