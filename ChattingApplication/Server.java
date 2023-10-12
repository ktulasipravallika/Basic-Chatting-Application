package ChattingApplication;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

public class Server implements ActionListener {

    JTextField text1;
    JPanel textArea;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {

        f.setTitle("Chatting Application");

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 500, 60);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(7, 20, 25, 25);
        p1.setLayout(null);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(50, 5, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(370, 15, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(425, 15, 30, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(470, 15, 30, 30);
        p1.add(more);

        JLabel name = new JLabel("Person_1");
        name.setBounds(120, 15, 150, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Times New Roman", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(120, 38, 150, 20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        p1.add(status);

        textArea = new JPanel();
        textArea.setBounds(2, 62, 496, 670);
        textArea.setBackground(Color.LIGHT_GRAY);
        f.add(textArea);

        text1 = new JTextField();
        text1.setBounds(0, 730, 390, 52);
        text1.setBackground(Color.WHITE);
        text1.setForeground(Color.BLACK);
        text1.setFont(new Font("Times New Roman", Font.ITALIC, 15));
        f.add(text1);

        

        JButton send = new JButton("Send");
        send.setBackground(Color.BLACK);
        send.setBounds(387, 732, 112, 50);
        send.setFont(new Font("Serif", Font.BOLD, 15));
        send.addActionListener(this);
        f.add(send);

        f.setLayout(null);
        f.getContentPane().setBackground(Color.WHITE);
        f.setSize(500, 780);
        f.setUndecorated(true);
        f.setVisible(true);
        f.setLocation(70, 20);

    }

    public void actionPerformed(ActionEvent ae) {


        try{
            textArea.setLayout(new BorderLayout());

            String chatText = text1.getText();
            
            JPanel p2 = formatLabel(chatText);
          
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            textArea.add(vertical, BorderLayout.PAGE_START);
            
            dout.writeUTF(chatText);

            text1.setText(" ");
            f.repaint();
            f.invalidate();
            f.validate();
        }

        catch(Exception e){
            e.printStackTrace();
        }
            
    }


    public static JPanel formatLabel(String outText){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style = \"width: 150px\">" + outText + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN , 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;

    }

    public static void main(String[] args) {
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left =new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                    
                }            
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }

}



            
            

            

            
            

            

            
