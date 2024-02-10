package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Modelo.InventarioHibernate;
import Modelo.PedidosHibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.InventarioHibernate;
import Modelo.ProveedorHibernate;
import Otros.RoundedPanel;
import Vista.AdminStock.Renderer;

public class MakeDelivery extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private SessionFactory instancia;
	private Session session;
	private List<InventarioHibernate> pedidoList;
	private JTable tableStock;
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private Color azulito = new Color(148, 220, 219);
	private int lastIdpedido;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public MakeDelivery() {
		
		// -------------------- Conexión ------------------
		this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(InventarioHibernate.class).addAnnotatedClass(ProveedorHibernate.class).addAnnotatedClass(PedidosHibernate.class)
				.buildSessionFactory();
		this.session = instancia.openSession();
		String hql4="From PedidosHibernate";
		Query<PedidosHibernate> conPedido=session.createQuery(hql4, PedidosHibernate.class);
		List<PedidosHibernate>auxPed=conPedido.getResultList();
		if(auxPed.isEmpty()) {
			lastIdpedido=1;
		}else {
			lastIdpedido=auxPed.getLast().getId_pedido();
		}
		
		setBounds(470, 50, 750, 1000);
		getContentPane().setLayout(null);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 800, 800, 33);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		JButton okButton = new JButton("Pedir");

		buttonPane.add(okButton);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setModal(false);
				dispose();
			}
		});
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				session.beginTransaction();							
				java.util.Date fecha = new Date();
				java.sql.Date dia = new java.sql.Date(fecha.getTime());
				
				
				
				for(InventarioHibernate y:pedidoList) {
					PedidosHibernate pedido=new PedidosHibernate(dia);
					y.addPedido(pedido);
					pedido.setProducto(y);
					session.update(y);
					session.saveOrUpdate(pedido);
				}
				
				session.getTransaction().commit();
				setModal(false);
				dispose();
				
			}
		});

		contentPanel.setBounds(0, 0, 784, 728);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// Inventario

		// Panel fondo de stock
		JPanel panelBackStock = new RoundedPanel(50, azulito);
		panelBackStock.setBounds(5, 5, 660, 785);
		panelBackStock.setOpaque(false);
		panelBackStock.setLayout(null);
		getContentPane().add(panelBackStock);

		// Panel titulo de stock
		JPanel panelTitleStock = new JPanel();
		panelTitleStock.setBounds(25, 15, 610, 745);
		panelTitleStock.setBorder(new TitledBorder(lb, "  Inventario  ", TitledBorder.LEFT, TitledBorder.TOP, font,
				new Color(51, 51, 51)));
		panelTitleStock.setOpaque(false);
		panelTitleStock.setLayout(null);
		panelBackStock.add(panelTitleStock);

		// Panel del Menú
		JPanel menuStock = new JPanel();
		menuStock.setBackground(new Color(148, 220, 219));
		menuStock.setBounds(30, 50, 600, 705);
		menuStock.setLayout(null);
		panelBackStock.add(menuStock);

		// ScrollPane para cargar la talbla inventario
		JScrollPane menuTableStock = new JScrollPane();
		menuTableStock.setBorder(BorderFactory.createEmptyBorder());
		menuTableStock.setBounds(0, 30, 600, 675);
		menuTableStock.setBackground(new Color(148, 220, 219));
		menuStock.add(menuTableStock);

		// Tabla
		tableStock = new JTable();
		tableStock.setShowVerticalLines(false);
		tableStock.setShowHorizontalLines(false);
		tableStock.setCellSelectionEnabled(true);
		tableStock.setBounds(0, 0, 600, 675);
		tableStock.setBackground(new Color(250, 250, 250));
		tableStock.setSelectionBackground(new Color(148, 220, 219));
		tableStock.setShowGrid(false);
		tableStock.setBorder(null);
		tableStock.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.setRowHeight(35);
		tableStock.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
		tableStock.getTableHeader().setBackground(new Color(148, 220, 219));
		tableStock.getTableHeader().setBorder(new LineBorder(new Color(148, 220, 219)));
		menuTableStock.add(tableStock);
		menuTableStock.setViewportView(tableStock);
		pedidoList=loadTableStock(tableStock);
		getContentPane().add(contentPanel);
		
		tableStock.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evnt) {
				InventarioHibernate solicitado = new InventarioHibernate();
				// Seleccionar row
				tableStock.addColumnSelectionInterval(0, 1);

				// Cambios en la selección
				tableStock.setColumnSelectionAllowed(false);
				tableStock.setCellSelectionEnabled(false);
				tableStock.setColumnSelectionAllowed(true);
				tableStock.setCellSelectionEnabled(true);

				// selección del poroducto
				String selectedProduct = tableStock.getValueAt(tableStock.getSelectedRow(), tableStock.getSelectedColumn())
						.toString();
				// recorrer y encontrar el id, a traves de for each con una lista local que se
				// carga cada vez que cargamos la lista por que buscar por nombre puede dar
				// problemas
				int resultado;
				do {
					resultado = mostrarInputDialog(selectedProduct);
				}while(resultado<0);
				
				tableStock.setValueAt(resultado,tableStock.getSelectedRow(),tableStock.getSelectedColumn()+1);
				pedidoList.get(tableStock.getSelectedRow()).setCantidad(resultado);
				
			}
		});
	}

	public List<InventarioHibernate> loadTableStock(JTable table) {
		String hql2 = "From InventarioHibernate where cantidad<0";
		Query<InventarioHibernate> consulta2 = session.createQuery(hql2, InventarioHibernate.class);
		List<InventarioHibernate> auxiList = consulta2.getResultList();
		// Prepara la tabla
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "Producto", "Cantidad" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		table.setModel(model);
		JTableHeader header = table.getTableHeader();
		if (auxiList.size() < 20) {
			model.setRowCount(19);
		} else {
			model.setRowCount(auxiList.size());
		}
		int fila = 0, columna = 0;

		// Carga los datos
		for (InventarioHibernate producto : auxiList) {
			model.setValueAt(producto.getNombre(), fila, columna);
			model.setValueAt(producto.getCantidad(), fila, columna + 1);
			fila++;
		}

		// Se alinea el texto de las columnas
		Renderer tcr = new Renderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		table.setDefaultRenderer(Object.class, tcr);

		int[] anchos = { 400, 200 };
		for (int i = 0; i < 2; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
		}
		return auxiList;

	}
	public static int mostrarInputDialog(String producto) {
		// Crear un cuadro de diálogo de entrada
		int numero = 0;
		String input = JOptionPane.showInputDialog("Ingrese cuantas cantidades vas a pedir : " + producto);

		try {
			// Intenta convertir la entrada a un entero
			numero = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			// En caso de error, muestra un mensaje y vuelve a llamar al método
			JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error",
					JOptionPane.ERROR_MESSAGE);

		}
		return numero;
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
