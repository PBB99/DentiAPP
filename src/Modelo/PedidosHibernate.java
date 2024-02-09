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
	@Column(name = "id_pedidos", nullable = false)
	private Integer id_pedido;

	@Column(name = "fecha", nullable = false)
	private Date fecha;

	@Column(name = "estado", nullable = false)
	private String estado;

	public PedidosHibernate(Integer id_odontograma, Date fecha) {
		super();
		this.id_pedido = id_odontograma;
		this.fecha = fecha;

	}

	public Integer getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	@ManyToOne()
	@JoinColumn(name = "pedidos_id_pedido")
	private InventarioHibernate producto_pedido;

	public InventarioHibernate getProducto() {
		return producto_pedido;
	}

	public void setProducto(InventarioHibernate especialidad_tratamiento) {
		this.producto_pedido = especialidad_tratamiento;
	}

	@ManyToOne
	@JoinColumn(name = "usuarios_dni_usuario")
	private UserHibernate usuario_pedido; // Este atributo va a @OneToMany en Cliente

	public UserHibernate getUser() {
		return usuario_pedido;
	}

	public void setUser(UserHibernate usuario_pedido) {
		this.usuario_pedido = usuario_pedido;
	}

}