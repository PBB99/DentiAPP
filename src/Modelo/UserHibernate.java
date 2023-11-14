package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class UserHibernate {

	@Id
	@Column(name = "dni", nullable = false)
	private String dni;
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Column(name = "apellido", nullable = false)
	private String apellido;
	@Column(name = "contraseña", nullable = false)
	private String contraseña;
	@Column(name = "estado", nullable = false)
	private boolean estado;
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
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "TreatmentsUser [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", contraseña="
				+ contraseña + ", estado=" + estado + "]";
	}
	
	public UserHibernate() {}
	
	public UserHibernate(String dni, String nombre, String apellido, String contraseña, boolean estado) {
		this.dni=dni;
		this.nombre=nombre;
		this.apellido=apellido;
		this.contraseña=contraseña;
		this.estado=estado;
	}
}
