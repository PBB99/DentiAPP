package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "tratamientos")
public class TreatmentsHibernate {
	
	@Id
	@Column(name = "codigo_tratamiento", nullable = false)
	private Integer codigo_tratamiento;
	
//	@Column(name = "especialidad", nullable = false)
//	private Integer especialidad;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "precio", nullable = false)
	private String precio;
	
	public TreatmentsHibernate() {}
	
	public TreatmentsHibernate(Integer codigo_tratamiento, String nombre, String precio) {
		super();
		this.codigo_tratamiento = codigo_tratamiento;
//		this.especialidad = especialidad;
		this.nombre = nombre;
		this.precio = precio;
	}

	public Integer getCodigo_tratamiento() {
		return codigo_tratamiento;
	}

	public void setCodigo_tratamiento(Integer codigo_tratamiento) {
		this.codigo_tratamiento = codigo_tratamiento;
	}

//	public Integer getEspecialidad() {
//		return especialidad;
//	}
//
//	public void setEspecialidad(Integer especialidad) {
//		this.especialidad = especialidad;
//	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "especialidad")
    private SpecialityHibernate especialidad_tratamiento; //Este atributo va a @OneToMany en Cliente

    public SpecialityHibernate getEspecialidad() {
        return especialidad_tratamiento;
    }

    public void setEspecialidad(SpecialityHibernate especialidad_tratamiento) {
        this.especialidad_tratamiento = especialidad_tratamiento;
    }
    
    @OneToMany(mappedBy = "tratamientoHiber", cascade = CascadeType.ALL)
    private List<CitaHibernate> citas;
    
    public List<CitaHibernate> getCitas(){
        return citas;
    }
    
    public void addCita(CitaHibernate cita){
        if (citas == null) citas=new ArrayList<>();
        citas.add(cita);
        cita.setTratamiento(this);
    }
}
