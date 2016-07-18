package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class DrawingPanel extends JPanel {
    public BufferedImage image;
    private ActionType actionType;
    private Color currentColor;

    private BufferedImage currentAllocation;

    private Polygon polygon;

    int xPressed;
    int yPressed;
    int xReleased;
    int yReleased;
    int xPrevious;
    int yPrevious;

    public DrawingPanel(int wight, int height) {
        image = new BufferedImage(wight, height, BufferedImage.TYPE_4BYTE_ABGR);
        setCurrentColor(Color.BLACK);
        actionType = ActionType.PENCIL;

        MouseAdapter common = new CommonAdapter();
        this.addMouseListener(common);
        this.addMouseMotionListener(common);

        MouseAdapter rectanglePainter = new RectanglePainter();
        this.addMouseListener(rectanglePainter);
        this.addMouseMotionListener(rectanglePainter);

        MouseAdapter pencilPainter = new PencilPainter();
        this.addMouseListener(pencilPainter);
        this.addMouseMotionListener(pencilPainter);

        MouseAdapter circlePainter = new CirclePainter();
        this.addMouseListener(circlePainter);
        this.addMouseMotionListener(circlePainter);

        MouseAdapter erasePainter = new EraserPainter();
        this.addMouseListener(erasePainter);
        this.addMouseMotionListener(erasePainter);

        MouseAdapter allocator = new Allocator();
        this.addMouseListener(allocator);
        this.addMouseMotionListener(allocator);

        MouseAdapter putter = new Putter();
        this.addMouseListener(putter);
        this.addMouseMotionListener(putter);

        MouseAdapter deleter = new Cutter();
        this.addMouseListener(deleter);
        this.addMouseMotionListener(deleter);

        MouseAdapter loupe = new Loupe();
        this.addMouseListener(loupe);
        this.addMouseMotionListener(loupe);

        MouseAdapter text = new TextPainter();
        this.addMouseListener(text);
        this.addMouseMotionListener(text);

        MouseAdapter polygonalAl = new PolygonalAllocator();
        this.addMouseListener(polygonalAl);
        this.addMouseMotionListener(polygonalAl);
    }

    public BufferedImage getImage() {
        return this.image ;
    }
    
    

    public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void test() {
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1200, 600);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, null);
    }

    public void setActionType(ActionType type) {
        this.actionType = type;
        if (type == ActionType.POLYGONAL_AREA) {
            polygon = new Polygon();
        }
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    class CommonAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        public void mousePressed(MouseEvent e) {
            xPressed = e.getX();
            yPressed = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            if (actionType == ActionType.PENCIL || actionType == ActionType.INSERT
                    || actionType == ActionType.ERASER || actionType == ActionType.LOUPE2
                    || actionType == ActionType.LOUPE4 || actionType == ActionType.POLYGONAL_AREA)
                return;
            repaint();

            int xP = xPressed;
            int yP = yPressed;

            int xR = e.getX();
            int yR = e.getY();

            int wight = xR - xP; 
            int height = yR - yP;

            if (wight < 0) {
                wight = -wight;
                xP = xR;
            }
            if (height < 0) {
                height = -height;
                yP = yR;
            }

            getGraphics().drawRect(xP, yP, wight, height);
        }
    }

    class RectanglePainter extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.QUADRANGLE) return;
            xReleased = e.getX();
            yReleased = e.getY();
            Graphics g = image.getGraphics();
            g.setColor(currentColor);

            int wight = xReleased - xPressed;
            int height = yReleased - yPressed;

            if (wight < 0) {
                wight = -wight;
                int newX = xPressed;
                xPressed = xReleased;
                xReleased = newX;
            }
            if (height < 0) {
                height = -height;
                int newY = yPressed;
                yPressed = yReleased;
                yReleased = newY;
            }
            g.drawRect(xPressed, yPressed, wight, height);
            repaint();
        }
    }

    class PencilPainter extends MouseAdapter {
        boolean lineNew = true;

        public void mouseDragged(MouseEvent e) {
            if (actionType != ActionType.PENCIL) return;
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage("d:\\images\\cursor_pencil.png");
            Point hotspot = new Point(0, 0);
            Cursor cursor = toolkit.createCustomCursor(image, hotspot, "pencil");
            setCursor(cursor);

            int x = e.getX();
            int y = e.getY();
            if (!lineNew) {
                lineDrawing(x, y);
            } else {
                lineNew = false;
            }

            xPrevious = x;
            yPrevious = y;
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            lineNew = true;
        }

        private void lineDrawing(int x, int y) {
            Graphics g = image.getGraphics();
            g.setColor(currentColor);
            g.drawLine(xPrevious, yPrevious, x, y);
        }
    }

    class CirclePainter extends MouseAdapter {
        public void mouseMoved(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            xPressed = e.getX();
            yPressed = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.CIRCLE) return;
            xReleased = e.getX();
            yReleased = e.getY();
            Graphics g = image.getGraphics();
            g.setColor(currentColor);

            int wight = xReleased - xPressed;
            int height = yReleased - yPressed;

            if (wight < 0) {
                wight = -wight;
                int newX = xPressed;
                xPressed = xReleased;
                xReleased = newX;
            }
            if (height < 0) {
                height = -height;
                int newY = yPressed;
                yPressed = yReleased;
                yReleased = newY;
            }
            g.drawOval(xPressed, yPressed, wight, height);
            repaint();
        }
    }

    class EraserPainter extends MouseAdapter {
        boolean lineNew = true;

        public void mouseDragged(MouseEvent e) {
            if (actionType != ActionType.ERASER) return;
            int x = e.getX();
            int y = e.getY();
            if (!lineNew) {
                eraserDraw(x, y);
            } else {
                lineNew = false;
            }

            xPrevious = x;
            yPrevious = y;
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            lineNew = true;
        }

        private void eraserDraw(int x, int y) {
            Graphics g = image.getGraphics();
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10.0f));
            g2.drawLine(xPrevious, yPrevious, x, y);
        }
    }

    class Allocator extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            if (actionType != ActionType.RECTANGLE_AREA) return;
            xPressed = e.getX();
            yPressed = e.getY();
        }

        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.RECTANGLE_AREA) return;
            xReleased = e.getX();
            yReleased = e.getY();
            allocate();
        }

        private void allocate() {
            int xP = xPressed;
            int yP = yPressed;

            int xR = xReleased;
            int yR = yReleased;

            int wight = xR - xP; 
            int height = yR - yP;

            if (wight < 0) {
                wight = -wight;
                xP = xR;
            }
            if (height < 0) {
                height = -height;
                yP = yR;
            }

            currentAllocation = new BufferedImage(wight, height, BufferedImage.TYPE_4BYTE_ABGR);
            currentAllocation.getGraphics().drawImage(image, -xP, -yP, null);
        }
    }

    class Putter extends MouseAdapter {
        public void mouseDragged(MouseEvent e) {
            if (actionType != ActionType.INSERT) return;
            repaint();
            getGraphics().drawRect(e.getX(), e.getY(), currentAllocation.getWidth(), currentAllocation.getHeight());
            getGraphics().drawImage(currentAllocation, e.getX(), e.getY(), null);
        }

        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.INSERT) return;
            image.getGraphics().drawImage(currentAllocation, e.getX(), e.getY(), null);
            repaint();
        }
    }

    class Cutter extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.CUT) return;
            xReleased = e.getX();
            yReleased = e.getY();
            eraseRec();
        }

        private void eraseRec() {
            int xP = xPressed;
            int yP = yPressed;

            int xR = xReleased;
            int yR = yReleased;

            int wight = xR - xP; 
            int height = yR - yP;

            if (wight < 0) {
                wight = -wight;
                xP = xR;
            }
            if (height < 0) {
                height = -height;
                yP = yR;
            }

            currentAllocation = new BufferedImage(wight, height, BufferedImage.TYPE_4BYTE_ABGR);
            currentAllocation.getGraphics().drawImage(image, -xP, -yP, null);

            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(xP, yP, wight, height);

            repaint();
        }
    }

    class Loupe extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (actionType == ActionType.LOUPE2 || actionType == ActionType.LOUPE4) {


                double value = 1.0;
                if (actionType == ActionType.LOUPE2) value = 2.0;
                if (actionType == ActionType.LOUPE4) value = 4.0;

                BufferedImage before = new BufferedImage(200, 300, BufferedImage.TYPE_4BYTE_ABGR);
                before.getGraphics().drawImage(image, -e.getX(), -e.getY(), null);
                BufferedImage after = new BufferedImage(200, 300, BufferedImage.TYPE_4BYTE_ABGR);
                AffineTransform at = new AffineTransform();
                at.scale(value, value);
                AffineTransformOp scaleOp =
                        new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                after = scaleOp.filter(before, after);

                getGraphics().drawImage(after, e.getX(), e.getY(), null);
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (actionType == ActionType.LOUPE2 || actionType == ActionType.LOUPE4) repaint();
        }
    }

    class TextPainter extends MouseAdapter {
        public void mouseReleased(MouseEvent m) {
            if (actionType != ActionType.TEXT) return;
            JDialog textDialog = new JDialog();
            textDialog.setSize(new Dimension(200, 300));
            textDialog.setLayout(new BorderLayout());
            JTextArea textArea = new JTextArea();
            textDialog.add(textArea, BorderLayout.CENTER);
            JButton button = new JButton("Ok");
            textDialog.add(button, BorderLayout.SOUTH);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String data = textArea.getText();
                    Graphics g = image.getGraphics();
                    Font font = new Font("Tahoma", Font.BOLD | Font.ITALIC, 40);
                    g.setFont(font);
                    g.setColor(currentColor);
                    g.drawString(data, xPressed, yPressed);
                    textDialog.setVisible(false);
                    repaint();
                }
            });
            textDialog.setVisible(true);
        }
    }

    class PolygonalAllocator extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            if (actionType != ActionType.POLYGONAL_AREA) return;

            polygon.addPoint(e.getX(), e.getY());

            if (polygon.npoints > 2) {

                currentAllocation = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
                Graphics g = currentAllocation.getGraphics();
                g.setClip(polygon);
                g.drawImage(image, 0, 0, null);

                Rectangle bounds = polygon.getBounds();
                BufferedImage fin    = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                fin.getGraphics().drawImage(currentAllocation, -1 * (int) bounds.getX(), -1 * (int) bounds.getY(), null);
                currentAllocation = fin;

                getGraphics().drawPolygon(polygon);
            }


        }
    }
}

	

