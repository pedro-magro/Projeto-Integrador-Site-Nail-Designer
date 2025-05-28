package br.com.nailDesigner.dtos;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DisponibilidadeDTO {
	
	private Long id;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Long funcionarioId;
    private String funcionarioEmail;

    
    
    
    public String getFuncionarioEmail() {
        return funcionarioEmail;
    }

    public void setFuncionarioEmail(String funcionarioEmail) {
        this.funcionarioEmail = funcionarioEmail;
    }  
	public Long getFuncionarioId() {
		return funcionarioId;
	}
	public void setFuncionarioId(Long id) {
		this.funcionarioId = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DayOfWeek getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(DayOfWeek diaSemana) {
		this.diaSemana = diaSemana;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public LocalTime getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}
    
    

}
