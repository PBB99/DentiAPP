package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
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
	private List<InventarioHibernate>lista;
	private JTable tableStock;
	private LineBorder lb = new LineBorder(new Color(240, 240, 240), 3, true);
	private Font font = new Font("Dialog", Font.BOLD, 15);
	private Color azulito = new Color(148, 220, 219);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the dialog.
	 */
	public MakeDelivery(List<InventarioHibernate>lista) {
		this.lista=lista;
		// -------------------- Conexión ------------------
				this.instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
						.addAnnotatedClass(InventarioHibernate.class).addAnnotatedClass(ProveedorHibernate.class)
						.buildSessionFactory();
				this.session = instancia.openSession();

		setBounds(470, 50,750, 1000);
				getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 728, 800, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Pedir");
				o
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
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
																				loadTableStock(tableStock);
																				getContentPane().add(contentPanel);
	}
	public void loadTableStock(JTable table) {
		String hql2="From InventarioHibernate where cantidad<0";
		Query<InventarioHibernate> consulta2 = session.createQuery(hql2, InventarioHibernate.class);
		List<InventarioHibernate>auxiList=consulta2.getResultList();
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
