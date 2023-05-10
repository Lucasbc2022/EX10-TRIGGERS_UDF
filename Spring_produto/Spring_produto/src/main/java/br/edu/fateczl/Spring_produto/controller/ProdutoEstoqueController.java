package br.edu.fateczl.Spring_produto.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.Spring_produto.model.Produto;
import br.edu.fateczl.Spring_produto.persistence.ProdutoDAO;

@Controller
public class ProdutoEstoqueController {

	@Autowired
	ProdutoDAO pDao;
	private int quantidade;
	
	@RequestMapping(name = "produtoestoque", value = "/produtoestoque", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		return new ModelAndView("produtoestoque");
	}
	
	@RequestMapping(name = "produtoestoque", value = "/produtoestoque", method = RequestMethod.POST)
	public ModelAndView produtos(ModelMap model, @RequestParam Map<String, String> allParams) {
		String valor_minimo = allParams.get("valor_minimo");
		String botao = allParams.get("botao");
		
		
		
		List<Produto> listaProdutoForaEstoque = new ArrayList<Produto>();
		String erro = "";
		String saida = "";
		int qtdMinima;
		
		try {
			
			
			if(botao.equalsIgnoreCase("buscar")) {
				qtdMinima = Integer.parseInt(valor_minimo);
				quantidade = pDao.consultaQtdForaEstoque(qtdMinima);
				System.out.println(qtdMinima);
			} 
			else {
			if(botao.equalsIgnoreCase("listar")){
				qtdMinima = Integer.parseInt(valor_minimo);
				listaProdutoForaEstoque = pDao.consultaListaProdutosForaEstoque(qtdMinima);
				quantidade = 0;
				System.out.println(qtdMinima);
			}
			}
			
			
		} catch(ClassNotFoundException | SQLException | NumberFormatException e) {
			erro = "Nao pode haver valor zerado";
			System.out.println(erro);
			
		} finally {
			
			model.addAttribute("listaProdutoForaEstoque", listaProdutoForaEstoque);
			model.addAttribute("quantidade", quantidade);
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
		}
		
		return new ModelAndView("produtoestoque");
	}
}
