package Modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "catalogo")
public class CatalogoHibernate implements Serializable{
	
	@Id
	@Column(name = "id_producto", nullable = false)
	private Integer id_producto;
	
	@Column(name = "cif", nullable = false)
	private Integer cif;
	
	@Column(name = "precio", nullable = false)
	private Integer precio;

	
	
	public CatalogoHibernate(Integer id_producto, Integer cif, Integer precio) {
		super();
		this.id_producto = id_producto;
		this.cif = cif;
		this.precio = precio;
	}

	public Integer getId_producto() {
		return id_producto;
	}

	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
	}

	public Integer getCif() {
		return cif;
	}

	public void setCif(Integer cif) {
		this.cif = cif;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
	
}