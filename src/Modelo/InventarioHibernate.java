package Modelo;

import java.sql.Date;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table(name = "inventario")
public class InventarioHibernate implements Serializable{
	
	@Id
	@Column(name = "id_producto", nullable = false)
	private Integer id_producto;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	public InventarioHibernate() {
		super();
	}

	public InventarioHibernate(Integer id_producto, String nombre, Integer cantidad) {
		super();
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public Integer getId_producto() {
		return id_producto;
	}

	public void setId_producto(Integer id_producto) {
		this.id_producto = id_producto;
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
	
	@ManyToMany()
    @JoinTable(name = "inventario_has_proveedores",
        joinColumns = {@JoinColumn(name = "inventario_id_producto")},
        inverseJoinColumns = {@JoinColumn(name = "proveedores_cif_proveedor")}
    )
    private List<ProveedorHibernate> proveedores = new ArrayList<ProveedorHibernate>();
    
    public List<ProveedorHibernate> getProveedores() {
    	return proveedores;
    }
    public void addProveedor(ProveedorHibernate p)
    {
        this.proveedores.add(p);
        p.getProductos().add(this);
    }
    public void setProveedores(List<ProveedorHibernate> proveedores) {
        this.proveedores = proveedores;
    }
    
    @ManyToMany()
    @JoinTable(name = "usuarios_has_inventario",
        joinColumns = {@JoinColumn(name = "inventario_id_producto")},
        inverseJoinColumns = {@JoinColumn(name = "usuarios_dni_usuario")}
    )
    private List<UserHibernate> usuarios= new ArrayList<UserHibernate>();
    
    public List<UserHibernate> getUsuarios() {
    	return usuarios;
    }
    public void addUser(UserHibernate p)
    {
        this.usuarios.add(p);
        p.getProductos().add(this);
    }
    public void setUsuarios(List<UserHibernate> usuarios) {
        this.usuarios = usuarios;
    }
    

    @OneToMany(mappedBy = "producto_pedido")
	private List<PedidosHibernate> pedidos;

	public List<PedidosHibernate> getProductos() {
		return pedidos;
	}

	public void addPedido(PedidosHibernate th) {
		if (pedidos == null) {
			pedidos = new ArrayList<>();
			pedidos.add(th);
			th.setProducto(this);
		}
    
   
}
	}