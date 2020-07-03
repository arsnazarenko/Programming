package frontend.graphicsInterface.mainWindow;



import frontend.graphicsInterface.LocaleActionListener;
import frontend.graphicsInterface.controllers.Controllers;
import frontend.graphicsInterface.mainWindow.commands.CommandPanel;
import frontend.graphicsInterface.mainWindow.table.TablePanel;
import frontend.mvc.ObjectsMapModel;
import frontend.mvc.ObjectsMapView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame implements LocaleActionListener {
    private final String FONT;
    private JTabbedPane tabbedPane;
    private CommandPanel commandPanel;
    private TablePanel tablePanel;
    private UserPanel userPanel;
    private ObjectsMapView mapView;

    public MainWindow(String FONT, CommandPanel commandPanel,
                      TablePanel tablePanel, UserPanel userPanel) {
        super("Table of the Organizations");
        this.FONT = FONT;
        this.commandPanel = commandPanel;
        this.tablePanel = tablePanel;
        this.userPanel = userPanel;
        createTabbedPane();
        createFrame();
        localeChange(Controllers.getLocale());

    }

    private void createFrame(){
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBackground(Color.WHITE);

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(userPanel,BorderLayout.NORTH);
        westPanel.add(commandPanel,BorderLayout.CENTER);
        add(westPanel,BorderLayout.WEST);
        add(tabbedPane,BorderLayout.CENTER);
    }

    private void createTabbedPane(){
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab(null,tablePanel);
        ObjectsMapModel model = new ObjectsMapModel(new ArrayDeque<>());
        tabbedPane.addTab(null, new ObjectsMapView(model.getOrganizationsCoordinateInfo(), model.getCellSize(), model.getCellCount()));
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setFont(new Font(FONT, Font.PLAIN, 12));}

    @Override
    public void localeChange(Locale locale ) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        tabbedPane.setTitleAt(0,bundle.getString("table"));
        tabbedPane.setTitleAt(1,bundle.getString("map"));
    }
    public CommandPanel getCommandPanel() {
        return commandPanel;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }
}
