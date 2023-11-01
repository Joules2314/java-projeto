package com.RG.PIGP.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.RG.PIGP.models.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {
	Empresa findByCodigo(long codigo);
	List<Empresa> findByNome(String nome);
}
