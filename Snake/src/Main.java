import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final Dimension SCREEN_DIMENSION = new Dimension(Global.WORLD_SCALE*Global.SCREEN_SIZE, Global.WORLD_SCALE*Global.SCREEN_SIZE);
        final GameObject apple = new GameObject(Global.WORLD_SCALE, new Color(255, 0 ,0));
        final GameObject head = new GameObject(Global.WORLD_SCALE, new Color(0, 100, 0));
        final GameObject body = new GameObject(Global.WORLD_SCALE, new Color(0 , 200, 0));
        List<GameObject> bodyParts = new ArrayList<>();

        JFrame frame = new JFrame("Snake");
        JPanel panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(new Color(0));

                head.draw(g);
                apple.draw(g);
                for(GameObject part : bodyParts)
                    part.draw(g);
            }
        };
        frame.addKeyListener(new GameKeyListener());
        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_DIMENSION);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        boolean gameRunning = true;
        while(gameRunning)
        {
            panel.repaint();
            TimeUnit.MILLISECONDS.sleep(500);

            switch(Global.moveDirection)
            {
                case 'w':
                    head.addPosition(0, Global.WORLD_SCALE*-1);
                    break;
                case 'a':
                    head.addPosition(Global.WORLD_SCALE*-1, 0);
                    break;
                case 's':
                    head.addPosition(0, Global.WORLD_SCALE);
                    break;
                case 'd':
                    head.addPosition(Global.WORLD_SCALE, 0);
                    break;
            }
        }
    }
}

class GameKeyListener extends KeyAdapter
{
    char lastKeyPressed = ' ';

    @Override
    public void keyPressed(KeyEvent event)
    {
        char keyPressed = event.getKeyChar();
        if(keyPressed == 'w' && lastKeyPressed != 's')
            lastKeyPressed = keyPressed;
        else if(keyPressed == 's' && lastKeyPressed != 'w')
            lastKeyPressed = keyPressed;
        else if(keyPressed == 'a' && lastKeyPressed != 'd')
            lastKeyPressed = keyPressed;
        else if(keyPressed == 'd' && lastKeyPressed != 'a')
            lastKeyPressed = keyPressed;
        Global.moveDirection = lastKeyPressed;
        System.out.println(Global.moveDirection);
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

    public void addPosition(int x, int y)
    {
        this.x += x;
        this.y += y;
    }
}

class Global
{
    static int WORLD_SCALE = 10, SCREEN_SIZE = 50;
    static char moveDirection = 'd';
}