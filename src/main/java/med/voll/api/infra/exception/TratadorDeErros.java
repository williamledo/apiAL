package med.voll.api.infra.exception;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice //Indica que essa classe vai tratar exceções para todos os controllers
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class) //Indica que esse método trata a exceção EntityNotFoundException
	public ResponseEntity tratarErro404() {
		
		return ResponseEntity.notFound().build();
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<List<ErroDTO>> tratarErroIntegridade(DataIntegrityViolationException ex) { //Trata erros de violação de integridade de dados, como chaves únicas duplicadas

	    String campo = "desconhecido";
	    String mensagem = "Violação de integridade de dados";

	    Throwable causa = ex.getRootCause(); // Obtém a causa raiz da exceção
	    if (causa != null && causa.getMessage() != null) {
	        String erro = causa.getMessage();

	        if (erro.contains("medicos_email_key")) {
	            campo = "email";
	            mensagem = "Email já cadastrado";
	        } else if (erro.contains("medicos_crm_key")) {
	            campo = "crm";
	            mensagem = "CRM já cadastrado";
	        }
	    }

	    return ResponseEntity
	            .badRequest()
	            .body(List.of(new ErroDTO(campo, mensagem)));
	}


	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		
		var erros = ex.getFieldErrors();
		
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); //Transforma a lista de FieldError em uma lista de DadosErroValidacao
		
	}
	
	private record DadosErroValidacao(String campo, String mensagem) { //Record para representar os dados de erro de validação
	
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
		
	}
	
}
