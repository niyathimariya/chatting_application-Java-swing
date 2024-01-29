package source;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
public class cServer implements ActionListener 
{
    JTextField text1;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f=new JFrame();
    static DataOutputStream dataoutputstream;
    cServer()
    {
        f.setLayout(null);
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("images/backvector.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);//we need to add image over pannel hence call with p1
        back.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("images/person1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile =new JLabel(i6);
        profile.setBounds(40,20,25,25);
        p1.add(profile);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("images/videocamera.png"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video =new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);


        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("images/phone-icon-png-3.png"));
        Image i11=i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone =new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("images/dots_vector.png"));
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel norevert =new JLabel(i12);
        norevert.setBounds(420,20,20,30);
        p1.add(norevert);

        JLabel name=new JLabel("George");
        name.setBounds(110,15,100,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SANS_SERIF",Font.BOLD,24));
        p1.add(name);

        JLabel status=new JLabel("Active Now");
        status.setBounds(110,35,100,23);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SANS_SERIF",Font.BOLD,14));
        p1.add(status);

        a1= new JPanel();
        a1.setBounds(2,73,450,630);
        f.add(a1);
        
        text1=new JTextField();
        text1.setBounds(5,655,350,40);
        text1.setFont(new Font("SANS_SERIF",Font.PLAIN,16));
        f.add(text1);

        JButton send=new JButton("Send");
        send.setBounds(360,655,80,40);
        send.setBackground(new Color(7,94,84));
        send.addActionListener(this); //the action thaat needs to be performed written inside actionPerformed method.
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SANS_SERIF",Font.PLAIN,16));
        f.add(send);

        f.setSize(450,700);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        f.setLocation(200,50);
        f.setVisible(true);//needs to last statement as we want it to be the visible only after applying all the changes
    }
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String out=text1.getText();
            JLabel output=new JLabel(out);
            JPanel p2=formatLabel(out);
            //p2.add(output);
            a1.setLayout(new BorderLayout());
            JPanel right=new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);

            dataoutputstream.writeUTF(out);

            text1.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output=new JLabel("<html> <p style='width:150px'> "+ out +" </p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time =new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    
}
public class Server
{
    public static void main(String[] args)
    {
        DataOutputStream dataoutputstream;
        Box vertical=Box.createVerticalBox();
        JFrame f=new JFrame();
                new cServer();
        
        try
        {
            ServerSocket serversocket=new ServerSocket(6001);
            while(true)
            {
                Socket socket=serversocket.accept();
                DataInputStream datainputstream=new DataInputStream(socket.getInputStream());
                dataoutputstream=new DataOutputStream(socket.getOutputStream());
                while(true)
                {
                    String msg=datainputstream.readUTF();
                    JPanel panel=formatLabel(msg);

                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}