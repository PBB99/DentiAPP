package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "historial_citas")
public class HistorialCitasHibernate implements Serializable{
	
	@Id
	@Column(name = "id_historialcitas", nullable = false)
	private Integer id_historialcitas;
	
	@Column(name = "observaciones", nullable = false)
	private String observaciones;

	public HistorialCitasHibernate(Integer id_historialcitas, String observaciones) {
		super();
		this.id_historialcitas = id_historialcitas;
		this.observaciones = observaciones;
	}
	
}