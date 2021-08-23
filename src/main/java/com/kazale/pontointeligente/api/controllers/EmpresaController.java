package com.kazale.pontointeligente.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kazale.pontointeligente.api.dtos.EmpresaDto;
import com.kazale.pontointeligente.api.entities.Empresa;
import com.kazale.pontointeligente.api.response.Response;
import com.kazale.pontointeligente.api.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
		public EmpresaController() {
		}
	/**
	 * Busca una empresa dado un CNPJ
	 * 
	 * @param cnpj
	 * @return ResponseEntity<Response<EmpresaDto>>
	 */
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> bucarPorCnpj(@PathVariable ("cnpj") String cnpj){
		log.info("Buscando empresa por CNPJ: {}", cnpj);
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			log.info("Empresa nao encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa nao encontrada para o CNPJ " + cnpj);
			return  ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);		
	}
	
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		empresaDto.setId(empresa.getId());
		
		return empresaDto;
	}

}
