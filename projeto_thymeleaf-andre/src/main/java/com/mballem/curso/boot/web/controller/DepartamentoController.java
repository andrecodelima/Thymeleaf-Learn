package com.mballem.curso.boot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService dpService;

	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {

		model.addAttribute("departamentos", dpService.buscarTodos());
		return "/departamento/lista";

	}

	// Primeira Forma
//	@PostMapping("/salvar")
//	public String salvar(Departamento departamento) {
//		dpService.salvar(departamento);
//		return "redirect:/departamentos/cadastrar";
//	}

	@PostMapping("/salvar")
	public String salvar(Departamento departamento, RedirectAttributes attr) {
		dpService.salvar(departamento);
		attr.addFlashAttribute("success", "Departamento inserido com sucesso");
		return "redirect:/departamentos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", dpService.buscarPorId(id));
		return "/departamento/cadastro";
	}

	// Primeira forma
//	@PostMapping("/editar")
//	public String editar(Departamento departamento) {
//		dpService.editar(departamento);
//		return "redirect:/departamentos/cadastrar";
//	}
	
	@PostMapping("/editar")
	public String editar(Departamento departamento, RedirectAttributes attr) {
		dpService.editar(departamento);
		attr.addFlashAttribute("success", "Departamento editado com sucesso");
		return "redirect:/departamentos/cadastrar";
	}
	

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {

// Primeira forma
//		if(!dpService.deptTemCargos(id)) {
//			dpService.excluir(id);
//		}

		if (dpService.deptTemCargos(id)) {
			model.addAttribute("fail", "Departamento não removido. Possui cargo(s) vinculado(s).");
			dpService.excluir(id);
		} else {
			dpService.excluir(id);
			model.addAttribute("sucess", "Departamento excluído com sucesso.");
		}

		return "/departamento/lista";
	}
}
