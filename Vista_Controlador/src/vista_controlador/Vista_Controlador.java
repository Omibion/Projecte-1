package vista_controlador;

import Vista.FRameLoggin;
import Vista.FramePrincipal;
import Vista.*;
import david.milaifontanals.org.Categoria;
import david.milaifontanals.org.Equip;
import david.milaifontanals.org.Interficie_persistencia;
import david.milaifontanals.org.Jugador;
import david.milaifontanals.org.Temporada;
import david.milaifontanals.org.conexio_BBDD;
import david.milaifontanals.org.gestorEquipsException;
import david.milaifontanals.org.usuari;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Vista_Controlador implements ActionListener {
    private FRameLoggin fl;
    private FramePrincipal fp;
    private Interficie_persistencia persistencia;
    private Jugadors_JPanel panelJugadors;
    public Vista_Controlador() throws gestorEquipsException {
        try {    
            this.persistencia = new conexio_BBDD();  
        } catch (gestorEquipsException ex) {
            throw new gestorEquipsException("Error al inicializar la capa de persistencia", ex);
        }
        this.fl = new FRameLoggin();
        this.fp = new FramePrincipal();

        JPanel panelCentro = fp.getPanelCentro();
        CardLayout cardLayout = new CardLayout();
        panelCentro.setLayout(cardLayout);

        JPanel panelTemporades = new Temporades_JPanel();
        panelTemporades.setName("Temporades");
        JPanel panelEquips = new EquipsConsulta_JPanel();
        panelEquips.setName("Equips");
        panelJugadors = new Jugadors_JPanel(); 
        panelJugadors.setName("Jugadors");
        JPanel panelficha = new FitxaJugador_JPanel();

        panelCentro.add(panelTemporades, "Temporades");
        panelCentro.add(panelEquips, "Equips");
        panelCentro.add(panelJugadors, "Jugadors");

        fp.temp.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Temporades"));
        fp.equip.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Equips"));
        fp.jugadors.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Jugadors"));
 

        this.fl.getLoginButton().addActionListener(this);
        this.fl.setVisible(true);
        ((Temporades_JPanel) panelTemporades).getBtnCrear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                crearTemporada((Temporades_JPanel) panelTemporades);
            }
        });
((Temporades_JPanel) panelTemporades).getDesarButton().addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        desarCommit(); 
    }
});

((Temporades_JPanel) panelTemporades).getBtnEsborrar().addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        eliminarTemporadasSeleccionadas((Temporades_JPanel) panelTemporades);
    }
});
        ((Jugadors_JPanel) panelJugadors).getCercaPerNomButton().addActionListener(e -> buscarPorNombre());
        ((Jugadors_JPanel) panelJugadors).getCercaPerFechaButton().addActionListener(e -> buscarPorFecha());
        ((Jugadors_JPanel) panelJugadors).getCercaPerCategoriaButton().addActionListener(e -> buscarPorCategoria());
        ((Jugadors_JPanel) panelJugadors).getCercaButton().addActionListener(e-> buscarPerNIF());

       fp.temp.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (panelTemporades.isShowing()) {
                    cargarTemporadasEnVista();
                }
            }
        });

        
                
                    cargarJugadoresEnVista();
                    cargarCategoriasEnVista();
                    cargarEquiposEnVista();
       
    }

    private void desarCommit() {
    try {
        
        persistencia.commit();

        JOptionPane.showMessageDialog(fp, "Cambios guardados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    }  catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(fp, "Error al hacer commit: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fl.getLoginButton()) {
            String usuario = fl.getUsuari().getText();
            String password = new String(fl.getContrasenya().getPassword()); 
           
            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(fl, "Por favor, ingrese ambos campos", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            try {
                password = persistencia.encriptar_contrasenya(password);
            } catch (gestorEquipsException ex) {
                ex.printStackTrace();
            }

            try {
                usuari u = persistencia.obtenir_usuari(usuario);
                
                if (u != null && u.getContrasenya().equals(password)) {
                    fl.setVisible(false);
                    fp.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(fl, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    fl.getUsuari().setText("");  
                    fl.getContrasenya().setText(""); 
                    fl.getUsuari().requestFocus(); 
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(fl, "Error al validar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                fl.getUsuari().setText(""); 
                fl.getContrasenya().setText(""); 
                fl.getUsuari().requestFocus();  
            }
        }
    }

    private void cargarTemporadasEnVista() {
        try {
            ArrayList<Temporada> listaTemporadas = persistencia.carregar_temporades();
            Temporades_JPanel panelTemporades = (Temporades_JPanel) fp.getPanelCentro().getComponent(0); 
            panelTemporades.cargarTemporadas(listaTemporadas);
        } catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(fp, "Error al cargar las temporadas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     public void cargarJugadoresEnVista() {
        try {
            HashMap<Integer, Jugador> jugadores = persistencia.carregar_jugador();
            
            Jugadors_JPanel jugadorsPanel = (Jugadors_JPanel) getPanelPorNombre("Jugadors"); 
            ArrayList<Categoria> cat = persistencia.carregar_categories();
            if (jugadorsPanel == null) {
                System.err.println("El panel de jugadores no está disponible.");
                return; 
            }
            jugadorsPanel.actualizarTabla(jugadores, cat);
        } catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(fp, "Error al cargar jugadores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public JPanel getPanelPorNombre(String nombrePanel) {
        CardLayout layout = (CardLayout) fp.getPanelCentro().getLayout();
        for (Component comp : fp.getPanelCentro().getComponents()) {
            if (fp.getPanelCentro().isAncestorOf(comp) && nombrePanel.equals(comp.getName())) {
                return (JPanel) comp;
            }
        }
        return null;
    }

   private void crearTemporada(Temporades_JPanel panelTemporadesView) {

    String anySeleccionadoStr = (String) panelTemporadesView.getComboAnyInici().getSelectedItem();

    int anySeleccionado = Integer.parseInt(anySeleccionadoStr);

    int anySiguiente = anySeleccionado + 1;

    java.sql.Date fechaInicio = Date.valueOf(anySeleccionado + "-01-01");
    java.sql.Date fechaFin = Date.valueOf(anySiguiente + "-12-31");

    Temporada nuevaTemporada = new Temporada(fechaInicio, fechaFin);

    try {
       
        if (persistencia.afegir_temporada(fechaInicio, fechaFin)) {
         
            ArrayList<Temporada> listaTemporadas = persistencia.carregar_temporades();

           
            Temporades_JPanel panelTemporades = (Temporades_JPanel) fp.getPanelCentro().getComponent(0);
            panelTemporades.cargarTemporadas(listaTemporadas);  

            JOptionPane.showMessageDialog(fp, "Temporada creada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(fp, "No se pudo crear la temporada", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al crear la temporada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void eliminarTemporadasSeleccionadas(Temporades_JPanel panelTemporadesView) {
    ArrayList<Temporada> temporadasSeleccionadas = null;
    try {
        temporadasSeleccionadas = panelTemporadesView.getTemporadasSeleccionadas(persistencia.carregar_temporades());
    } catch (gestorEquipsException ex) {
        Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (temporadasSeleccionadas == null || temporadasSeleccionadas.isEmpty()) {
        JOptionPane.showMessageDialog(fp, "No se han seleccionado temporadas para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        for (Temporada temporada : temporadasSeleccionadas) {
            boolean eliminado = persistencia.eliminar_temporada(temporada);
            if (eliminado) {
                JOptionPane.showMessageDialog(fp, "Temporada eliminada: " + temporada.getIdTemp(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        ArrayList<Temporada> listaTemporadasActualizada = persistencia.carregar_temporades();
        panelTemporadesView.cargarTemporadas(listaTemporadasActualizada);
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al eliminar temporadas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void cargarCategoriasEnVista() {
    try {
        ArrayList<Categoria> categorias = persistencia.carregar_categories();

        Jugadors_JPanel panelJugadors = (Jugadors_JPanel) fp.getPanelCentro().getComponent(2);
        panelJugadors.cargarCategorias(categorias);
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al cargar las categorías: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void buscarPorNombre() {
    String nombre = panelJugadors.getNombreField().getText().toLowerCase();
    HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>(); 
    HashMap<Integer, Jugador> jugadores = null; 
    ArrayList<Categoria> cat=null;
        try {
            cat = persistencia.carregar_categories();
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    try {
        jugadores = persistencia.carregar_jugador(); 
    } catch (gestorEquipsException ex) {
        Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
    }

    
    if (nombre.isEmpty()) {
        panelJugadors.actualizarTabla(jugadores, cat);
        return;
    }

    for (Jugador jugador : jugadores.values()) {
        if (jugador.getNomJugador().toLowerCase().contains(nombre)) {
            jugadoresFiltrados.put(jugador.getId(), jugador); // Si contiene el texto, lo agrega al map filtrado
            System.out.println("Jugador encontrado: " + jugador.getNomJugador());
        }
    }

    panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
}
 private void buscarPorFecha() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = panelJugadors.getFechaField().getText(); 
            HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
            HashMap<Integer, Jugador> jugadores = null;
            ArrayList<Categoria> cat=null;
            cat=persistencia.carregar_categories();
            jugadores= persistencia.carregar_jugador();
           
            for (Jugador jugador : jugadores.values()) {
                if (sdf.format(jugador.getDataNaix()).contains(fecha)) {
                    jugadoresFiltrados.put(jugador.getId(), jugador);
                }
            }

            // Actualizar la tabla con los jugadores filtrados
            panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelJugadors, "Formato de fecha no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 private void buscarPorCategoria() {
        String categoriaSeleccionada = (String) panelJugadors.getCategoriaComboBox().getSelectedItem(); 
        HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
        HashMap<Integer, Jugador> jugadores = null;
            ArrayList<Categoria> cat=null;
        try {
            cat=persistencia.carregar_categories();
            jugadores= persistencia.carregar_jugador();
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        for (Jugador jugador : jugadores.values()) {
            jugador.asignarCategoria(cat);
            if (jugador.getCat().getNom().equals(categoriaSeleccionada)) {
                jugadoresFiltrados.put(jugador.getId(), jugador);
            }
        }

      
        panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
    }
private void buscarPerNIF() {
    String NIF = panelJugadors.getNifField().getText().toLowerCase();
    HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>(); 
    HashMap<Integer, Jugador> jugadores = null; 
    ArrayList<Categoria> cat=null;
        try {
            cat = persistencia.carregar_categories();
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    try {
        jugadores = persistencia.carregar_jugador(); 
    } catch (gestorEquipsException ex) {
        Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
    }

    
    if (NIF.isEmpty()) {
        panelJugadors.actualizarTabla(jugadores, cat);
        return;
    }

    for (Jugador jugador : jugadores.values()) {
        if (jugador.getIdLegal().toLowerCase().contains(NIF)) {
            jugadoresFiltrados.put(jugador.getId(), jugador); // Si contiene el texto, lo agrega al map filtrado
            System.out.println("Jugador encontrado: " + jugador.getNomJugador());
        }
        panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
    }
}

private void cargarEquiposEnVista() {
    try {
        HashMap<Integer, Equip> equipos = persistencia.carregar_equips(); 
        
        EquipsConsulta_JPanel equipsPanel = (EquipsConsulta_JPanel) getPanelPorNombre("Equips"); // Obtener el panel de equipos
        
        if (equipsPanel != null) {
            equipsPanel.actualizarTabla(equipos); 
        } else {
            System.err.println("El panel de equipos no está disponible.");
        }
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al cargar los equipos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        try {
            new Vista_Controlador();
        } catch (gestorEquipsException ex) {
            ex.printStackTrace(); 
        }
    }
}
