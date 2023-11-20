package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "historial_tratamientos")
public class HistorialTratamientosHibernate implements Serializable{
	
	@Id
	@Column(name = "id_historial", nullable = false)
	private Integer id_historial;
	
	@Column(name = "dni_paciente", nullable = false)
	private String dni_paciente;
	
	@Column(name = "estado", nullable = false)
	private Integer estado;
	
	@Column(name = "pago", nullable = false)
	private Integer pago;
	
	@Column(name = "observaciones", nullable = false)
	private String observaciones;

	public HistorialTratamientosHibernate(Integer id_historial, String dni_paciente, Integer estado, Integer pago,
			String observaciones) {
		super();
		this.id_historial = id_historial;
		this.dni_paciente = dni_paciente;
		this.estado = estado;
		this.pago = pago;
		this.observaciones = observaciones;
	}

}