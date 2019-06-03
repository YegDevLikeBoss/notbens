import com.sun.deploy.panel.JreTableModel;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame{

    public JTabbedPane tab;

    public JPanel game1Panel;
    public JPanel game2Panel;

    public JTextArea helpLabel;
    public JPanel game;

    public String game1Help;
    public String game2Help;

    public Move move;

    public ImageIcon king;
    public ImageIcon qween;

    public Font fcust, fc;

    public Menu() {
        super("Курсовая работа студента егояна А.А.");

        game1Help = "* Вначале выбирается кликом мыши клетка с фигурой, затем, также кликом мыши, куда она ходит.\n* Фигура ходит только в свободное поле по своим правилам.\n* Порядок ходов белыми и черными фигурами не регламентируется.\n* Игра заканчивается, когда фигуры переставлены";
        game2Help = "* Фишка может перешагивать другую фишку по горизонтали или вертикали. \n* Если фишка перешагнула другую фишку, то та фишка, которую перешагнули, убирается с доски.\n* Кликом мыши выбирается фишка, затем выбирается пустая клетка, куда ходить выбранной фишке.\n* Лучший результат достигается тогда, когда на доске осталась одна фишка. ";


        try {
            fcust = Font.createFont(Font.TRUETYPE_FONT, new File("res/ARIALUNI.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fcust);

        } catch (FontFormatException | IOException e) {
            fcust = new Font("TimesRoman", Font.PLAIN, 25);
            System.out.println("ttf font not registered" + e);
        }

        fc = new Font (fcust.getName(), Font.PLAIN, 25);

        king = new ImageIcon("king.png");
        qween = new ImageIcon("qween.png");

        setDefaultCloseOperation( EXIT_ON_CLOSE );

        JMenuBar mainMenu = createMenuBar();

        setJMenuBar(mainMenu);

        move = new Move();

        tab = new JTabbedPane();

        game1Panel = new JPanel();
        game1Panel.add(createGame1());

        game2Panel = createGame2();

        game = game1Panel;
        JPanel help = new JPanel();

        helpLabel = new JTextArea();
        helpLabel.setEditable(false);
        helpLabel.setText(game1Help);

        help.add(helpLabel);

        game.setName("Переставь фигуры");
        help.setName("Помощь");

        tab.add(help);
        tab.add(game);

        this.add(tab);

        setSize(1000, 700);
        setVisible(true);
    }

    private JTable init(JTable arg) {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == 1) {
                    arg.setValueAt('♛', i, j);
                } else
                if (i == 3) {
                    arg.setValueAt('♔', i, j);
                } else
                    arg.setValueAt(' ', i, j);
            }
        }

        arg.setValueAt('█', 0, 0);
        arg.setValueAt('A', 0, 1);
        arg.setValueAt('B', 0, 2);
        arg.setValueAt('C', 0, 3);
        arg.setValueAt('1', 1, 0);
        arg.setValueAt('2', 2, 0);
        arg.setValueAt('3', 3, 0);

        return arg;
    }

    public boolean game2Win(JTable arg) {
        int k = 0;

        for (int i = 0 ; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (arg.getValueAt(i, j).toString().charAt(0)=='*')
                    k++;
            }
        }

        if (k == 1)
            return true;

        return false;
    }

    public boolean game1Win(JTable arg) {

        if ((arg.getValueAt(1, 1).toString().charAt(0)=='♔') &&
                (arg.getValueAt(1, 2).toString().charAt(0)=='♔') &&
                (arg.getValueAt(1, 3).toString().charAt(0)=='♔') &&
                (arg.getValueAt(3, 1).toString().charAt(0)=='♛') &&
                (arg.getValueAt(3, 2).toString().charAt(0)=='♛') &&
                (arg.getValueAt(3, 3).toString().charAt(0)=='♛')
        )
        {
            return true;
        }

        return false;
    }

    public boolean game2Loose(JTable arg) {

        boolean ret = true;

        for (int i = 1 ; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                if (arg.getValueAt(i, j).toString().charAt(0)=='*') {
                    if (arg.getValueAt(i, j-1).toString().charAt(0)=='*')
                    {ret = false;} else
                    if (arg.getValueAt(i+1, j).toString().charAt(0)=='*')
                    {ret = false;} else
                    if (arg.getValueAt(i, j+1).toString().charAt(0)=='*')
                    {ret = false;} else
                    if (arg.getValueAt(i-1, j).toString().charAt(0)=='*')
                    {ret = false;}

                }
            }
        }

        for (int i = 2 ; i < 5; i++) {
            if (arg.getValueAt(i, 0).toString().charAt(0)=='*') {
                if (arg.getValueAt(i-1, 0).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(i,1).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(i+1,0).toString().charAt(0)=='*')
                {ret = false;}

            }
        }

        for (int i = 2 ; i < 5; i++) {
            if (arg.getValueAt(i, 6).toString().charAt(0)=='*') {
                if (arg.getValueAt(i-1, 6).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(i,5).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(i+1,6).toString().charAt(0)=='*')
                {ret = false;}

            }
        }

        for (int i = 2 ; i < 5; i++) {
            if (arg.getValueAt(0, i).toString().charAt(0)=='*') {
                if (arg.getValueAt(0, i-1).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(1, i).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(0, i+1).toString().charAt(0)=='*')
                {ret = false;}

            }
        }

        for (int i = 2 ; i < 5; i++) {
            if (arg.getValueAt(6, i).toString().charAt(0)=='*') {
                if (arg.getValueAt(6, i-1).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(5, i).toString().charAt(0)=='*')
                {ret = false;} else
                if (arg.getValueAt(6, i+1).toString().charAt(0)=='*')
                {ret = false;}

            }
        }

        return ret;
    }

    public JTable makeTable2(){
        JTable jt = new JTable(7, 7);

        Font f = new Font("TimesRoman", Font.PLAIN, 47);

        jt.setRowHeight(50);
        jt.setFont(f);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( JLabel.CENTER );
        renderer.setVerticalAlignment( JLabel.CENTER );

        for (int i = 0 ; i < 7; i++){
            jt.getColumnModel().getColumn(i).setPreferredWidth(50);
            jt.getColumnModel().getColumn(i).setCellRenderer(renderer);

        }

        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (((i == 0) || (i == 1) || (i == 5) || (i == 6))&&((j == 0) || (j == 1) || (j == 5) || (j == 6)))
                    jt.setValueAt('█', i, j);
                else jt.setValueAt('*', i, j);
            }
        }

        jt.setValueAt(' ', 3, 3);

        return jt;
    }

    public JTable makeTable1(){

        JTable jt = new JTable(4 , 4);

        jt.setRowHeight(100);
        jt.setRowHeight(0, 30);

        jt.setFont(fc);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( JLabel.CENTER );
        renderer.setVerticalAlignment( JLabel.CENTER );
        renderer.setFont(new Font("TimesRoman", Font.PLAIN, 25));

        renderer.repaint();

        jt.getColumnModel().getColumn(0).setPreferredWidth(30);
        jt.getColumnModel().getColumn(0).setCellRenderer(renderer);


        for (int i = 1 ; i < 4; i++){
            jt.getColumnModel().getColumn(i).setPreferredWidth(100);
            jt.getColumnModel().getColumn(i).setCellRenderer(renderer);

        }

        jt = init(jt);

        move.clearmove();

        return jt;
    }

    public void Game1Check(JTable arg)
    {
        if (game1Win(arg)) {
            System.out.println("win");
            JOptionPane.showMessageDialog(getFrame(), "Фигуры переставлены, конец игры",
                    "alert", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public JPanel createGame1() {

        move.clearmove();

        JPanel game = new JPanel();

        JTable jt = makeTable1();

        String column_names[]= {"Фигура","Клетка n-1","Клетка n"};
        DefaultTableModel tm = new DefaultTableModel(column_names,150);
        JTable jt2 = new JTable(tm);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(fc);
        jt2.getColumnModel().getColumn(0).setCellRenderer(renderer);

        JScrollPane panel = new JScrollPane(jt2);

        panel.setPreferredSize(new Dimension(300, 500));

        jt.setEnabled( false );

        jt.addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    int row = jt.rowAtPoint(evt.getPoint());
                                    int col = jt.columnAtPoint(evt.getPoint());
                                    if (row >= 0 && col >= 0) {

                                        char val = jt.getValueAt(row, col).toString().charAt(0);

                                        System.out.println("row " + row + " col " + col + " val " + val);

                                        if (move.firstMove)
                                        {
                                            if ((val == '♔')||(val=='♛'))
                                            {
                                                move.move(row, col, val);
                                            }
                                        } else {
                                            if (val == ' ') {
                                                move.move(row, col, val);

                                                if (move.delta() == 0)
                                                {
                                                    move.unmove();
                                                } else {
                                                    if (jt.getValueAt( move.row1, move.col1).toString().charAt(0) == '♔')
                                                    {
                                                        if ((move.deltaX()<=1)&&(move.deltaY()<=1))
                                                        {
                                                            jt.setValueAt( ' ', move.row1, move.col1);
                                                            jt.setValueAt( '♔', move.row2, move.col2);

                                                            try {


                                                                jt2.setValueAt('♔', move.increment, 0);
                                                                jt2.setValueAt(move.charify(move.col1) + Integer.toString(move.row1), move.increment, 1);
                                                                jt2.setValueAt(move.charify(move.col2) + Integer.toString(move.row2), move.increment, 2);

                                                            } catch (ArrayIndexOutOfBoundsException e)
                                                            {
                                                                move.clearmove();

                                                                DefaultTableModel dm = (DefaultTableModel)jt2.getModel();
                                                                dm.getDataVector().removeAllElements();
                                                                dm.setRowCount(150);
                                                                dm.fireTableDataChanged(); // notifies the JTable that the model has changed


                                                                jt2.setValueAt('♔', move.increment, 0);
                                                                jt2.setValueAt(move.charify(move.col1) + Integer.toString(move.row1), move.increment, 1);
                                                                jt2.setValueAt(move.charify(move.col2) + Integer.toString(move.row2), move.increment, 2);
                                                            }

                                                            move.increase();



                                                        }
                                                    } else
                                                    if (jt.getValueAt( move.row1, move.col1).toString().charAt(0) == '♛')
                                                    {
                                                        if ((move.deltaX()<=1)&&(move.deltaY()<=1))
                                                        {
                                                              jt.setValueAt(' ', move.row1, move.col1);
                                                              jt.setValueAt('♛', move.row2, move.col2);

                                                              try {
                                                                  jt2.setValueAt('♛', move.increment, 0);
                                                                  jt2.setValueAt(move.charify(move.col1) + Integer.toString(move.row1), move.increment, 1);
                                                                  jt2.setValueAt(move.charify(move.col2) + Integer.toString(move.row2), move.increment, 2);
                                                              } catch (ArrayIndexOutOfBoundsException e) {
                                                                  move.clearmove();

                                                                  DefaultTableModel dm = (DefaultTableModel)jt2.getModel();
                                                                  dm.getDataVector().removeAllElements();
                                                                  dm.setRowCount(150);
                                                                  dm.fireTableDataChanged(); // notifies the JTable that the model has changed


                                                                  jt2.setValueAt('♛', move.increment, 0);
                                                                  jt2.setValueAt(move.charify(move.col1) + Integer.toString(move.row1), move.increment, 1);
                                                                  jt2.setValueAt(move.charify(move.col2) + Integer.toString(move.row2), move.increment, 2);
                                                              }
                                                            move.increase();
                                                        }
                                                        if (((move.deltaX()==2)||(move.deltaX()==0))&&((move.deltaY()==2)||(move.deltaY()==0)))
                                                        {
                                                            if ((move.deltaX() == 0)&& (jt.getValueAt(move.row2, move.col2 - (move.diffY() / move.deltaY())).toString().charAt(0)==' ')) {
                                                                System.out.println("here");
                                                                jt.setValueAt( ' ', move.row1, move.col1);
                                                                jt.setValueAt( '♛', move.row2, move.col2);


                                                                try {
                                                                jt2.setValueAt('♛', move.increment,0);
                                                                jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    move.clearmove();

                                                                    DefaultTableModel dm = (DefaultTableModel)jt2.getModel();
                                                                    dm.getDataVector().removeAllElements();
                                                                    dm.setRowCount(150);
                                                                    dm.fireTableDataChanged(); // notifies the JTable that the model has changed


                                                                    jt2.setValueAt('♛', move.increment,0);
                                                                    jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                    jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                }
                                                                move.increase();
                                                            } else
                                                            if ((move.deltaY() == 0)&&(jt.getValueAt(move.row2 - (move.diffX() / move.deltaX()), move.col2).toString().charAt(0)==' ')) {
                                                                System.out.println("here2");
                                                                jt.setValueAt( ' ', move.row1, move.col1);
                                                                jt.setValueAt( '♛', move.row2, move.col2);

                                                                try {
                                                                jt2.setValueAt('♛', move.increment,0);
                                                                jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    move.clearmove();

                                                                    DefaultTableModel dm = (DefaultTableModel)jt2.getModel();
                                                                    dm.getDataVector().removeAllElements();
                                                                    dm.setRowCount(150);
                                                                    dm.fireTableDataChanged(); // notifies the JTable that the model has changed


                                                                    jt2.setValueAt('♛', move.increment,0);
                                                                    jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                    jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                }
                                                                    move.increase();
                                                            } else
                                                            if (((move.deltaX()==2)&&(move.deltaY()==2))&&(jt.getValueAt(move.row2 - (move.diffX() / move.deltaX()), move.col2 - (move.diffY() / move.deltaY())).toString().charAt(0)==' ')) {
                                                                jt.setValueAt(' ', move.row1, move.col1);
                                                                jt.setValueAt('♛', move.row2, move.col2);

                                                                try {
                                                                jt2.setValueAt('♛', move.increment,0);
                                                                jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                } catch (ArrayIndexOutOfBoundsException e) {
                                                                    move.clearmove();

                                                                    DefaultTableModel dm = (DefaultTableModel)jt2.getModel();
                                                                    dm.getDataVector().removeAllElements();
                                                                    dm.setRowCount(150);
                                                                    dm.fireTableDataChanged(); // notifies the JTable that the model has changed

                                                                    jt2.setValueAt('♛', move.increment,0);
                                                                    jt2.setValueAt(move.charify(move.col1)+Integer.toString(move.row1), move.increment,1);
                                                                    jt2.setValueAt(move.charify(move.col2)+Integer.toString(move.row2), move.increment,2);
                                                                }

                                                                move.increase();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    Game1Check(jt);
                                    if (game1Win(jt)) {
                                        for (int i = 0; i < 4; i++)
                                        {
                                            for (int j = 0; j < 4; j++)
                                            {
                                                if (i == 1) {
                                                    jt.setValueAt('♛', i, j);
                                                } else
                                                if (i == 3) {
                                                    jt.setValueAt('♔', i, j);
                                                } else
                                                    jt.setValueAt(' ', i, j);
                                            }
                                        }

                                        jt.setValueAt('█', 0, 0);
                                        jt.setValueAt('А', 0, 1);
                                        jt.setValueAt('В', 0, 2);
                                        jt.setValueAt('С', 0, 3);
                                        jt.setValueAt('1', 1, 0);
                                        jt.setValueAt('2', 2, 0);
                                        jt.setValueAt('3', 3, 0);

                                        int i = 0;

                                        while (jt2.getValueAt(i, 2)!=null) {
                                            jt2.setValueAt(null, i, 0);
                                            jt2.setValueAt(null, i, 1);
                                            jt2.setValueAt(null, i, 2);

                                            i++;
                                        }

                                        move.clearmove();

                                    }
                                }
                            });

        JButton gBtn1 = new JButton("Начать игру");

        JLabel l1 = new JLabel("Игровое поле");
        JLabel l2 = new JLabel("Таблица ходов");

        gBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        if (i == 1) {
                            jt.setValueAt('♛', i, j);
                        } else
                        if (i == 3) {
                            jt.setValueAt('♔', i, j);
                        } else
                            jt.setValueAt(' ', i, j);
                    }
                }

                jt.setValueAt('█', 0, 0);
                jt.setValueAt('A', 0, 1);
                jt.setValueAt('B', 0, 2);
                jt.setValueAt('C', 0, 3);
                jt.setValueAt('1', 1, 0);
                jt.setValueAt('2', 2, 0);
                jt.setValueAt('3', 3, 0);

                int i = 0;

                while (jt2.getValueAt(i, 2)!=null) {
                    jt2.setValueAt(null, i, 0);
                    jt2.setValueAt(null, i, 1);
                    jt2.setValueAt(null, i, 2);

                    i++;
                }

                move.clearmove();

            }});


        game.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        game.add(l1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        game.add(l2, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        game.add(jt, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        game.add(panel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        game.add(gBtn1, c);



        return game;
    }

    public JPanel createGame2() {

        JPanel game = new JPanel();

        JTable jt = makeTable2();

        jt.setEnabled( false );

        jt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jt.rowAtPoint(evt.getPoint());
                int col = jt.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {

                    char val = jt.getValueAt(row, col).toString().charAt(0);

                    System.out.println( "row " + row + " col " + col + " val " + val);

                    if (move.firstMove)
                    {
                        if (val == '*')
                        {
                            move.move(row, col, val);
                        }
                    } else
                    {
                        if (val == ' ')
                        {
                            move.move(row, col, val);
                            if (move.delta() == 0)
                            {
                                move.unmove();
                            } else
                                if (((move.deltaX()==2)&&(move.deltaY()==0))||((move.deltaX()==0)&&(move.deltaY()==2)))
                                {
                                    if ((move.deltaX() == 0)&& (jt.getValueAt(move.row2, move.col2 - (move.diffY() / move.deltaY())).toString().charAt(0)=='*')) {
                                        jt.setValueAt(' ', move.row2, move.col2 - (move.diffY() / move.deltaY()));
                                        jt.setValueAt( ' ', move.row1, move.col1);
                                        jt.setValueAt( '*', move.row2, move.col2);
                                    }
                                    if ((move.deltaY() == 0)&&(jt.getValueAt(move.row2 - (move.diffX() / move.deltaX()), move.col2).toString().charAt(0)=='*')) {
                                        jt.setValueAt(' ', move.row2 - (move.diffX() / move.deltaX()), move.col2 );
                                        jt.setValueAt( ' ', move.row1, move.col1);
                                        jt.setValueAt( '*', move.row2, move.col2);
                                    }


                                    System.out.println("dx " + move.deltaX() + " dy " + move.deltaY() + " dfx " + move.diffX() + " dfy " + move.diffY());
                                    jt.updateUI();

                                    if (game2Win(jt)) {
                                        System.out.println("win");
                                        JOptionPane.showMessageDialog(getFrame(), "Победа",
                                                "alert", JOptionPane.INFORMATION_MESSAGE);
                                    } else
                                    if (game2Loose(jt)) {
                                        System.out.println("loose");
                                        JOptionPane.showMessageDialog(getFrame(), "Игра закончена",
                                                "alert", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                        }

                    }

                }
            }
        });

        JButton gBtn2 = new JButton("Начать игру");

        gBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                for (int i = 0; i < 7; i++)
                {
                    for (int j = 0; j < 7; j++)
                    {
                        if (((i == 0) || (i == 1) || (i == 5) || (i == 6))&&((j == 0) || (j == 1) || (j == 5) || (j == 6)))
                            jt.setValueAt('█', i, j);
                        else jt.setValueAt('*', i, j);
                    }
                }

                jt.setValueAt(' ', 3, 3);
            }});

        game.add(jt);
        game.add(gBtn2);

        return game;

    }

    public JMenuBar createMenuBar() {

        JMenuBar mainMenu = new JMenuBar();

        JMenu games = new JMenu("Игры");

        JMenu about = new JMenu("О игре");
        JMenuItem exit = new JMenuItem("Выход");
        JMenuItem game1 = new JMenuItem("Переставь фигуры");
        JMenuItem game2 = new JMenuItem("Йога");

        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        game1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tab.setComponentAt(1, game1Panel);
                tab.setSelectedIndex(1);
                tab.setTitleAt(1, "Переставь фигуры");
                helpLabel.setText(game1Help);

                game1Panel = createGame1();

                tab.updateUI();

            }
        });

        game2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tab.setComponentAt(1, game2Panel);
                tab.setSelectedIndex(1);
                tab.setTitleAt(1, "Йога");
                helpLabel.setText(game2Help);

                game2Panel = createGame2();

                tab.updateUI();

            }
        });

        about.add(game1);
        about.add(game2);

        games.add(about);
        games.add(exit);

        mainMenu.add(games);

        return mainMenu;
    }

    JFrame getFrame() {
        return this;
    }

}
