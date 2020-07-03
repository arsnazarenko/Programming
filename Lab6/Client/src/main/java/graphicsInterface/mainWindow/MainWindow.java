package graphicsInterface.mainWindow;

import graphicsInterface.LocaleActionListener;
import graphicsInterface.mainWindow.commands.CommandPanel;
import graphicsInterface.mainWindow.table.TablePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame implements LocaleActionListener {
    private final String FONT;
    private JTabbedPane tabbedPane;
    private CommandPanel commandPanel;
    private TablePanel tablePanel;
    private UserPanel userPanel;

    public MainWindow(String FONT, CommandPanel commandPanel,
                      TablePanel tablePanel, UserPanel userPanel, Locale locale) {
        super("Table of the Organizations");
        this.FONT = FONT;
        this.commandPanel = commandPanel;
        this.tablePanel = tablePanel;
        this.userPanel = userPanel;

        createTabbedPane();
        createFrame();

        localeChange(locale);

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
        tabbedPane.addTab(null,new JPanel());
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setFont(new Font(FONT, Font.PLAIN, 12));}

    @Override
    public void localeChange(Locale locale ) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        tabbedPane.setTitleAt(0,bundle.getString("table"));
        tabbedPane.setTitleAt(1,bundle.getString("map"));
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }

    public void setCommandPanel(CommandPanel commandPanel) {
        this.commandPanel = commandPanel;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public void setTablePanel(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public void setUserPanel(UserPanel userPanel) {
        this.userPanel = userPanel;
    }
}
