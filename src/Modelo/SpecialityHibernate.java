package Modelo;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "especialidades")

public class SpecialityHibernate  implements Serializable{
	
	@Id
	@Column(name = "id_especialidad", nullable = false)
	private Integer id_especialidad;
	
	@Column(name = "especialidad", nullable = false)
	private String especialidad;
	
	public SpecialityHibernate() {}
	
	public SpecialityHibernate(Integer id_especialidad, String especialidad) {
		this.id_especialidad = id_especialidad;
		this.especialidad = especialidad;
	}

	public Integer getId_especialidad() {
		return id_especialidad;
	}

	public void setId_especialidad(Integer id_especialidad) {
		this.id_especialidad = id_especialidad;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	
}
