package com.linkatalog.projetofinal.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends JpaRepository<Links, Long> {
	
	List<Links> findByCompanyId(Long companyId);
	  
	  @Transactional
	  void deleteByCompanyId(Long companyId);

}
