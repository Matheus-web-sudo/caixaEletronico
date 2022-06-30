package com.h2.db.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; //Importando bibliotecas padrão
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.h2.db.exception.RecordNotFoundException;
import com.h2.db.model.User;
import com.h2.db.service.UserService; //Importando métodos

@Controller
@RequestMapping("/")
public class UserController {  //Classe principal (Index)
	@Autowired
	UserService service;

	@RequestMapping //Usando um RequestMaping para fazer uma requisição POST para o envio de dados para URL
	public String getAllUsers(Model model) {
		System.out.println("getAllUsers");

		List<User> list = service.getAllUsers(); 

		model.addAttribute("users", list);

		return "list-users";
	}

	@RequestMapping(path = { "/edit", "/edit/{id}" }) //A função para editar buscando um usuária cadastrado pelo id 
	public String editUserById(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {

		System.out.println("editUserById" + id);
		if (id.isPresent()) {
			User entity = service.getUserById(id.get());
			model.addAttribute("user", entity);
		} else {
			model.addAttribute("user", new User());
		}

		return "add-edit-user";
	}

	@RequestMapping(path = { "/withdraw", "/withdraw/{id}" })
	public String withdraw(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {

		if (id.isPresent()) {
			User entity = service.getUserById(id.get());
			model.addAttribute("user", entity);
		} else {
			model.addAttribute("user", new User());
		}

		return "with-draw";
	}

	@RequestMapping(path = { "/deposit", "/deposit/{id}" })
	public String deposit(Model model, @PathVariable("id") Optional<Long> id)
			throws RecordNotFoundException {

		if (id.isPresent()) {
			User entity = service.getUserById(id.get());
			model.addAttribute("user", entity);
		} else {
			model.addAttribute("user", new User());
		}

		return "deposit";
	}

	@RequestMapping(path = "/delete/{id}")  //Deletar um usuário
	public String deleteUserById(Model model, @PathVariable("id") Long id)
			throws RecordNotFoundException {

		System.out.println("deleteUserById" + id);

		service.deleteUserById(id);
		return "redirect:/";
	}

	@RequestMapping(path = "/createUser", method = RequestMethod.POST)  //Adicionar um usuário
	public String createOrUpdateUser(User user) {
		System.out.println("createOrUpdateUser ");

		service.createOrUpdateUser(user);

		return "redirect:/";
	}

	@RequestMapping(path = "/insert", method = RequestMethod.POST)  //Depositar 
	public String insertMoney(User user, @RequestParam(value = "money", required = false) String money)
			throws RecordNotFoundException {

		service.depositar(user, money);
		return "redirect:/";
	}

	@RequestMapping(path = "/get", method = RequestMethod.POST) //sacar
	public String getMoney(User user, @RequestParam(value = "money", required = false) String money)
			throws Exception {

		service.sacar(user,money);
        return "redirect:/";
	}
}
