package com.RG.PIGP.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.RG.PIGP.models.Pessoas;
import com.RG.PIGP.models.Empresa;

public interface PessoasRespository extends CrudRepository<Pessoas, String> {
	Iterable<Pessoas>findByEmpresa(Empresa empresa);
	
	Pessoas findByRg(String rg);
	
	Pessoas findById(long id);
	
	List<Pessoas>findByNomePessoa(String nomePessoa);

}
