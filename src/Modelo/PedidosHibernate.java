package Modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "pedidos")
public class PedidosHibernate implements Serializable {

	@Id
	@Column(name = "id_pedido", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_pedido;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	public PedidosHibernate() {
		super();
		

	}

	public PedidosHibernate(Date fecha) {
		super();
		this.fecha = fecha;

	}

	public Integer getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	@ManyToOne()
	@JoinColumn(name = "pedidos_id_producto")
	private InventarioHibernate producto_pedido;

	public InventarioHibernate getProducto() {
		return producto_pedido;
	}

	public void setProducto(InventarioHibernate especialidad_tratamiento) {
		this.producto_pedido = especialidad_tratamiento;
	}

	

}