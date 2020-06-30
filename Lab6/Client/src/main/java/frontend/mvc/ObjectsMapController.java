package frontend.mvc;

import library.сlassModel.Organization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Deque;

public class ObjectsMapController {
    private ObjectsMapView view;
    private ObjectsMapModel model;

    public ObjectsMapController(ObjectsMapView view, ObjectsMapModel model) {
        this.view = view;
        this.model = model;
        initController();
        initDrawPane();
    }

    private void initController() {
        view.getClearButton().addActionListener(e -> clearTextArea());

    }
    private void initDrawPane() {
        view.getDrawPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    super.mouseClicked(e);

                    model.getOrganizationsCoordinateInfo().forEach((key, value) -> {
                        if (equalsCoordinate(e.getPoint(), value.getValue(), value.getKey())) {
                            view.getObjectsInfo().append(" " + key.toString() + "\n");
                        }
                    });
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    super.mouseClicked(e);
                    System.out.println("Окно с этим объектом для редактирования ");
                }
            }
        });
    }

    private boolean equalsCoordinate(Point p1, Point p2, int size) { //p1 - our
        if ((p2.y - 14 * size <= p1.y && p1.y <= (p2.y - 13 / 3 *size) && (p2.x - 1.5 * size) <= p1.x && p1.x <= (p2.x + 1.5 * size))) {
            return true;
        }
        return false;
    }

    private void clearTextArea() {
        JTextArea objectsArea = view.getObjectsInfo();
        objectsArea.selectAll();
        objectsArea.replaceSelection("");
    }
    public void updateObjectsMapView(Deque<Organization> organization) {
        if (view.getDrawPanel() != null) {
            model = new ObjectsMapModel(organization); //обновляем модель
            JScrollPane sp = view.getScrollPane();
            int cellSize = model.getCellSize(); //получаем данные для рисования
            int cellCount = model.getCellCount();

            Container contentPane = view.getContentPane();
            contentPane.remove(sp); //удаляем с панели старый объект

            view.setDrawPanel(new DrawPanel(model.getOrganizationsCoordinateInfo(), cellSize, cellCount)); //установили новый эелмент для рисования
            initDrawPane();
            sp.setViewportView(view.getDrawPanel()); //обновляем этот рисунок в перемещаемом окошке
            contentPane.add(sp);
            contentPane.revalidate();
            contentPane.repaint();
        }
    }


}
