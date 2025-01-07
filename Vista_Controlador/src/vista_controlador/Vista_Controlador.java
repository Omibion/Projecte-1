package vista_controlador;

import Vista.FRameLoggin;
import Vista.FramePrincipal;
import Vista.*;
import david.milaifontanals.org.Categoria;
import david.milaifontanals.org.Equip;
import david.milaifontanals.org.Interficie_persistencia;
import david.milaifontanals.org.Jugador;
import david.milaifontanals.org.Membre;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;


public class Vista_Controlador implements ActionListener {
    static private String nomClassePersistencia = null;
    private FRameLoggin fl;
    private FramePrincipal fp;
    private Interficie_persistencia persistencia=null;
    private Jugadors_JPanel panelJugadors;
    private HashMap <Integer,Jugador>hmjug;
    private ArrayList <Temporada>hmtemp;
    private HashMap <Integer,Categoria>hmcat;
    private HashMap <Integer,Equip>hmeqp;
    private HashMap <String,Membre> hmmem;
    private EquipPanel panelEq;
    private CreaEquip_JPanel creaEq;
    private AfegirJugadorsEquip_JPanel afgjugadors;
    private FitxaJugador_JPanel fitxaJug;
    ArrayList <Membre> jugAfegir = new ArrayList();
    CardLayout cardLayout;
    JPanel panelCentro ;
    JPanel panelEquips;
    private boolean isedicio;
    private Equip equipoEnEdicion;
    public Vista_Controlador() throws gestorEquipsException {
        try {    
            persistencia = (Interficie_persistencia) Class.forName(nomClassePersistencia).newInstance(); 
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        hmcat=persistencia.carregar_categories();
        hmjug=persistencia.carregar_jugador();
        hmtemp=persistencia.carregar_temporades();
        hmeqp=persistencia.carregar_equips();
        hmmem=persistencia.carregar_membres();
        this.fl = new FRameLoggin();
        this.fp = new FramePrincipal();
        panelCentro = fp.getPanelCentro();
        cardLayout = new CardLayout();
        panelCentro.setLayout(cardLayout);
        JPanel panelTemporades = new Temporades_JPanel();
        panelTemporades.setName("Temporades");
        panelEquips= new EquipsConsulta_JPanel();
        panelEquips.setName("Equips");
        panelJugadors = new Jugadors_JPanel(); 
        panelJugadors.setName("Jugadors");
        fitxaJug=new FitxaJugador_JPanel();
        JPanel panelficha = new FitxaJugador_JPanel();
        creaEq =new CreaEquip_JPanel();
        creaEq.setName("creaEq");
        panelCentro.add(panelTemporades, "Temporades");
        panelCentro.add(panelEquips, "Equips");
        panelCentro.add(panelJugadors, "Jugadors");
        panelCentro.add(creaEq,"creaEq");
        panelCentro.add(fitxaJug,"fitxaJug");
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
        ((Jugadors_JPanel) panelJugadors).getEsborraSeleccionat().addActionListener(e -> borrarjugadorsseleccionats());
         panelJugadors.getAfegirJugador().addActionListener(e->abrirFichaVacia());
        ((EquipsConsulta_JPanel) panelEquips).getCerca().addActionListener(e-> buscarperTemporadaiCategoria(hmeqp));
        ((EquipsConsulta_JPanel)panelEquips).getBorraEquip().addActionListener(e->borrarEquip(hmeqp,hmmem));
        ((EquipsConsulta_JPanel)panelEquips).getDesa().addActionListener(e->desarCommit());
        ((EquipsConsulta_JPanel)panelEquips).getCreaEquips().addActionListener(e->abrirCreacionEq());
        fitxaJug.getBtnDesa().addActionListener(e->crearJugador());
        fitxaJug.getBtnCanviarFoto().addActionListener(e -> seleccionarFoto());
        if(!isedicio){
        ((CreaEquip_JPanel)creaEq).getGuarda().addActionListener(e -> {
    if (isedicio) {
        editarEquip(equipoEnEdicion);
        ((EquipsConsulta_JPanel) panelEquips).actualizarTablaEquipos(hmeqp);
    } else {
        crearEquip();
        ((EquipsConsulta_JPanel) panelEquips).actualizarTablaEquipos(hmeqp);
    }
});
        fitxaJug.getBtnTorna().addActionListener(e->cardLayout.show(panelCentro, "Jugadors"));
        ((CreaEquip_JPanel)creaEq).getTorna().addActionListener(e->cardLayout.show(panelCentro, "Equips"));
 ((EquipsConsulta_JPanel) panelEquips).getTaulaEquips().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JTable table = (JTable) e.getSource();
            int row = table.getSelectedRow(); 
            if (row >= 0) { 
                int idEquip = (Integer) table.getValueAt(row, 0); 
                
                if (hmeqp.containsKey(idEquip)) {
                    
                    Equip equipSeleccionado = hmeqp.get(idEquip);
                    System.out.println("Idequip"+equipSeleccionado.getIdEq());
                    equipoEnEdicion=equipSeleccionado;
                    EquipPanel equipPanel = new EquipPanel(equipSeleccionado);
                    afgjugadors = new AfegirJugadorsEquip_JPanel(equipSeleccionado);
                    equipPanel.setName("EquipPanel_" + idEquip);
                    panelCentro.add(equipPanel, "EquipPanel_" + idEquip);
                    ArrayList <Membre> mem =null;
                    CardLayout cardLayout = (CardLayout) panelCentro.getLayout();
                    cardLayout.show(panelCentro, "EquipPanel_" + idEquip);
                    mem=equipPanel.cargarJugadores(idEquip, hmmem);
                    equipPanel.getBotonVolver().addActionListener((ActionEvent evt) -> cardLayout.show(panelCentro, "Equips"));
                    panelCentro.add(afgjugadors,"AfgJug");
                    equipPanel.getBotonAgregar().addActionListener((ActionEvent evt)-> cardLayout.show(panelCentro, "AfgJug"));
                    afgjugadors.getTornaButton().addActionListener((ActionEvent evt) -> {
                    equipPanel.cargarJugadores(idEquip, hmmem);
                    cardLayout.show(panelCentro, "EquipPanel_" + idEquip);
});
                    Equip eq= hmeqp.get(idEquip);
                    equipPanel.getBotonEdita().addActionListener((ActionEvent evt)-> abrirEdicionEq(eq));
                    cargarJugadoresPorCategoria(equipSeleccionado,hmjug,idEquip,mem);
                    afgjugadors.getAfegeixSeleccionats().addActionListener((ActionEvent evt) -> {
                        try {
                            afejirJugadorsSeleccionats(idEquip);
                            equipPanel.cargarJugadores(idEquip, hmmem);
                            cardLayout.show(panelCentro, "EquipPanel_" + idEquip);
                        } catch (gestorEquipsException ex) {
                            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    equipPanel.getBotonEliminar().addActionListener((ActionEvent evt) -> {
    try {
        borrarJugadorsSeleccionats(equipPanel, idEquip);
    } catch (gestorEquipsException ex) {
        Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
    }
                
});
                        equipPanel.getBotonDesa().addActionListener((ActionEvent evt)->{
                       desaEquips(equipPanel,idEquip);
                    });
                } else {
                    JOptionPane.showMessageDialog(fp, "L'equip seleccionat no existeix", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
});
 panelJugadors.getExportarJugadors().addActionListener(e->exportarJugadoresCSV( hmjug));
 ((Jugadors_JPanel) panelJugadors).getTaulaJugadors().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {  
            JTable table = (JTable) e.getSource();
            int row = table.getSelectedRow();
            if (row >= 0) {
               
                int idJugador = (Integer) table.getValueAt(row, 5);  

                if (hmjug.containsKey(idJugador)) {
                    Jugador jugadorSeleccionado = hmjug.get(idJugador);
                    System.out.println("ID Jugador: " + jugadorSeleccionado.getId());
                    fitxaJug.setName("fitxaJug" + idJugador);  
                    panelCentro.add(fitxaJug, "fitxaJug"+idJugador);
                    cardLayout.show(panelCentro, "fitxaJug"+idJugador);    
                    omplirFitxaJugador(jugadorSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(fp, "El jugador seleccionat no existeix", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
});

       fp.temp.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (panelTemporades.isShowing()) {
                    cargarTemporadasEnVista();
                      cargarJugadoresEnVista(hmjug,hmcat);
                    cargarCategoriasEnVista(hmcat);
                    cargarCategoriasEnCreaEquip(hmcat);
                    cargaTemporadasEnCreaEquip(hmtemp);
                    cargarEquiposEnVista(hmeqp);
                }
            }
        });     
    }
}
    private void desarCommit() {
    try {
        
        persistencia.commit();

        JOptionPane.showMessageDialog(fp, "Canvis desats correctament,", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    }  catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(fp, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fl.getLoginButton()) {
            String usuario = fl.getUsuari().getText();
            String password = new String(fl.getContrasenya().getPassword()); 
           
            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(fl, "Ingresa els dos camps siusplau.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(fl, "Usuari o contrasenya incorrectes.", "Error", JOptionPane.ERROR_MESSAGE);
                    fl.getUsuari().setText("");  
                    fl.getContrasenya().setText(""); 
                    fl.getUsuari().requestFocus(); 
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(fl, "Error al validar l'usuari: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(fp, "Error al carregar les temporades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     public void cargarJugadoresEnVista(HashMap<Integer, Jugador> jugadores,HashMap<Integer,Categoria> cat) {
         Jugadors_JPanel jugadorsPanel = (Jugadors_JPanel) getPanelPorNombre("Jugadors");
         if (jugadorsPanel == null) {
             
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

           
        } else {
            JOptionPane.showMessageDialog(fp, "No s'ha pogut crear la temporada", "Error", JOptionPane.ERROR_MESSAGE);
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
        JOptionPane.showMessageDialog(fp, "Error al eliminar temporades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public void cargarCategoriasEnVista( HashMap<Integer,Categoria> categorias) {
    Jugadors_JPanel panelJugadors = (Jugadors_JPanel) fp.getPanelCentro().getComponent(2);
    panelJugadors.cargarCategorias(categorias);
}
public void cargarCategoriasEnCreaEquip( HashMap<Integer,Categoria> categorias) {
    CreaEquip_JPanel panelEquips = (CreaEquip_JPanel) fp.getPanelCentro().getComponent(3);
    panelEquips.cargarCategorias(categorias);
}
public void cargaTemporadasEnCreaEquip( ArrayList<Temporada> temporadas) {
    CreaEquip_JPanel panelEquips = (CreaEquip_JPanel) fp.getPanelCentro().getComponent(3);
    panelEquips.cargarTemporadas(temporadas);
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
            JOptionPane.showMessageDialog(panelJugadors, "Format de data no válid.", "Error", JOptionPane.ERROR_MESSAGE);
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
        JOptionPane.showMessageDialog(fp, "Error al carregar les temporades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                equiposFiltrados.put(equip.getIdEq(), equip);
            }
        }
        equipsPanel.actualizarTablaEquipos(equiposFiltrados);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(fp, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void borrarEquip(HashMap<Integer, Equip> equipos, HashMap<String, Membre> membres) {
    EquipsConsulta_JPanel equipsPanel = (EquipsConsulta_JPanel) getPanelPorNombre("Equips");
    TableModel modelo = equipsPanel.getTaulaEquips().getModel();
    int rowCount = modelo.getRowCount(); 
    ArrayList<Integer> ideqs = new ArrayList<>();

    for (int i = 0; i < rowCount; i++) {
        Object seleccionat = modelo.getValueAt(i, 4); 
        if ((Boolean) seleccionat) { 
            Object valor = modelo.getValueAt(i, 0); 
            ideqs.add((Integer) valor); 
        }
    }
    if (ideqs.isEmpty()) {
        
        return;
    }
    int confirm = JOptionPane.showConfirmDialog(fp, "Segur que vols borrar els equips sel·leccionats?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try {
           
            for (Integer idEquip : ideqs) {
                Equip equipToDelete = equipos.get(idEquip);
                if (equipToDelete != null) {
                    
                    if (persistencia.eliminar_equip(equipToDelete)) {
                        equipos.remove(idEquip); 
                    }
                }
            }
            JOptionPane.showMessageDialog(fp, "Equipos borrats correctament.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarEquiposEnVista(equipos); 
        } catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(fp, "Error al borrar els equips: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public void cargarJugadoresPorCategoria(Equip equipSeleccionado, HashMap<Integer, Jugador> jugadores, int idEquipoSeleccionado, ArrayList<Membre> membresDelEquipo) {

    Categoria categoriaEquipo = equipSeleccionado.getCat();
    HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();

    for (Jugador jugador : jugadores.values()) {
        if (jugador.getCat() != null && jugador.getCat().esInferiorOIgual(categoriaEquipo)) {
            jugadoresFiltrados.put(jugador.getId(), jugador);
        }
    }


    afgjugadors.actualizarTabla(jugadoresFiltrados, idEquipoSeleccionado, membresDelEquipo);
}

private void afejirJugadorsSeleccionats(int idEquip) throws gestorEquipsException {
    
    JTable tabla = afgjugadors.getTaulaJugadors();
    int[] filasSeleccionadas = tabla.getSelectedRows();
    if (filasSeleccionadas.length == 0) {
        return;
    }
    for (int fila : filasSeleccionadas) {
        int idJugador =  (int) tabla.getValueAt(fila, 0); 
        char titular = ((Boolean) tabla.getValueAt(fila, 5)) ? 'S' : 'N';
        System.out.println(""+titular);

        try {
            Jugador jugador = hmjug.get(idJugador);
            persistencia.afegir_membre(idJugador, idEquip, titular);
            Membre membre = new Membre(jugador,hmeqp.get(idEquip),titular);
            hmmem.put(""+jugador.getIdLegal()+idEquip,membre);
            jugAfegir.add(membre);
            JOptionPane.showMessageDialog(null, "Jugadores afegits correctament.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (gestorEquipsException ex) {
             JOptionPane.showMessageDialog(null, "El jugador ja es titular en un altre equip.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void borrarJugadorsSeleccionats(EquipPanel equipPanel, Integer idEquip) throws gestorEquipsException {
    ArrayList<Jugador> jugadoresSeleccionados = equipPanel.obtenerMiembrosSeleccionados(equipPanel.getTabla(), hmjug);
    
    for (Jugador jugador : jugadoresSeleccionados) {
        String claveMembre = jugador.getIdLegal() + String.valueOf(idEquip);
        System.out.println(""+claveMembre);
        if (hmmem.containsKey(claveMembre)) {
            Membre membre = hmmem.get(claveMembre);
            persistencia.eliminar_membre(membre);
            hmmem.remove(claveMembre);
        }
    equipPanel.cargarJugadores(idEquip, hmmem);
  
}

}
private void desaEquips(EquipPanel equipPanel,int idEquip){
        try {
            persistencia.commit();
            
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            jugAfegir.clear();
        }
        HashMap<String,Membre>titulars=equipPanel.obtenerMiembrosTitulares(equipPanel.getTabla(), hmjug,hmeqp,idEquip);
        for (Membre titular : titulars.values()) {
            try {
                
                persistencia.editar_membre(titular);
                hmmem.replace(titular.getJug().getIdLegal()+titular.getEq().getIdEq()+"", titular);
            } catch (gestorEquipsException ex) {
                Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                persistencia.commit();
            } catch (gestorEquipsException ex) {
                Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            equipPanel.cargarJugadores(idEquip, hmmem);
    }
        
        
}

private void borrarjugadorsseleccionats() {
    ArrayList<Jugador> seleccionats = panelJugadors.obtenirSeleccionats(hmjug);
    for (Jugador seleccionat : seleccionats) {
        try {
            System.out.println("" + seleccionat.getNomJugador() + seleccionat.getId());
            ArrayList<Membre> membre = persistencia.obtenir_membre_per_jugador(seleccionat.getId());
            
            if (membre != null && !membre.isEmpty()) {                
                for (Membre mem : membre) {
                    System.out.println("" + mem.getJug().getNomJugador() + " " + mem.getEq().getNomEquip());
                    persistencia.eliminar_membre(mem);
                    hmmem.remove(seleccionat.getId() + mem.getEq().getIdEq() + "");
                }
            }
            
            persistencia.eliminar_jugador(seleccionat);
            hmjug.remove(seleccionat.getId());
        } catch (gestorEquipsException ex) {
            JOptionPane.showMessageDialog(null, "Error a l'esborrar el jugador " + seleccionat.getNomJugador() + " " + seleccionat.getCognoms(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("" + ex);
        }
    }
    panelJugadors.actualizarTabla(hmjug, hmcat);
}


private void crearEquip() {
        String nom=creaEq.getE_nom().getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        ButtonModel mod=creaEq.getGrup().getSelection();
        char tipus;
        Equip eqp=null;
        if(mod.equals(creaEq.getFem().getModel())){
            tipus='D';
        }else if(mod.equals(creaEq.getMasc().getModel())){
            tipus='H';
        }else{
            tipus='M';
        }
        int idcat=creaEq.getCategories().getSelectedIndex()+1;
        int idtemp=-1;
        String any=creaEq.getTemporades().getSelectedItem().toString();
        for (Temporada temporada : hmtemp) {
        if(sdf.format(temporada.getAnyIni()).equals(any)){
                    idtemp=temporada.getIdTemp();
                     eqp=new Equip(hmeqp.size()+1,nom,tipus,hmcat.get(idcat),temporada);
                }
            }
        try {
            System.out.println("nom: "+nom+"tipus: "+tipus+"idcat: "+"temporada: "+idtemp);
           hmeqp.put(hmeqp.size()+1, eqp);
            persistencia.afegir_equip(nom, tipus, idcat, idtemp);
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
private void editarEquip(Equip eq){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    eq.setNomEquip(creaEq.getE_nom().getText());
    Temporada temp = null;
    eq.setCat(hmcat.get(creaEq.getCategories().getSelectedIndex()+1));
     String any=creaEq.getTemporades().getSelectedItem().toString();
     for (Temporada temporada : hmtemp) {
        if(sdf.format(temporada.getAnyIni()).equals(any)){
                    temp=temporada;
        }
     }
     
    eq.setTemp(temp);
    ButtonModel mod=creaEq.getGrup().getSelection();
        char tipus;
        Equip eqp=null;
        if(mod.equals(creaEq.getFem().getModel())){
            tipus='D';
        }else if(mod.equals(creaEq.getMasc().getModel())){
            tipus='H';
        }else{
            tipus='M';
        }
        eq.setTipus(tipus);
        try {
            persistencia.editar_equip(eq);
            Equip equi=hmeqp.get(eq.getIdEq());
            equi=eq;
          
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
       
}

private void rellenarCreaEquip(Equip eq){
    ((CreaEquip_JPanel)creaEq).getE_nom().setText(eq.getNomEquip());
    ((CreaEquip_JPanel)creaEq).getCategories().setSelectedItem(eq.getCat().getNom());
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    ((CreaEquip_JPanel)creaEq).getTemporades().setSelectedItem(sdf.format(eq.getTemp().getAnyIni()));
             switch (eq.getTipus()) {
        case 'H':
            ((CreaEquip_JPanel)creaEq).getMasc().setSelected(true);
            break;
        case 'D':
            ((CreaEquip_JPanel)creaEq).getFem().setSelected(true);
            break;
        case 'M':
            ((CreaEquip_JPanel)creaEq).getMix().setSelected(true);
            break;
    }
}
 private void limpiarFormulario() {
        creaEq.getE_nom().setText("");
        creaEq.getCategories().setSelectedIndex(0);
        creaEq.getTemporades().setSelectedIndex(0);
        creaEq.getGrup().clearSelection();
    }
 private void abrirPantallaEquip(boolean esEdicion, Equip equipo) {
        creaEq.setModoEdicion(esEdicion);

        if (esEdicion && equipo != null) {
            rellenarCreaEquip(equipo);
        } else {
            limpiarFormulario();
        }

        cardLayout.show(panelCentro, "creaEq");
    }
    private void abrirEdicionEq(Equip eq){
        isedicio = true;
        abrirPantallaEquip(isedicio,eq);
    }
    private void abrirCreacionEq(){
        Equip eq=null;
      isedicio = false;
        abrirPantallaEquip(isedicio,eq);
    }
    private void crearJugador(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
char sexe = 0;
java.sql.Date dataNaix = null;
java.sql.Date dataRevisio = null;

String nom = fitxaJug.getTextNom().getText();
String cognom = fitxaJug.getTextCognoms().getText();


if (fitxaJug.getRdDona().isSelected()) {
    sexe = 'D';
} else if (fitxaJug.getRdHome().isSelected()) {
    sexe = 'H';
        System.out.println("Sexe: "+sexe);
        
try {
    java.util.Date utilDataNaix = sdf.parse(fitxaJug.getCal().getText());
    java.util.Date utilDataRevisio = sdf.parse(fitxaJug.getCalRevisio().getText());
    dataNaix = new java.sql.Date(utilDataNaix.getTime());
    dataRevisio = new java.sql.Date(utilDataRevisio.getTime());
} catch (ParseException ex) {
    Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, "Error al parsear las fechas", ex);
}

String id_legal = fitxaJug.getTextNif().getText();
String iban = fitxaJug.getTextIban().getText();
String adreça = fitxaJug.getTextAdreça().getText();
String poblacio = fitxaJug.getTextPoblacio().getText();
String codip = fitxaJug.getTextCp().getText();
String foto = fitxaJug.getRutaImagen();

try {
    persistencia.afegir_jugador(nom, cognom, sexe, dataNaix, id_legal, iban, dataRevisio, adreça, poblacio, codip, foto);
    persistencia.commit();
    Jugador jug = new Jugador(persistencia.obtenir_ultim_jugador(), nom, cognom, sexe, dataNaix, id_legal, iban, dataRevisio, adreça, foto);
    hmjug.put(jug.getId(), jug);
    panelJugadors.actualizarTabla(hmjug, hmcat);
    persistencia.commit();
} catch (gestorEquipsException ex) {
    Logger.getLogger(Vista_Controlador.class.getName()).log(Level.SEVERE, "Error al añadir el jugador", ex);
}

        
    }}
    public void seleccionarFoto() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona una imagen");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
    int seleccion = fileChooser.showOpenDialog(null);
    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivoSeleccionado = fileChooser.getSelectedFile();
        String ruta = archivoSeleccionado.getAbsolutePath();
        System.out.println(""+ruta);
        fitxaJug.setRutaImagen(ruta);      
        ImageIcon nuevaImagen = new ImageIcon(ruta);
        nuevaImagen=fitxaJug.redimensionarImagen(ruta, 200, 200);
        fitxaJug.getImagenLabel().setIcon(nuevaImagen);  
        
    } else {
        JOptionPane.showMessageDialog(null, "No s'ha sel·leccionat cap imatge", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
public void omplirFitxaJugador(Jugador jug){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    fitxaJug.getTextNom().setText(jug.getNomJugador());
    fitxaJug.getTextCognoms().setText(jug.getCognoms());
    fitxaJug.getTextAdreça().setText(jug.getAdreça());
    fitxaJug.getTextCp().setText(jug.getCp());
    fitxaJug.getTextNif().setText(jug.getIdLegal());
    fitxaJug.getTextPoblacio().setText(jug.getCiutat());
    fitxaJug.getTextIban().setText(jug.getIban());
      String rutaImagen = jug.getFoto();
    if (rutaImagen != null && !rutaImagen.isEmpty()) {
        ImageIcon imagen = new ImageIcon(rutaImagen);  
        imagen=fitxaJug.redimensionarImagen(rutaImagen, 200, 200);
        fitxaJug.getImagenLabel().setIcon(imagen);
        
    } 
    fitxaJug.getCal().setText(sdf.format(jug.getDataNaix()));
    fitxaJug.getCalRevisio().setText(sdf.format(jug.getAnyFiRevisioMedica()));
     switch (jug.getSexe()) {
        case 'H':
            fitxaJug.getRdHome().setSelected(true);
            break;
        case 'D':
            fitxaJug.getRdDona().setSelected(true);
            break;
     }
}
     private void limpiarFormularioFicha() {
         fitxaJug.getTextNom().setText("");
    fitxaJug.getTextCognoms().setText("");
    fitxaJug.getTextAdreça().setText("");
    fitxaJug.getTextCp().setText("");
    fitxaJug.getTextNif().setText("");
    fitxaJug.getTextPoblacio().setText("");
    fitxaJug.getTextIban().setText("");
    String rutaImagen = "C:/Users/isard/Documents/Projecte-1/Vista_Controlador/src/img/placeholder-male.jpg";
    ImageIcon imagen = new ImageIcon(rutaImagen);  
        imagen=fitxaJug.redimensionarImagen(rutaImagen, 200, 200);
        fitxaJug.getImagenLabel().setIcon(imagen);
        fitxaJug.getCal().setText("");
    fitxaJug.getCalRevisio().setText("");
    fitxaJug.getRdDona().setSelected(false);
    fitxaJug.getRdHome().setSelected(false);
   
    }
    private void abrirFichaVacia(){
        limpiarFormularioFicha();
        cardLayout.show(panelCentro, "fitxaJug");
    }


public void exportarJugadoresCSV(HashMap<Integer, Jugador> hmjug) {

    File archivoCSV = new File("jugadores.csv");
 
    try (FileWriter writer = new FileWriter(archivoCSV)) {

        writer.append("Nom,Cognoms,Sexe,Data de naixement,NIF,IBAN,Data revisió médica,Adreça,Foto,Ciudad,Código Postal\n");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Jugador jugador : hmjug.values()) {
            writer.append(jugador.getNomJugador());  
            writer.append(",");  
            writer.append(jugador.getCognoms());  
            writer.append(","); 
            writer.append(String.valueOf(jugador.getSexe())); 
            writer.append(",");
            writer.append(sdf.format(jugador.getDataNaix()));
            writer.append(","); 
            writer.append(jugador.getIdLegal());
            writer.append(","); 
            writer.append(jugador.getIban());
            writer.append(","); 
            if (jugador.getAnyFiRevisioMedica() != null) {
                writer.append(sdf.format(jugador.getAnyFiRevisioMedica()));
            } else {
                writer.append("");  
            }
            writer.append(",");  
            writer.append(escapeComas(jugador.getAdreça()));
            writer.append(","); 
            writer.append(jugador.getFoto()); 
            System.out.println(""+jugador.getFoto());
            writer.append(",");  
            writer.append(jugador.getCiutat());
            writer.append(","); 
            writer.append(jugador.getCp());  
            writer.append("\n");  
        }
    } catch (IOException e) {
      
        System.err.println("Error al exportar el l'arxiu CSV: " + e.getMessage());
    }
}
private String escapeComas(String input) {
    if (input == null) {
        return "";
    }
    

    String result = input.replace("\"", "\"\"");
    if (result.contains(",") || result.contains("\n") || result.contains("\r")) {
        result = "\"" + result + "\""; 
    }
    
    return result;
}

    public static void main(String[] args) {
         if (args.length == 0) {
            System.out.println("Cal passar el nom de la classe que dona la persistència com a primer argument");
            System.exit(0);
        }
        try {
            nomClassePersistencia = args[0];
            new Vista_Controlador();
        } catch (gestorEquipsException ex) {
            ex.printStackTrace(); 
        }
    }
}
   
