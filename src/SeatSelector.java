import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class SeatSelector { 
    int posx = 10, posy = 10;
    JLabel seats[][];
    boolean seatMatrix[][];
    JLabel SelectedSeats, SeatNums = new JLabel();
    public static JFrame f;
    JPanel ss = new JPanel();
    JLabel lNumSeats, lTotalAmt, lLegend[], lSymbol[];
    JButton bGoBack, bProceedToPay;
    public static String Movie;
    double TicketPrice, TotAmt;
    public static String seatString;
    String dbSeatString = "";
    
    
    SeatSelector(String MovieName){
        Movie = MovieName;
        f = new JFrame("Select Your Seats for :"+Movie);        
        TicketPrice = 120;  // Use SQL to fetch ticket price
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db1";
            Connection con = DriverManager.getConnection(url,"root","");
            Statement s = con.createStatement();
            String Query = "Select seatnumbers from tickets where movie='"+Movie+"'";
            ResultSet rs = s.executeQuery(Query);
            while(rs.next()){
                dbSeatString += rs.getString(1);
            }
            
        }
        
        catch(ClassNotFoundException e) {System.out.println(e);}
        catch(SQLException e) {System.out.println(e);}
        
        int r=12,c=9;
        ss.setBounds(10,10,9*40+10,12*25+10);
        ss.setLayout(null);
        seats = new JLabel[r][c];
        seatMatrix = new boolean [r][c];
        for (int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                seats[i][j] = new JLabel(((char)(i+65))+""+(j+1));
                seats[i][j].setBounds(posx,posy,35,15);
                seats[i][j].setOpaque(true);
                seats[i][j].setBackground(Color.GREEN);
                seats[i][j].setHorizontalAlignment(0);
                seats[i][j].setBorder(BorderFactory.createLineBorder(new Color(11, 110, 0),2,true));
                ss.add(seats[i][j]);
                seatMatrix[i][j] = false;
                //System.out.println(dbSeatString+"-----"+seats[i][j].getText().charAt(0)+"-"+seats[i][j].getText().charAt(1));
                if (dbSeatString.contains(seats[i][j].getText().charAt(0)+"-"+seats[i][j].getText().charAt(1))){
                    seats[i][j].setBackground(Color.GRAY);
                }
                else{
                seats[i][j].addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){ 
                        int ii=-1,jj=-1;
                        seatString = "<html>";
                        int numSeats=0;
                        if(e.getComponent().getBackground() == Color.red)
                            e.getComponent().setBackground(Color.GREEN);
                        else e.getComponent().setBackground(Color.red);
                        ss.setVisible(true);
                        JLabel t = (JLabel) e.getComponent();
                        //System.out.println(t.getText()+" - "+t.getBackground().equals(Color.GREEN));
                        //SeatNums.setText(SeatNums.getText()+t.getText()+", ");
                        
                        if(t.getBackground().equals(Color.RED)){
                            ii = t.getText().charAt(0) - 65;
                            jj = Integer.parseInt(""+t.getText().charAt(1))-1;
                            seatMatrix[ii][jj] = true;
                        }
                        if(t.getBackground().equals(Color.GREEN)){
                            ii = t.getText().charAt(0) - 65;
                            jj = Integer.parseInt(""+t.getText().charAt(1))-1;
                            seatMatrix[ii][jj] = false; 
                            
                        }
                       
                        for (int k =0; k<r; k++){
                            for(int l=0; l<c; l++){
                                if(seatMatrix[k][l]){seatString+=(char)(k+65)+"-"+(l+1)+", "; numSeats++;}
                                
                            }
                        }
                        seatString+="</html>";
                        //System.out.println(seatString);
                        SeatNums.setText(seatString);
                        lNumSeats.setText("Number of Seats Selected :"+numSeats);
                        lTotalAmt.setText("Total Amount : Rs."+(numSeats*TicketPrice));
                        TotAmt = numSeats*TicketPrice;
                    }
                });
                }
                posx+=40;
            }
            posy+=25;
            posx = 10;
        }
        ss.setVisible(true); 
        
        SeatNums.setBounds(450,60,300, 100);
        SeatNums.setFont(new java.awt.Font("Segoe UI", 0, 14));
        f.add(SeatNums);
        
        
        
    
        //f = new JFrame("Select Your Seats");
        f.setLayout(null);
        f.setSize(800,550);
        f.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        f.setResizable(false);
        f.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
            System.out.print("Exiting");
            f.dispose();
            }
            
        });
        
        
        f.add(ss);
        f.setVisible(true);
        ss.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        SelectedSeats = new JLabel("Selected Seats:");
        SelectedSeats.setBounds(450,20,150,30);
        SelectedSeats.setFont(new java.awt.Font("Segoe UI", 0, 16));
        f.add(SelectedSeats);   
        
        
        lNumSeats = new JLabel("Number of Seats Selected :");
        lNumSeats.setBounds(450, 220, 250, 30);
        lNumSeats.setFont(new java.awt.Font("Segoe UI", 0, 16));
        f.add(lNumSeats);
        
        
        lTotalAmt = new JLabel();
        lTotalAmt.setBounds(450, 270, 200, 30);
        lTotalAmt.setFont(new java.awt.Font("Segoe UI", 0, 16));
        f.add(lTotalAmt);
        
        bGoBack = new JButton("Go Back");
        bGoBack.setBounds(420, 400, 100, 35);
        bGoBack.setFont(new java.awt.Font("Segoe UI", 0, 14));
        bGoBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                System.out.print("Going Back");
                f.dispose();
                HomePageFrame.main(new String[1]);
            }
        });
        f.add(bGoBack);
        
        bProceedToPay = new JButton("Proceed To Pay");
        bProceedToPay.setBounds(550, 400, 160, 35);
        bProceedToPay.setFont(new java.awt.Font("Segoe UI", 0, 14));
        bProceedToPay.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                //f.dispose();
                f.setVisible(false);
                PaymentFrame.Caller("Movie", TotAmt);
            }
        });
        f.add(bProceedToPay);
        
        int[] legendPos = {30,350,200,30};
        lLegend = new JLabel[3];
        for(int i=0; i<3; i++){
            lLegend[i] = new JLabel();
            lLegend[i].setBounds(legendPos[0], legendPos[1]+(i*30), legendPos[2],legendPos[3]);
            lLegend[i].setFont(new java.awt.Font("Segoe UI", 0, 18));
            f.add(lLegend[i]);
        }
        lLegend[0].setText("Available Seats");
        lLegend[1].setText("Unavailable Seats");
        lLegend[2].setText("Selected Seats");
        
        lSymbol = new JLabel[3];
        for(int i=0; i<3; i++){
            lSymbol[i] = new JLabel();
            lSymbol[i].setBounds(legendPos[0]+210, legendPos[1]+(i*30),35,15);
            lSymbol[i].setOpaque(true);
            lSymbol[i].setHorizontalAlignment(0);
            lSymbol[i].setBorder(BorderFactory.createLineBorder(new Color(11, 110, 0),2,true));
            f.add(lSymbol[i]);
        }
        lSymbol[0].setBackground(Color.GREEN);
        lSymbol[1].setBackground(Color.GRAY);
        lSymbol[2].setBackground(Color.RED);
        
    }
    
    public static void main(String[]ar){new SeatSelector("movie");}
    
    public static void caller(String moviename){
        Movie = moviename;
        new SeatSelector(moviename);
    }
}
