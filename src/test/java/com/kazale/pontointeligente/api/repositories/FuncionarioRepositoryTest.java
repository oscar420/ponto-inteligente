package com.kazale.pontointeligente.api.repositories;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.kazale.pontointeligente.api.entities.Empresa;
import com.kazale.pontointeligente.api.entities.Funcionario;
import com.kazale.pontointeligente.api.enums.PerfilEnum;
import com.kazale.pontointeligente.api.utils.PasswordUtils;

@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";
	
	/* Cria as instancias dos objetos Empresa e Funcionario.
	 * Grava os objetos nos repositorios respectivos
	 */
	@BeforeEach
	public void setUp() throws Exception{
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		
	}
	
	/* Elimina as instanias criadas dos repositorios para evitar conflitos*/
	@AfterEach
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	/* Testa se o metodo findByCpf funciona.
	 * Comparando o Cpf declarado na viariavel CPF e o cpf do funcionario criado\
	 */
	@Test
	public void testBuscarFuncionarioPorCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		
		Assertions.assertEquals(CPF, funcionario.getCpf());
	}
	
	/*Testa se o metodo findByEmail funciona.
	 * Comparando o Email declarado na viariavel EMAIL e o email do funcionario criado
	 */
	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		
		Assertions.assertEquals(EMAIL, funcionario.getEmail());
	}
	
	/* Testa o metodo findByCpfOrEmail
	 * conferindo que o funcionario encontrado usando o EMAIL ou CPF nao e nulo (encontrou o funcionario).
	 */
	@Test
	public void testBuscarFuncionarioPorEmailECpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		
		Assertions.assertNotNull(funcionario);
	}
	
	
	/* Testa o metodo findByCpfOrEmail
	 * conferindo que o funcionario encontrado usando um emialinvalido e o CPF nao e nulo (encontrou o funcionario).
	 */
	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");
		
		Assertions.assertNotNull(funcionario);
	}
	
	/* Testa o metodo findByCpfOrEmail
	 * conferindo que o funcionario encontrado usando um EMAIL e o Cpf invalido nao e nulo (encontrou o funcionario).
	 */
	@Test
	public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("12345678901", EMAIL);

		Assertions.assertNotNull(funcionario);
	}
	
	/*Metodos que que da os valores as classes Funcionario e Empresa*/
private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
	Funcionario funcionario = new Funcionario();
	funcionario.setNome("Fulano de tal");
	funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
	funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
	funcionario.setCpf(CPF);
	funcionario.setEmail(EMAIL);
	funcionario.setEmpresa(empresa);
	
	return funcionario;	
}

private Empresa obterDadosEmpresa() {
	Empresa empresa = new Empresa();
	empresa.setCnpj("51463645000100");
	empresa.setRazaoSocial("Empresa de exemplo");
	
	return empresa;
}

}
