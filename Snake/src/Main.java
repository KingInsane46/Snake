import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        final Dimension SCREEN_DIMENSION = new Dimension(Global.WORLD_SCALE*Global.SCREEN_SIZE, Global.WORLD_SCALE*Global.SCREEN_SIZE);

        JFrame frame = new JFrame("Snake");
        frame.add(new GamePanel());
        frame.addKeyListener(new GameKeyListener());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_DIMENSION);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

class GamePanel extends JPanel
{
    final GameObject apple = new GameObject(Global.WORLD_SCALE, new Color(255, 0 ,0));
    final GameObject head = new GameObject(Global.WORLD_SCALE, new Color(0, 100, 0));
    final GameObject body = new GameObject(Global.WORLD_SCALE, new Color(0 , 200, 0));

    List<GameObject> bodyParts = new ArrayList<>();

    public GamePanel()
    {
        head.setPosition(Global.SCREEN_SIZE*Global.WORLD_SCALE/2,Global.SCREEN_SIZE*Global.WORLD_SCALE/2);
    }

    public void paintComponent(Graphics g)
    {
        head.draw(g);
        apple.draw(g);
        for(GameObject part : bodyParts)
            part.draw(g);
    }
}

class GameKeyListener extends KeyAdapter
{
    char keyPressed;
    @Override
    public void keyPressed(KeyEvent event)
    {
        keyPressed = event.getKeyChar();
    }
}

class GameObject
{
    int x, y, size;
    Color color;

    public GameObject(int size, Color color)
    {
        this.size = size;
        this.color = color;
        x = 0;
        y = 0;
    }

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

class Global
{
    static int WORLD_SCALE = 10, SCREEN_SIZE = 50;
}