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
    private HashMap <Integer,Jugador>hmjug;
    private ArrayList <Temporada>hmtemp;
    private HashMap <Integer,Categoria>hmcat;
    private HashMap <Integer,Equip>hmeqp;
    public Vista_Controlador() throws gestorEquipsException {
        try {    
            this.persistencia = new conexio_BBDD();  
        } catch (gestorEquipsException ex) {
            throw new gestorEquipsException("Error al inicializar la capa de persistencia", ex);
        }
        hmcat=persistencia.carregar_categories();
        hmjug=persistencia.carregar_jugador();
        hmtemp=persistencia.carregar_temporades();
        hmeqp=persistencia.carregar_equips();
        
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
        ((Jugadors_JPanel) panelJugadors).getCercaPerNomButton().addActionListener(e -> buscarPorNombre(hmjug,hmcat));
        ((Jugadors_JPanel) panelJugadors).getCercaPerFechaButton().addActionListener(e -> buscarPorFecha(hmjug,hmcat));
        ((Jugadors_JPanel) panelJugadors).getCercaPerCategoriaButton().addActionListener(e -> buscarPorCategoria(hmjug,hmcat));
        ((Jugadors_JPanel) panelJugadors).getCercaButton().addActionListener(e-> buscarPerNIF(hmjug,hmcat));
        ((EquipsConsulta_JPanel) panelEquips).getCerca().addActionListener(e-> buscarperTemporadaiCategoria(hmeqp));
       fp.temp.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (panelTemporades.isShowing()) {
                    cargarTemporadasEnVista();
                      cargarJugadoresEnVista(hmjug,hmcat);
                    cargarCategoriasEnVista(hmcat);
                    cargarEquiposEnVista(hmeqp);
                }
            }
        });

        
                
                  
       
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

     public void cargarJugadoresEnVista(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
         Jugadors_JPanel jugadorsPanel = (Jugadors_JPanel) getPanelPorNombre("Jugadors");
         if (jugadorsPanel == null) {
             System.err.println("El panel de jugadores no está disponible.");
             return;
         }
         jugadorsPanel.actualizarTabla(jugadores, cat);
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

public void cargarCategoriasEnVista( HashMap<Integer,Categoria> categorias) {
    Jugadors_JPanel panelJugadors = (Jugadors_JPanel) fp.getPanelCentro().getComponent(2);
    panelJugadors.cargarCategorias(categorias);
}


private void buscarPorNombre(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
    String nombre = panelJugadors.getNombreField().getText().toLowerCase();
    HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
    if (nombre.isEmpty()) {
        panelJugadors.actualizarTabla(jugadores, cat);
        return;
    }

    for (Jugador jugador : jugadores.values()) {
        if (jugador.getNomJugador().toLowerCase().contains(nombre)) {
            jugadoresFiltrados.put(jugador.getId(), jugador);
           
        }
    }

    panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
}
 private void buscarPorFecha(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = panelJugadors.getFechaField().getText(); 
            HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
            for (Jugador jugador : jugadores.values()) {
                if (sdf.format(jugador.getDataNaix()).contains(fecha)) {
                    jugadoresFiltrados.put(jugador.getId(), jugador);
                }
            }
            panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panelJugadors, "Formato de fecha no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 private void buscarPorCategoria(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
        String categoriaSeleccionada = (String) panelJugadors.getCategoriaComboBox().getSelectedItem(); 
        HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
        for (Jugador jugador : jugadores.values()) {
            jugador.asignarCategoria(cat);
            if (jugador.getCat().getNom().equals(categoriaSeleccionada)) {
                jugadoresFiltrados.put(jugador.getId(), jugador);
            }
        }
        panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
    }
private void buscarPerNIF(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
    String NIF = panelJugadors.getNifField().getText().toLowerCase();
    HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>(); 
     
    if (NIF.isEmpty()) {
        panelJugadors.actualizarTabla(jugadores, cat);
        return;
    }

    for (Jugador jugador : jugadores.values()) {
        if (jugador.getIdLegal().toLowerCase().contains(NIF)) {
            jugadoresFiltrados.put(jugador.getId(), jugador); 
            
        }
        panelJugadors.actualizarTabla(jugadoresFiltrados, cat);
    }
}
private void cargarTemporadasEnComboBox(EquipsConsulta_JPanel equipsPanel) {
    try {
      
        ArrayList<Temporada> listaTemporadas = persistencia.carregar_temporades();
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
   
        equipsPanel.getTemporada().removeAllItems();

      
        for (Temporada temporada : listaTemporadas) {
             String anyIniStr = sdf.format(temporada.getAnyIni());
             String anyFiStr = sdf.format(temporada.getAnyFi());
            String temporadaStr = anyIniStr + "-" + anyFiStr;
            equipsPanel.getTemporada().addItem(temporadaStr);  
        }
        
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al cargar las temporadas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void cargarCategoriasEnComboBox(EquipsConsulta_JPanel equipsPanel,HashMap <Integer, Categoria> listaCategorias){
    
        equipsPanel.getCategoria().removeAllItems();
        listaCategorias.forEach((key,Categoria)->{
            equipsPanel.getCategoria().addItem(Categoria.getNom());
    });
}
private void cargarEquiposEnVista(HashMap<Integer, Equip> equipos) {
    EquipsConsulta_JPanel equipsPanel = (EquipsConsulta_JPanel) getPanelPorNombre("Equips");
    equipsPanel.actualizarTablaEquipos(equipos);
    cargarTemporadasEnComboBox(equipsPanel);
    cargarCategoriasEnComboBox(equipsPanel,hmcat);
}
private void buscarperTemporadaiCategoria(HashMap<Integer, Equip> equipos) {
    EquipsConsulta_JPanel equipsPanel = (EquipsConsulta_JPanel) getPanelPorNombre("Equips");

    String temporadaSeleccionada = (String) equipsPanel.getTemporada().getSelectedItem();
    String categoriaSeleccionada = (String) equipsPanel.getCategoria().getSelectedItem();
    
    try {
 
        String dosPrimerosDigitosTemporada = temporadaSeleccionada.substring(0, 2);
        HashMap<Integer, Equip> equiposFiltrados = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yy"); 
        for (Equip equip : equipos.values()) {
            String tempEnYY = sdf.format(equip.getTemp().getAnyIni());
            if (equip.getCat().getNom().equals(categoriaSeleccionada) &&
                dosPrimerosDigitosTemporada.equals(tempEnYY)) {
                System.out.println(""+equip.getCat().getNom());
                equiposFiltrados.put(equip.getIdEq(), equip);
            }
        }
        equipsPanel.actualizarTablaEquipos(equiposFiltrados);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(fp, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
