import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    static final int WORLD_SCALE = 10, SCREEN_SIZE = 50;
    static final GameObject APPLE = new GameObject(WORLD_SCALE, new Color(255, 0 ,0)), HEAD = new GameObject(WORLD_SCALE, new Color(0, 100, 0));
    static List<GameObject> bodyParts = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException
    {
        GameKeyAdapter gameKeyAdapter = new GameKeyAdapter();
        JPanel panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(new Color(0));
                APPLE.draw(g);
                HEAD.draw(g);
                for(GameObject part : bodyParts)
                    part.draw(g);
            }
        };

        JFrame frame = new JFrame("Snake");
        frame.add(panel);
        frame.addKeyListener(gameKeyAdapter);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WORLD_SCALE*SCREEN_SIZE, WORLD_SCALE*SCREEN_SIZE));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        HEAD.setPosition(SCREEN_SIZE/2 * WORLD_SCALE, SCREEN_SIZE/2 * WORLD_SCALE);
        spawnApple();
        boolean gameRunning = true;
        while(gameRunning)
        {
            TimeUnit.MILLISECONDS.sleep(1000);
            panel.repaint();
            int lastX = HEAD.x, lastY = HEAD.y;

            switch (gameKeyAdapter.getLastKeyPressed()) {
                case 'w' -> HEAD.setPosition(HEAD.x, HEAD.y + WORLD_SCALE * -1);
                case 'a' -> HEAD.setPosition(HEAD.x + WORLD_SCALE * -1, HEAD.y);
                case 's' -> HEAD.setPosition( HEAD.x, HEAD.y + WORLD_SCALE);
                case 'd' -> HEAD.setPosition(HEAD.x + WORLD_SCALE, HEAD.y);
            }
            if(bodyParts.size() > 0)
            {
                bodyParts.get(0).setPosition(lastX, lastY);
                bodyParts.add(bodyParts.get(0));
                bodyParts.remove(0);
            }
            if(HEAD.x == APPLE.x && HEAD.y == APPLE.y)
            {
                spawnApple();
                GameObject newGameObject = new GameObject(WORLD_SCALE, new Color(0, 200, 0));
                newGameObject.setPosition(lastX, lastY);
                bodyParts.add(newGameObject);
            }
            for(GameObject part : bodyParts)
            {
                if(part.x == HEAD.x && part.y == HEAD.y)
                {
                    gameRunning = false;
                    break;
                }
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
            APPLE.setPosition(random.nextInt(SCREEN_SIZE) * WORLD_SCALE, random.nextInt(SCREEN_SIZE) * WORLD_SCALE);
            if(HEAD.x == APPLE.x || HEAD.y == APPLE.y)
            {
                spawn = true;
                break;
            }
            for(GameObject part : bodyParts)
            {
                if(part.x == APPLE.x || part.y == APPLE.y)
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