package Modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class ClienteHibernate implements Serializable {

	@Id
	@Column(name = "dni_cliente", nullable = false)
	private String dni_cliente;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellidos", nullable = false)
	private String apellidos;

	@Column(name = "edad", nullable = false)
	private int edad;

	public ClienteHibernate() {
		super();
	}

	public ClienteHibernate(String dni_cliente, String nombre, String apellidos, int edad) {
		super();
		this.dni_cliente = dni_cliente;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}

	public String getDni_cliente() {
		return dni_cliente;
	}

	public void setDni_cliente(String dni_cliente) {
		this.dni_cliente = dni_cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	
}
