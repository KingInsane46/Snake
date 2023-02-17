import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    static final int WORLD_SCALE = 10, SCREEN_SIZE = 50;
    static final GameObject apple = new GameObject(WORLD_SCALE, new Color(255, 0 ,0)),head = new GameObject(WORLD_SCALE, new Color(0, 100, 0));
    static List<GameObject> bodyParts = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException
    {
        final Dimension SCREEN_DIMENSION = new Dimension(WORLD_SCALE*SCREEN_SIZE, WORLD_SCALE*SCREEN_SIZE);
        GameKeyAdapter gameKeyAdapter = new GameKeyAdapter();
        JPanel panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(new Color(0));
                apple.draw(g);
                for(GameObject part : bodyParts)
                    part.draw(g);
            }
        };

        JFrame frame = new JFrame("Snake");
        frame.add(panel);
        frame.addKeyListener(gameKeyAdapter);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_DIMENSION);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        bodyParts.add(head);
        head.setPosition(WORLD_SCALE * SCREEN_SIZE/2, WORLD_SCALE * SCREEN_SIZE/2);
        spawnApple();
        boolean gameRunning = true;
        while(gameRunning)
        {
            panel.repaint();
            TimeUnit.MILLISECONDS.sleep(200);

            switch (gameKeyAdapter.getLastKeyPressed()) {
                case 'w' -> head.setPosition(head.x, head.y + WORLD_SCALE * -1);
                case 'a' -> head.setPosition(head.x + WORLD_SCALE * -1, head.y);
                case 's' -> head.setPosition( head.x, head.y + WORLD_SCALE);
                case 'd' -> head.setPosition(head.x + WORLD_SCALE, head.y);
            }
        }
    }

    static void spawnApple()
    {
        Random random = new Random();
        boolean spawn = true;
        while(spawn)
        {
            spawn = false;
            apple.setPosition(random.nextInt(WORLD_SCALE * SCREEN_SIZE), random.nextInt(WORLD_SCALE * SCREEN_SIZE));
            for(GameObject part : bodyParts)
            {
                if(part.x == apple.x || part.y == apple.y)
                {
                    spawn = true;
                    break;
                }
            }
        }
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

class GameKeyAdapter extends KeyAdapter
{
    public char lastKeyPressed = 'd';
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
    }

    public char getLastKeyPressed()
    {
        return lastKeyPressed;
    }
};