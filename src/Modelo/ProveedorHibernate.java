package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
public class ProveedorHibernate implements Serializable{
	
	@Id
	@Column(name = "cif", nullable = false)
	private Integer cif;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "estado", nullable = false)
	private int estado;

	public ProveedorHibernate(Integer cif, String nombre, int estado) {
		super();
		this.cif = cif;
		this.nombre = nombre;
		this.estado = estado;
	}
	
	

}