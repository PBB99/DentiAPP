package Modelo;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tratamientos")

public class TreatmentsHibernate {
	
	@Id
	@Column(name = "codigo_tratamiento", nullable = false)
	private Integer codigo_tratamiento;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "precio", nullable = false)
	private int precio;
	
	public TreatmentsHibernate() {}
	
	public TreatmentsHibernate(Integer codigo_tratamiento, String nombre, int precio) {
		super();
		this.codigo_tratamiento = codigo_tratamiento;
		this.nombre = nombre;
		this.precio = precio;
	}

	public Integer getCodigo_tratamiento() {
		return codigo_tratamiento;
	}

	public void setCodigo_tratamiento(Integer codigo_tratamiento) {
		this.codigo_tratamiento = codigo_tratamiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@ManyToOne() 
    @JoinColumn(name = "especialidad")
    private SpecialityHibernate especialidad; //Este atributo va a @OneToMany en Cliente

    public SpecialityHibernate getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(SpecialityHibernate especialidad_tratamiento) {
        this.especialidad = especialidad_tratamiento;
    }
    
    
}
