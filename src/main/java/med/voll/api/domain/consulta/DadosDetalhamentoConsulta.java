package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {

	public DadosDetalhamentoConsulta(DadosAgendamentoConsulta dados) {
		this(null, dados.idMedico(), dados.idPaciente(), dados.data());
	}
	
}
