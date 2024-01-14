package Modelo;

import java.sql.Date;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table(name = "proveedores")
public class ProveedorHibernate implements Serializable{
	
	@Id
	@Column(name = "cif_proveedor", nullable = false)
	private String cif;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "estado", nullable = false)
	private int estado;
	
	@Column(name = "correo", nullable = false)
	private String correo;

	public ProveedorHibernate() {}
	
	public ProveedorHibernate(String cif, String nombre, int estado, String correo) {
		super();
		this.cif = cif;
		this.nombre = nombre;
		this.estado = estado;
		this.correo = correo;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@ManyToMany(mappedBy = "proveedores")
    private List<InventarioHibernate> productos = new ArrayList<InventarioHibernate>();

    public List<InventarioHibernate> getProductos() {
        return productos;
    }

    public void setProductos(List<InventarioHibernate> productos) {
        this.productos = productos;
    }
    
    public void addProducto(InventarioHibernate i)
    {
        this.productos.add(i);
        i.getProveedores().add(this);
    }
}