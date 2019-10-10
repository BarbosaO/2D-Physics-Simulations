/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Oscar Barbosa
 */
public class Tennis extends Canvas implements Runnable, KeyListener
{
    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    AIPaddle p2;
    Ball b1;
    boolean gameStarted;
    Graphics gfx ;
    Image img;
    
    public void init()
    {
        this.resize(WIDTH, HEIGHT);
        gameStarted = false;
        this.addKeyListener(this);   
        thread = new Thread(this);
        p1 = new HumanPaddle(1);
        b1 = new Ball();
        p2 = new AIPaddle(2, b1);
        
        img = createImage(WIDTH, HEIGHT);
        gfx = img.getGraphics();
        thread.start();
    }
    
    public void paint(Graphics g)
    {
        gfx.setColor(Color.BLACK);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);
        if(b1.getX() < -10 || b1.getX() > 710)
        {
            gfx.setColor(Color.WHITE);
            gfx.drawString("Game Over", 350, 250);
            
        }
          
        p1.draw(gfx);
        b1.draw(gfx);
        p2.draw(gfx);
        
        if(!gameStarted)
        {
            gfx.setColor(Color.WHITE);
            gfx.drawString("Tennis", 340, 100);
            gfx.drawString("Press Enter to Start", 310, 130);
        }
        g.drawImage(img, 0, 0, this);
          
    }
    
    public void update(Graphics g)
    {
        paint(g);
    }

    @Override
    public void run() 
    {
        for(;;)
        {
            if(gameStarted)
            {
                p1.move();
                p2.move();
                b1.move();
                b1.checkPaddleCollision(p1, p2);
            
            }
            
            repaint();
            
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){}
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            p1.setUpAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            p1.setDownAccel(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            gameStarted = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            p1.setUpAccel(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            p1.setDownAccel(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String args[])
    {
        JFrame f = new JFrame();
        Tennis game = new Tennis();
        f.add(game);
        f.pack();
        f.setSize(700, 510);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        game.init();


    }
}
