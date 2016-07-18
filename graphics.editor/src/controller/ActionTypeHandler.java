package controller;

import model.ActionType;
import model.DrawingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionTypeHandler implements ActionListener {
    private DrawingPanel drawingPanel;

    private final String PENCIL = "pencil.jpeg";
    private final String ERASER = "eraser.png";
    private final String QUADRANGLE = "quadrangle.png";
    private final String CIRCLE = "circle.jpg";
    private final String TEXT = "t.png";
    private final String POLUGONAL_AREA = "someArea.png";
    private final String RECTANGLE_AREA = "area.gif";
    private final String LOUPE2 = "loupe2.png";
    private final String LOUPE4 = "loupe4.png";
    private final String INSERT = "insert.jpg";
    private final String CUT = "delete.jpg";

    public ActionTypeHandler(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        JButton button = (JButton) object;
        String buttonName = button.getName();
        if (buttonName == null) {
            drawingPanel.setCurrentColor(button.getBackground());
        } else {
            switch (buttonName) {
                case PENCIL:
                    drawingPanel.setActionType(ActionType.PENCIL);
                    break;
                case ERASER:
                    drawingPanel.setActionType(ActionType.ERASER);
                    break;
                case QUADRANGLE:
                    drawingPanel.setActionType(ActionType.QUADRANGLE);
                    break;
                case CIRCLE:
                    drawingPanel.setActionType(ActionType.CIRCLE);
                    break;
                case TEXT:
                    drawingPanel.setActionType(ActionType.TEXT);
                    break;
                case POLUGONAL_AREA:
                    drawingPanel.setActionType(ActionType.POLYGONAL_AREA);
                    break;
                case RECTANGLE_AREA:
                    drawingPanel.setActionType(ActionType.RECTANGLE_AREA);
                    break;
                case LOUPE2:
                    drawingPanel.setActionType(ActionType.LOUPE2);
                    break;
                case LOUPE4:
                    drawingPanel.setActionType(ActionType.LOUPE4);
                    break;
                case INSERT:
                    drawingPanel.setActionType(ActionType.INSERT);
                    break;
                case CUT:
                    drawingPanel.setActionType(ActionType.CUT);
                    break;
            }
        }
    }

}

