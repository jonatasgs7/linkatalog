package com.linkatalog.projetofinal.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkatalog.projetofinal.exception.ResourceNotFoundException;

import com.linkatalog.projetofinal.model.CompanyRepository;
import com.linkatalog.projetofinal.model.LinkRepository;
import com.linkatalog.projetofinal.model.Links;

@RestController
public class LinksController {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private LinkRepository linkRepository;
	
	
	@GetMapping("/company/{companyId}/links")
	  public ResponseEntity<List<Links>> getAllLinksByCompanyId(@PathVariable(value = "companyId") Long companyId) {
	    if (!companyRepository.existsById(companyId)) {
	    	throw new ResourceNotFoundException("Nenhuma empresa encontrada com o ID = " + companyId);
	    }
	    List<Links> link = linkRepository.findByCompanyId(companyId);
	    return new ResponseEntity<>(link, HttpStatus.OK);
	  }
	
	@GetMapping("/links/{id}")
	  public ResponseEntity<Links> getLinksByCompanyId(@PathVariable(value = "id") Long id) {
	    Links link = linkRepository.findById(id)
	    		.orElseThrow(() -> new ResourceNotFoundException("Nenhum link encontrado com o ID = " + id));;
	    return new ResponseEntity<>(link, HttpStatus.OK);
	  }
	
	@PostMapping("/company/{companyId}/link")
	  public ResponseEntity<Links> createLink(@PathVariable(value = "companyId") Long companyId,
			  @RequestBody Links linkRequest) {
	    Links link = companyRepository.findById(companyId).map(company -> {
	      linkRequest.setCompany(company);
	      return linkRepository.save(linkRequest);
	    }).orElseThrow(() -> new ResourceNotFoundException("Nenhuma empresa encontrada com o ID = " + companyId));
	    return new ResponseEntity<>(link, HttpStatus.CREATED);
	  }
	
	
	@DeleteMapping("/links/{id}")
	  public ResponseEntity<HttpStatus> deleteLink(@PathVariable("id") Long id) {
	    linkRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	
	@DeleteMapping("/company/{companyId}/links")
	  public ResponseEntity<List<Links>> deleteAllLinksOfCompany(@PathVariable(value = "companyId") Long companyId) {
	    if (!companyRepository.existsById(companyId)) {
	      throw new ResourceNotFoundException("Nenhuma empresa encontrada com o ID = " + companyId);
	    }
	    linkRepository.deleteByCompanyId(companyId);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	
	@ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
	   //response.setHeader("Content-Type: application/json", "charset=utf-8");
	   //response.setHeader("Content-Type", "multipart/form-data");
	   response.setHeader("Access-Control-Allow-Origin", "*");
	   response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	   response.setHeader("Access-Control-Allow-Credentials", "true");
	   response.setHeader("Access-Control-Allow-Headers", "auth-token, access-control-allow-origin");
    }
	
	

}
