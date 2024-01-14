package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Modelo.InventarioHibernate;
import Modelo.ProveedorHibernate;
import Vista.AdminStock.Renderer;

public class ChangeProducto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable proveedores;
	private JTextField tfNombre;
	private JTextField tfCantidad;
	private SessionFactory instancia;
	private Session miSesion;
	private InventarioHibernate ph;
	private String selectedProveedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChangeProducto dialog = new ChangeProducto(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChangeProducto(SessionFactory instancia, InventarioHibernate ph) {
		this.instancia = instancia;
		// this.miSesion = instancia.openSession();
		this.ph = ph;

		setModal(true);
		setContentPane(contentPanel);

		// -----------------------COMPONENTES-------------------

		setBounds(0, 0, 460, 550);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 50, 200, 20);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfNombre.setBounds(150, 50, 250, 25);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(30, 100, 200, 20);
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tfCantidad = new JTextField();
		tfCantidad.setColumns(10);
		tfCantidad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfCantidad.setBounds(150, 100, 250, 25);

		JLabel lblProveedores = new JLabel("Proveedores:");
		lblProveedores.setBounds(30, 150, 200, 20);
		lblProveedores.setFont(new Font("Tahoma", Font.PLAIN, 18));

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableProveedor = new JScrollPane();
		menuTableProveedor.setBorder(BorderFactory.createEmptyBorder());
		menuTableProveedor.setBounds(30, 200, 400, 200);
		menuTableProveedor.setBackground(new Color(148, 220, 219));

		// Tabla
		proveedores = new JTable();
		proveedores.setShowVerticalLines(false);
		proveedores.setShowHorizontalLines(false);
		proveedores.setCellSelectionEnabled(true);
		proveedores.setBounds(0, 0, 400, 200);
		proveedores.setBackground(new Color(250, 250, 250));
		proveedores.setShowGrid(false);
		proveedores.setBorder(null);
		proveedores.setRowHeight(35);
		proveedores.getTableHeader().setBackground(new Color(250, 250, 250));
		proveedores.getTableHeader().setBorder(new LineBorder(new Color(250, 250, 250)));
		loadTable(proveedores);

		JButton okButton = new JButton("OK");
		okButton.setBounds(70, 450, 125, 25);
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		okButton.setMnemonic(KeyEvent.VK_ENTER);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(265, 450, 125, 25);
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				setVisible(false);
				// miSesion.close();
				ChangeProducto.this.dispatchEvent(new WindowEvent(ChangeProducto.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setActionCommand("Cancel");

		// --------------------------LOGICA--------------------------

		// Acción de guardar
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// variable de control
				boolean isValid = true;

				// Nos aseguramos que rellena todos los campos
				if ((tfCantidad.getText().isBlank() || tfNombre.getText().isBlank())) {
					JOptionPane.showMessageDialog(contentPanel, "Debe rellenar todos los campos", "Error",
							JOptionPane.ERROR_MESSAGE);
					isValid = false;
				}

				if (isValid) {
					// Modifica el producto
					ph.setNombre(tfNombre.getText());

					try { // Controla que meta un número
						int numero = Integer.parseInt(tfCantidad.getText());

						// Controla que sea positivo
						if (numero < 0) {
							throw new Exception();
						}

						// Actualiza el número
						ph.setCantidad(numero);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPanel, "Valor no valido para el campo precio", "Error",
								JOptionPane.ERROR_MESSAGE);
						isValid = false;
					}

				}

				// Realizamos la operación de update
				if (isValid) {
					miSesion.beginTransaction();
					miSesion.update(ph);
					miSesion.getTransaction().commit();

					// Cerramos
					dispose();
					// miSesion.close();
				}
			}
		});

		// Acción de seleccionar elemento de la tabla Proveedor
		proveedores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				if (evnt.getClickCount() == 1) {

					// Seleccionar row
					proveedores.addColumnSelectionInterval(0, 1);

					// Cambios en la selección
					proveedores.setColumnSelectionAllowed(false);
					proveedores.setCellSelectionEnabled(false);
					proveedores.setColumnSelectionAllowed(true);
					proveedores.setCellSelectionEnabled(true);

					// selección de la especialidad
					selectedProveedor = proveedores
							.getValueAt(proveedores.getSelectedRow(), proveedores.getSelectedColumn()).toString();

					System.out.println("CIF " + selectedProveedor);
				}
			}
		});

		// ----------------------------ADICIONES-----------------------------

		contentPanel.add(lblProveedores);
		contentPanel.add(tfCantidad);
		contentPanel.add(lblNombre);
		contentPanel.add(okButton);
		contentPanel.add(cancelButton);
		contentPanel.add(lblCantidad);
		contentPanel.add(tfNombre);
		contentPanel.add(menuTableProveedor);
		menuTableProveedor.add(proveedores);
		menuTableProveedor.setViewportView(proveedores);

	}

	// ----------------------------Métodos-----------------------------
	// -------------------- Métodos y Funciones --------------------
	// Poner un cif
	public void setTextCantidad(String cantidad) {
		tfCantidad.setText(cantidad);
	}

	// Poner un nombre
	public void setTextNombre(String nombre) {
		tfNombre.setText(nombre);
	}

	// Pasar la sesion
	public void setSession(Session session) {
		miSesion = session;
	}

	public void loadTable(JTable table) {
		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "CIF", "Nombre" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(model);
		JTableHeader header = table.getTableHeader();
		if (ph.getProveedores().size() < 6) {
			model.setRowCount(5);
		} else {
			model.setRowCount(ph.getProveedores().size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (ProveedorHibernate producto : ph.getProveedores()) {
			model.setValueAt(producto.getCif(), fila, columna);
			model.setValueAt(producto.getNombre(), fila, columna + 1);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);
	}

	// Clase para cambiar el color de las filas
	public class Renderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// Evalua en que fila esta
			if (row % 2 == 0) {
				setBackground(new Color(220, 220, 220));
			} else {
				setBackground(new Color(250, 250, 250));
			}

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}
}
