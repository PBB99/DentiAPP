package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "inventario")
public class InventarioHibernate implements Serializable{
	
	@Id
	@Column(name = "id_producto", nullable = false)
	private Integer id_historial;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	public InventarioHibernate() {
		super();
	}

	public InventarioHibernate(Integer id_historial, String nombre, Integer cantidad) {
		super();
		this.id_historial = id_historial;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public Integer getId_historial() {
		return id_historial;
	}

	public void setId_historial(Integer id_historial) {
		this.id_historial = id_historial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}