package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
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
	
	@Override
	public String toString() {
		return dni_cliente;
	}

	@OneToMany(mappedBy = "clienteHiber", cascade = CascadeType.ALL)
    private List<CitaHibernate> citas;
    
    public List<CitaHibernate> getCitas(){
        return citas;
    }
    
    public void addPedido(CitaHibernate cita){
        if (citas == null) citas=new ArrayList<>();
        citas.add(cita);
        cita.setCliente(this);
    }
}
