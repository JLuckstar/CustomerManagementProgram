import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class GestionDeClientes extends JFrame
{
	
	// Variables
	private JTable table;
    private DefaultTableModel model;
    private JTextField txtNombre, txtEmail, txtDireccion;
    
    // Constructor
    public GestionDeClientes()
    {
    	setTitle("Gestión de Clientes");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }
    
    private void initComponents()
    {
    	JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        txtNombre = new JTextField();
        txtEmail = new JTextField();
        txtDireccion = new JTextField();

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);

        JButton btnAdd = new JButton("Añadir");
        JButton btnUpdate = new JButton("Editar");
        JButton btnDelete = new JButton("Borrar");
        JButton btnSave = new JButton("Guardar (Archivo)");

        panelForm.add(btnAdd);
        panelForm.add(btnUpdate);
        panelForm.add(btnDelete);
        panelForm.add(btnSave);

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Dirección"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Eventos
        btnAdd.addActionListener(e -> insertar());
        btnUpdate.addActionListener(e -> actualizar());
        btnDelete.addActionListener(e -> borrar());
        btnSave.addActionListener(e -> guardarEnArchivo());

        table.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
            	// variable
                int fila = table.getSelectedRow();
                
                // Decision
                if (fila >= 0) 
                {
                    txtNombre.setText(model.getValueAt(fila, 1).toString());
                    txtEmail.setText(model.getValueAt(fila, 2).toString());
                    txtDireccion.setText(model.getValueAt(fila, 3).toString());
                }
            }
        });
    }
    
    private void insertar()
    {
    	int id = model.getRowCount() + 1; // auto incremental simulado
        model.addRow(new Object[]{id, txtNombre.getText(), txtEmail.getText(), txtDireccion.getText()});
        limpiarCampos();
    }
    
    private void actualizar()
    {
    	// Variable
    	int fila = table.getSelectedRow();
    	
    	// Decision
        if (fila >= 0) 
        {
            model.setValueAt(txtNombre.getText(), fila, 1);
            model.setValueAt(txtEmail.getText(), fila, 2);
            model.setValueAt(txtDireccion.getText(), fila, 3);
            limpiarCampos();
        }
    }
    
    private void borrar()
    {
    	// Variables
    	int fila = table.getSelectedRow();
    	
    	// Decision
        if (fila >= 0) 
        {
            model.removeRow(fila);
            limpiarCampos();
        }
    }
    
    private void guardarEnArchivo()
    {
    	// Try and Catch
    	 try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) 
    	 {
    		 // Decision
             for (int i = 0; i < model.getRowCount(); i++) 
             {
                 Vector<?> row = model.getDataVector().elementAt(i);
                 writer.write(row.get(0) + "," + row.get(1) + "," + row.get(2) + "," + row.get(3));
                 writer.newLine();
             }
             JOptionPane.showMessageDialog(this, "Datos guardados en clientes.txt");
         } 
    	 catch (IOException e) 
    	 {
             JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage());
         }
    }
    
    private void limpiarCampos()
    {
    	txtNombre.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
    }

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(() -> new GestionDeClientes().setVisible(true));
	}
}