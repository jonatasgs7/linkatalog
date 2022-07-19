package com.linkatalog.projetofinal.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.linkatalog.projetofinal.model.Company;
import com.linkatalog.projetofinal.model.CompanyRepository;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@GetMapping(path={"/", "/all"})
	public @ResponseBody Iterable<Company> allCompanies() {
		return companyRepository.findAll();
	}
	
	@GetMapping("{id}")
	Company getCompany(@PathVariable Long id) {
		return companyRepository.findById(id).get();
	}
	
	@PostMapping("/add")
	public Company addCompany(@RequestBody Company company) {
		return companyRepository.save(company);
	}
	
	@DeleteMapping(path="/delete/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyRepository.deleteById(id);
	}
	
	@PutMapping("/update/{id}")
	Company updateCompany(@RequestBody Company newCompany, @PathVariable Long id) {
		Company x = companyRepository.findById(id).get();
		
		if(newCompany.getName() != null && !newCompany.getName().isEmpty()) {
			x.setName(newCompany.getName());
		}
		
		x.setDescription(newCompany.getDescription());
		// x.setImage_url(newCompany.getImage_url());
		x.setColor1(newCompany.getColor1());
		x.setColor2(newCompany.getColor2());
		return companyRepository.save(x);
	}
	
	@ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
	   //response.setHeader("Content-Type: application/json", "charset=utf-8");
	   // response.setHeader("Content-Type","multipart/form-data");
	   response.setHeader("Access-Control-Allow-Origin", "*");
	   response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	   response.setHeader("Access-Control-Allow-Credentials", "true");
	   response.setHeader("Access-Control-Allow-Headers", "auth-token, access-control-allow-origin");
    }

}
