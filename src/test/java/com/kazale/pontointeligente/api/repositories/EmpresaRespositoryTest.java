package com.kazale.pontointeligente.api.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kazale.pontointeligente.api.entities.Empresa;

//classe que testa se o CPJ e adicionado com sucesso e gravado no repositorio

@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRespositoryTest {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";
	
	/*Antes de cada test adicioma o valor do CNPJ e razaosocial ao objeto empresa
	para logo ser gravado no repositorio*/
	@BeforeEach
	public void setUp() throws Exception{
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exmemplo");
		empresa.setCnpj(CNPJ);
		this.empresaRepository.save(empresa);
	}
	
	/*Depois de ccada test elimina os objetos criados para evitar conflitos 
	e o uso desnecessario do repositorio*/
	@AfterEach
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	/*testa se CNPJ atribuido a objeto empressa e igual ao CCNPJ declarado no inicio, comprovando assim 
	que o CNPJ foi gravado com sucesso no repositorio (base de dados)*/
	@Test
	public void testBuscarPorCnpj() {
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
		
		Assertions.assertEquals(CNPJ, empresa.getCnpj());
	}

}
