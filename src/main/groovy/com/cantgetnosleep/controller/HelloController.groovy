package com.cantgetnosleep.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
@RequestMapping("/movie")
public class HelloController {
 
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String sayHello(@PathVariable String name, ModelMap model) {
 
		model.addAttribute("movie", name);
		return "list";
 
	}
 
}