package com.RG.PIGP.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RG.PIGP.models.Pessoas;
import com.RG.PIGP.repository.EmpresaRepository;
import com.RG.PIGP.repository.PessoasRespository;
import com.RG.PIGP.models.Empresa;


@Controller
public class EmpresaControllers {
	
	private EmpresaRepository er;
	private PessoasRespository pr;
	
	// Cadastrar Empresa
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.GET)
	public String form() {
		return "empresa/formEmpresa" ;
	}
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.POST)
	public String form(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os Campos");
			return "redirect:/cadastrarEmpresa";
			
		}
		er.save(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa cadastrada com exito");
	
		return "redirect:/cadastrarEmpresa";
	}
	
	//Lista Empresa
	
	@RequestMapping("/empresas")
	public ModelAndView listaEmpresas() {
		ModelAndView mv = new ModelAndView("empresa/listaEmpresa");
		Iterable<Empresa>empresas = er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
	
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEmpresa(@PathVariable("codigo") long codigo) {
		
		Empresa empresa = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("empresa/detalhesEmpresa");
		mv.addObject("empresa", empresa);
		
		Iterable<Pessoas>pessoa = pr.findByEmpresa(empresa);
		mv.addObject("pessoa", pessoa);
		
		return mv;
	}
	
	// Deleta a Empresa 
	@RequestMapping("/deletarEmpresa")
	public String deletarEmpresa(long codigo) {
		
		Empresa empresa = er.findByCodigo(codigo);
		er.delete(empresa);
		return "redirect:/empresas";
		
	}
	
	public String detalhesEmpresaPost(@PathVariable("codigo")long codigo, @Valid Pessoas pessoas, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/${codigo}";
		}
		
		//RG duplicado
		if(pr.findByRg(pessoas.getRg()) != null) {
			attributes.addFlashAttribute("mensagem erro", "RG duplicado");
			return "redirect:/${codigo}";
		}
		
		Empresa empresa = er.findByCodigo(codigo);
		pessoas.setEmpresa(empresa);
		pr.save(pessoas);
		attributes.addFlashAttribute("mensagem", "Pessoa adicionada");
		return "redirect:/${codigo}";
		
		
	}
	
	
	//Deleta Pessoas pelo RG
	@RequestMapping("/deletarPessoas")
	public String deletarPessoas(String rg) {
		Pessoas pessoas = pr.findByRg(rg);
		Empresa empresa = pessoas.getEmpresa();
		String codigo = "" + empresa.getCodigo();
		
		pr.delete(pessoas);
		
		return "redirect:/" +codigo;
	}
	
	//Metodos que atualisam empresa
	// formulario de adição de empresa
	
	public ModelAndView editarEmpresa(long codigo) {
		
	}
	
	
	
	

}
