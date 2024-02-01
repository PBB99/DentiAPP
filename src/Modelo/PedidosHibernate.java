package Modelo;

import java.sql.Date;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "pedidos")
public class PedidosHibernate implements Serializable{
	
	@Id
	@Column(name = "id_pedidos", nullable = false)
	private Integer id_odontograma;
	
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	@Column(name = "estado", nullable = false)
	private String estado;

	public PedidosHibernate(Integer id_odontograma, Date fecha, String estado) {
		super();
		this.id_odontograma = id_odontograma;
		this.fecha = fecha;
		this.estado = estado;
	}

	@ManyToOne
    @JoinColumn(name = "usuarios_dni_usuario")
    private UserHibernate usuario_pedido; //Este atributo va a @OneToMany en Cliente

    public UserHibernate getUser() {
        return usuario_pedido;
    }

    public void setUser(UserHibernate usuario_pedido) {
        this.usuario_pedido = usuario_pedido;
    }	
	
}