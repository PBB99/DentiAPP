package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")


public class UserHibernate  implements Serializable{
	
	@Id
	@Column(name = "dni_usuario", nullable = false)
	private String dni;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	@Column(name = "contraseña", nullable = false)
	private String contraseña;
	
	@Column(name = "estado", nullable = false)
	private int estado;
	
	public UserHibernate() {
		super();
	}
	
	public UserHibernate(String dni, String nombre, String apellido, String contraseña, int estado) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contraseña = contraseña;
		this.estado = estado;
	}

	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

    @Override
	public String toString() {
		return nombre;
	}
    
    @ManyToOne() 
    @JoinColumn(name = "especialidades_id_especialidad")
    private SpecialityHibernate especialidad_usuario; //Este atributo va a @OneToMany en Cliente

    public SpecialityHibernate getEspecialidad() {
        return especialidad_usuario;
    }

    public void setEspecialidad(SpecialityHibernate especialidad_usuario) {
        this.especialidad_usuario = especialidad_usuario;
    }
    
    @OneToMany(mappedBy = "usuario_cita", cascade = CascadeType.ALL)
	private List<CitaHibernate> citas;
	
	public List<CitaHibernate> getCitas(){
		return citas;
	}
	
	public void addTratamiento(CitaHibernate c){
        if (citas == null) citas=new ArrayList<>();
        citas.add(c);
        c.setUser(this);
    }
}
