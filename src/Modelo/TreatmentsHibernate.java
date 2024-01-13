package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "tratamientos")

public class TreatmentsHibernate {
	
	@Id
	@Column(name = "id_tratamiento", nullable = false)
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
	
	
	
	@Override
	public String toString() {
		return nombre;
	}



	@ManyToOne() 
    @JoinColumn(name = "especialidades_id_especialidad")
    private SpecialityHibernate especialidad_tratamiento; //Este atributo va a @OneToMany en Cliente

    public SpecialityHibernate getEspecialidad() {
        return especialidad_tratamiento;
    }

    public void setEspecialidad(SpecialityHibernate especialidad_tratamiento) {
        this.especialidad_tratamiento = especialidad_tratamiento;
    }
    
    @OneToMany(mappedBy = "tratamiento_cita", cascade = CascadeType.ALL)
	private List<CitaHibernate> citas;
	
	public List<CitaHibernate> getCitas(){
		return citas;
	}
	
	public void addTratamiento(CitaHibernate c){
        if (citas == null) citas=new ArrayList<>();
        citas.add(c);
        c.setTratamiento(this);
    }
}
