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
	
	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;

	public InventarioHibernate(Integer id_historial, Integer cantidad) {
		super();
		this.id_historial = id_historial;
		this.cantidad = cantidad;
	}

}