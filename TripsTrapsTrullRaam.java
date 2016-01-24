package tripstrapstrull;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * JFrame
 */
public class TripsTrapsTrullRaam extends JFrame
{
    // Kelle kord on?
    private char kelleKord = 'X';
    private boolean gameOver = false;

   // Ruudustik
   private Cell[][] cells = new Cell[3][3];

   // Staatuse riba
   JLabel jlblStatus = new JLabel("X-i käik");

   /**
    * No-argument Constructor
    */
   public TripsTrapsTrullRaam()
   {
       // Ruudustiku paneel
       JPanel paneel = new JPanel(new GridLayout(3, 3, 0, 0));
       for (int i = 0; i < 3; i++)
           for (int j = 0; j < 3; j++)
               paneel.add(cells[i][j] = new Cell());
       
       paneel.setBorder(new LineBorder(Color.red, 1));
       jlblStatus.setBorder(new LineBorder(Color.yellow, 1));

       add(paneel, BorderLayout.CENTER);
       add(jlblStatus, BorderLayout.SOUTH);
   }

   /**
    * Kas ruudustik on täis?
    * @return True, kui ruudustik täis. Muul juhul on false.
    */
    public boolean isFull()
    {
       for (int i = 0; i < 3; i++)
           for (int j = 0; j < 3; j++)
               if (cells[i][j].getToken() == ' ')
                   return false;
       return true;
    }

   /**
    * Kas X või O on võitnud
    * @param token Märk võitja testimiseks
    * @return True, kui üks märk on võitnud. Muul juhul on false.
    */
   public boolean isWon(char token)
   {
       // check rows
       for (int i = 0; i < 3; i++)
           if ((cells[i][0].getToken() == token)
                   && (cells[i][1].getToken() == token)
                   && (cells[i][2].getToken() == token)) 
           {
               return true;
           }

       // check columns
       for (int j = 0; j < 3; j++)
           if ((cells[0][j].getToken() == token)
               && (cells[1][j].getToken() == token)
               && (cells[2][j].getToken() == token))
           {
               return true;
           }
       // check diagonal
       if ((cells[0][0].getToken() == token)
               && (cells[1][1].getToken() == token)
               && (cells[2][2].getToken() == token))
           {
               return true;
           }

       if ((cells[0][2].getToken() == token)
               && (cells[1][1].getToken() == token)
               && (cells[2][0].getToken() == token))
           {
               return true;
           }

       return false;
   }

    /**
    * Defineerib mängulaua ruudustiku ruudud.
    */
    public class Cell extends JPanel
    {
       // ruudu märk
       private char token = ' ';

       /**
        * Constructor
        */
       public Cell()
       {
           setBorder(new LineBorder(Color.black, 1));
           addMouseListener(new MyMouseListener());
       }

       /**
        * Loeb ruudu märki
        * @return Ruudus oleva märgi väärtus
        */
       public char getToken()
       {
           return token;
       }

       /**
        * Määrab ruudu märgi
        * @param c  Ruudu märgi väärtuse tähis
        */
       public void setToken(char c)
       {
           token = c;
           repaint();
       }

       @Override
       protected void paintComponent(Graphics g)
       {
           super.paintComponent(g);

           if (token == 'X')
           {
               g.drawLine(10, 10, getWidth() - 10, getHeight() - 10);
               g.drawLine(getWidth() - 10, 10, 10, getHeight() - 10);
           }

           else if (token == 'O')
           {
               g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
           }
       }

       private class MyMouseListener extends MouseAdapter
       {
           @Override
           public void mouseClicked(MouseEvent e)
           {
               if (gameOver)
                   return;
               
               // if the cell is empty and the game is not over
               if (token == ' ' && kelleKord != ' ')
                   setToken(kelleKord);

               // Kontrolli mängu staatust
               if (isWon(kelleKord))
               {
                   jlblStatus.setText(kelleKord + " võtis! Mäng läbi!");
                   kelleKord = ' ';
                   gameOver = true;
               }
               else if (isFull())
               {
                   jlblStatus.setText("Viik! Mäng läbi!");
                   kelleKord = ' ';
                   gameOver = true;
               }
               else
               {
                   kelleKord = (kelleKord == 'X') ? 'O' : 'X';
                   jlblStatus.setText(kelleKord + "-i kord.");
               }
           }
       } // end class MyMouseListener
    } // end class Cell
} // end class TripsTrapsTrullRaam
