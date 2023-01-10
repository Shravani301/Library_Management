package library21;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;
  public class example{  
		 public static class ex{
		        public static int days=0;
		            }
		
	public static void main(String args[]){
	 selecting();
	}
	public static void selecting() {
	     JFrame f=new JFrame("selcet option to login");
		 JButton Admin=new JButton("ADMIN");
		    Admin.setBounds(20,20,120,25);//x axis, y axis, width, height 
	        Admin.addActionListener(new ActionListener() { //Perform action
	            public void actionPerformed(ActionEvent e){
	              login(); 
	                 }
	              }
	           );
	        JButton student=new JButton("STUDENT");
		    student.setBounds(20,60,120,25);//x axis, y axis, width, height 
	        student.addActionListener(new ActionListener() { //Perform action
	            public void actionPerformed(ActionEvent e){
	              login(); 
	                 }
	              }
	           );
	        f.add(Admin);
	       f.add(student);
	       
	       f.setSize(300,180);//400 width and 180 height  
	        f.setLayout(null);//using no layout managers  
	        f.setVisible(true);//making the frame visible 
	        f.setLocationRelativeTo(null);
 
	        }
	        public static void login() {

		final JFrame f=new JFrame("Login");
		JLabel l1,l2;  
        l1=new JLabel("Username");  //Create label Username
        l1.setBounds(30,15, 100,30); //x axis, y axis, width, height 
         
        l2=new JLabel("Password");  //Create label Password
        l2.setBounds(30,50, 100,30);    
         
        final JTextField F_user = new JTextField(); //Create text field for username
        F_user.setBounds(110, 15, 200, 30);
             
        final JPasswordField F_pass=new JPasswordField(); //Create text field for password
        
        F_pass.setBounds(110, 50, 200, 30);
     
		JButton login_but=new JButton("Login");//creating instance of JButton for Login Button
        login_but.setBounds(130,90,80,25);//Dimensions for button
        login_but.addActionListener(new ActionListener() {  //Perform action
             
            public void actionPerformed(ActionEvent e){ 
            	String username = F_user.getText(); //Store username entered by the user in the variable "username"
                @SuppressWarnings("deprecation")
				String password = F_pass.getText(); //Store password entered by the user in the variable "password"
                 
                if(username.equals("")) //If username is null
                {
                    JOptionPane.showMessageDialog(null,"Please enter username"); //Display dialog box with the message
                } 
                else if(password.equals("")) //If password is null
                {
                    JOptionPane.showMessageDialog(null,"Please enter password"); //Display dialog box with the message
                }
                else { 
                	Connection con=connect();
     try{  
    	 Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
         stmt.executeUpdate("USE LIBRARY1");
	     String st = ("SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+password+"'"); //Retreive username and passwords from users
         ResultSet rs = stmt.executeQuery(st); //Execute query
         if(rs.next()==false) { //Move pointer below
           System.out.print("No user");  
           JOptionPane.showMessageDialog(null,"Wrong Username/Password!"); //Display Message
          }
         else {
           f.dispose();
           rs.beforeFirst();  //Move the pointer above
           while(rs.next())
             {
               String admin = rs.getString("ADMIN"); //user is admin
               String UID = rs.getString("UID"); //Get user ID of the user
               if(admin.equals("1")) { //If boolean value 1
                  admin_menu(); //redirect to admin menu
               }
               else {
        	      user_menu(UID);
               }
              }
            }
          }
     catch(Exception r){
    	 System.out.println(r);}  
        }
      }
   });
        f.add(login_but);
        f.add(F_pass);//adding button in JFrame  
        f.add(F_user);  //add user	
        f.add(l1);  // add label1 i.e. for username
        f.add(l2); // add label2 i.e. for password
        
        f.setSize(400,180);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible 
        f.setLocationRelativeTo(null);
       
	}
public static void admin_menu() {
        
        
        JFrame f=new JFrame("Admin Functions");
        //************************(1)***************************
        JButton create_but=new JButton("Create/Reset");//creating instance of JButton to create or reset database
        create_but.setBounds(20,20,120,25);//x axis, y axis, width, height 
        create_but.addActionListener(new ActionListener() { //Perform action
            public void actionPerformed(ActionEvent e){
                 
                create(); //Call create function
                JOptionPane.showMessageDialog(null,"Database Created/Reset!"); //Open a dialog box and display the message
                 
            }
        });
        //*****************(2)*********************
        JButton view_but=new JButton("View Books");//creating instance of JButton to view books
        view_but.setBounds(20,60,120,25);//x axis, y axis, width, height 
        view_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){                     
                    JFrame f = new JFrame("Books Available"); 
                    //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                     
                     
                    Connection connection = connect(); //connect to database
                    String sql="select * from BOOKS"; //select all books 
                    try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1"); //use database
                        stmt=connection.createStatement();
                        ResultSet rs=stmt.executeQuery(sql);
                        JTable book_list= new JTable(); //view data in table format
                        book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                        //mention scroll bar
                        JScrollPane scrollPane = new JScrollPane(book_list); 
         
                        f.add(scrollPane); //add scrollpane
                        f.setSize(800, 400); //set size for frame
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }             
                 
                //JFrame f = new JFrame("Books Available"); 
                f.setSize(600,400);//400 width and 500 height  
                f.setLayout(null);//using no layout managers  
                f.setVisible(true);//making the frame visible 
                f.setLocationRelativeTo(null);
               
            }}); 
        //***********************(3)*************************
        JButton users_but=new JButton("View Users");//creating instance of JButton to view users
        users_but.setBounds(20,100,120,25);//x axis, y axis, width, height 
        users_but.addActionListener(new ActionListener() { //Perform action on click button
            public void actionPerformed(ActionEvent e){
                     
                    JFrame f = new JFrame("Users List");
               //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                     Connection connection = connect();
                    String sql="select * from users"; //retrieve all users
                    try {
                        Statement stmt = connection.createStatement();
                        // stmt.executeUpdate("use sonoo"); //use database
                        stmt=connection.createStatement();
                        ResultSet rs=stmt.executeQuery(sql);
                        JTable book_list= new JTable();
                        book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                        //mention scroll bar
                        JScrollPane scrollPane = new JScrollPane(book_list);
     
                        f.add(scrollPane); //add scrollpane
    
                        f.setSize(600,400); //set size for frame
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(null, e1);
                    }               
                 }
            }
        );  
     //*********************(4)****************************  
        JButton issued_but=new JButton("View Issued Books");//creating instance of JButton to view the issued books
        issued_but.setBounds(280,20,140,25);//x axis, y axis, width, height 
        issued_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    JFrame f = new JFrame("Users List");
                    //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                          
                    Connection connection = connect();
                    String sql="select * from issued";
                    try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1");
                        stmt=connection.createStatement();
                        ResultSet rs=stmt.executeQuery(sql);
                        JTable book_list= new JTable();
                        book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                         
                        JScrollPane scrollPane = new JScrollPane(book_list);
     
                        f.add(scrollPane);
                        f.setSize(800, 400);
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }       
                                 
        }
            }
        );
   //**************************(5)*************************
        JButton add_user=new JButton("Add User"); //creating instance of JButton to add users
        add_user.setBounds(280,60,140,25); //set dimensions for button
         
        add_user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    final JFrame g = new JFrame("Enter User Details"); //Frame to enter user details
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //Create label 
                    JLabel l1,l2;  
                    l1=new JLabel("Username");  //label 1 for username
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("Password");  //label 2 for password
                    l2.setBounds(30,50, 100,30); 
                     
                    //set text field for username 
                    final JTextField F_user = new JTextField();
                    F_user.setBounds(110, 15, 200, 30);
                     
                    //set text field for password
                    final JPasswordField F_pass=new JPasswordField();
                    F_pass.setBounds(110, 50, 200, 30);
                    //set radio button for admin
                    final JRadioButton a1 = new JRadioButton("Admin");
                    a1.setBounds(55, 80, 200,30);
                    //set radio button for user
                    JRadioButton a2 = new JRadioButton("User");
                    a2.setBounds(130, 80, 200,30);
                    //add radio buttons
                    ButtonGroup bg=new ButtonGroup();    
                    bg.add(a1);bg.add(a2);  
                     
                                     
                    JButton create_but=new JButton("Create");//creating instance of JButton for Create 
                    create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                         
                        String username = F_user.getText();
                        String password = F_pass.getText();
                        Boolean admin = false;
                         
                        if(a1.isSelected()) {
                            admin=true;
                        }
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1");
                         stmt.executeUpdate("INSERT INTO USERS(USERNAME,PASSWORD,ADMIN) VALUES ('"+username+"','"+password+"',"+admin+")");
                         JOptionPane.showMessageDialog(null,"User added!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                         
                     
                        g.add(create_but);
                        g.add(a2);
                        g.add(a1);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_user);
                        g.add(F_pass);
                        g.setSize(350,200);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                     
                     
        }
        });
//************************(6)*********************************     
        JButton add_book=new JButton("Add Book"); //creating instance of JButton for adding books
        add_book.setBounds(280,100,140,25); 
         
        add_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    //set frame wot enter book details
                    final JFrame g = new JFrame("Enter Book Details");
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // set labels
                    JLabel l1,l2,l3;  
                    l1=new JLabel("Book Name");  //lebel 1 for book name
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("Genre");  //label 2 for genre
                    l2.setBounds(30,53, 100,30); 
                     
                    l3=new JLabel("Price");  //label 2 for price
                    l3.setBounds(30,90, 100,30); 
                     
                    //set text field for book name
                    final JTextField F_bname = new JTextField();
                    F_bname.setBounds(110, 15, 200, 30);
                     
                    //set text field for genre 
                    final JTextField F_genre=new JTextField();
                    F_genre.setBounds(110, 53, 200, 30);
                    //set text field for price
                    final JTextField F_price=new JTextField();
                    F_price.setBounds(110, 90, 200, 30);
                             
                     
                    JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
                    create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                        // assign the book name, genre, price
                        String bname = F_bname.getText();
                        String genre = F_genre.getText();
                        String price = F_price.getText();
                        //convert price of integer to int
                        int price_int = Integer.parseInt(price);
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1");
                         stmt.executeUpdate("INSERT INTO BOOKS(BNAME,GENRE,PRICE) VALUES ('"+bname+"','"+genre+"',"+price_int+")");
                         JOptionPane.showMessageDialog(null,"Book added!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                                     
                        g.add(l3);
                        g.add(create_but);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_bname);
                        g.add(F_genre);
                        g.add(F_price);
                        g.setSize(350,200);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                                 
        }
        });
  //***************************(7)*************************************
        JButton issue_book=new JButton("Issue Book"); //creating instance of JButton to issue books
        issue_book.setBounds(280,140,140,25); 
         
        issue_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    //enter details
                    final JFrame g = new JFrame("Enter Details");
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //create labels
                    JLabel l1,l2,l3,l4;  
                    l1=new JLabel("Book ID(BID)");  // Label 1 for Book ID
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("User ID(UID)");  //Label 2 for user ID
                    l2.setBounds(30,53, 100,30); 
                     
                    l3=new JLabel("Period(days)");  //Label 3 for period
                    l3.setBounds(30,90, 100,30); 
                     
                    l4=new JLabel("Issued Date(DD-MM-YYYY)");  //Label 4 for issue date
                    l4.setBounds(30,127, 150,30); 
                     
                    final JTextField F_bid = new JTextField();
                    F_bid.setBounds(110, 15, 200, 30);
                     
                     
                    final JTextField F_uid=new JTextField();
                    F_uid.setBounds(110, 53, 200, 30);
                     
                    final JTextField F_period=new JTextField();
                    F_period.setBounds(110, 90, 200, 30);
                     
                    final JTextField F_issue=new JTextField();
                    F_issue.setBounds(180, 130, 130, 30);   
     
                     
                    JButton create_but=new JButton("Submit");//creating instance of JButton  
                    create_but.setBounds(130,170,80,25);//x axis, y axis, width, height 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                         
                        String uid = F_uid.getText();
                        String bid = F_bid.getText();
                        String period = F_period.getText();
                        String issued_date = F_issue.getText();
     
                        int period_int = Integer.parseInt(period);
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1");
                         stmt.executeUpdate("INSERT INTO ISSUED(UID,BID,ISSUED_DATE,PERIOD) VALUES ('"+uid+"','"+bid+"','"+issued_date+"',"+period_int+")");
                         JOptionPane.showMessageDialog(null,"Book Issued!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                         
                     
                        g.add(l3);
                        g.add(l4);
                        g.add(create_but);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_uid);
                        g.add(F_bid);
                        g.add(F_period);
                        g.add(F_issue);
                        g.setSize(350,250);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                     
                     
        }
        });
 //***************************(8)**********************************    
        JButton return_book=new JButton("Return Book"); //creating instance of JButton to return books
        return_book.setBounds(20,140,120,25); 
         
        return_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    final JFrame g = new JFrame("Enter Details");
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //set labels 
                    JLabel l1,l2,l3,l4;  
                    l1=new JLabel("Issue ID(IID)");  //Label 1 for Issue ID
                    l1.setBounds(30,15, 100,30); 
                    
                     
                    l4=new JLabel("Return Date(DD-MM-YYYY)");  
                    l4.setBounds(30,50, 150,30); 
                     
                    final JTextField F_iid = new JTextField();
                    F_iid.setBounds(110, 15, 200, 30);
                     
                     
                    final JTextField F_return=new JTextField();
                    F_return.setBounds(180, 50, 130, 30);
            
     
                    JButton create_but=new JButton("Return");//creating instance of JButton to mention return date and calculcate fine
                    create_but.setBounds(130,170,80,25);//x axis, y axis, width, height 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){                 
                         
                        String iid = F_iid.getText();
                        String return_date = F_return.getText();
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY1");
                         //Intialize date1 with NULL value
                         String date1=null;
                         String date2=return_date; //Intialize date2 with return date
                         
                         //select issue date
                         ResultSet rs = stmt.executeQuery("SELECT ISSUED_DATE FROM ISSUED WHERE IID="+iid);
                         while (rs.next()) {
                             date1 = rs.getString(1);
                              
                           }
                          
                         try {
                                Date date_1=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                                Date date_2=new SimpleDateFormat("dd-MM-yyyy").parse(date2);
                                //subtract the dates and store in diff
                                long diff = date_2.getTime() - date_1.getTime();
                                //Convert diff from milliseconds to days
                                example.ex.days=(int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                 
                                 
                            } catch (ParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                          
                         
                         //update return date
                         stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='"+return_date+"' WHERE IID="+iid);
                         g.dispose();
                          
     
                         Connection connection1 = connect();
                         Statement stmt1 = connection1.createStatement();
                         stmt1.executeUpdate("USE LIBRARY1");                
                        ResultSet rs1 = stmt1.executeQuery("SELECT PERIOD FROM ISSUED WHERE IID="+iid); //set period
                        String diff=null; 
                        while (rs1.next()) {
                             diff = rs1.getString(1);
                              
                           }
                        
                        int diff_int = Integer.parseInt(diff);
                      //  if(ex.days&amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;amp;gt;diff_int){ //If number of days are more than the period then calculcate fine
                            if(example.ex.days>10) { 
                            //System.out.println(ex.days);
                            int fine = (example.ex.days-diff_int)*10; //fine for every day after the period is Rs 10.
                            //update fine in the system
                            stmt1.executeUpdate("UPDATE ISSUED SET FINE="+fine+" WHERE IID="+iid);  
                            String fine_str = ("Fine: Rs. "+fine);
                            JOptionPane.showMessageDialog(null,fine_str);
                             
                        }
     
                         JOptionPane.showMessageDialog(null,"Book Returned!");
                          
                        }
                                 
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    }); 
                        g.add(l4);
                        g.add(create_but);
                        g.add(l1);
                        g.add(F_iid);
                        g.add(F_return);
                        g.setSize(350,250);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);              
        }
        });
     
        f.add(create_but);
        f.add(view_but);
        f.add(users_but);
        f.add(issued_but);
        f.add(add_user);
        f.add(add_book);
        f.add(issue_book);
        f.add(return_book);
        f.setSize(700,400);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible 
        f.setLocationRelativeTo(null);
       
       
       
        
}
public static void user_menu(final String UID) {
	 JFrame f=new JFrame("User Functions"); //Give dialog box name as User functions
     //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit user menu on closing the dialog box
     JButton view_but=new JButton("View Books");//creating instance of JButton  
     view_but.setBounds(20,20,120,25);//x axis, y axis, width, height 
     view_but.addActionListener(new ActionListener() { 
         public void actionPerformed(ActionEvent e){
              
             JFrame f = new JFrame("Books Available"); //View books stored in database
             //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
              
             Connection connection = connect();
             String sql="select * from BOOKS"; //Retreive data from database
             try {
                 Statement stmt = connection.createStatement(); //connect to database
                  stmt.executeUpdate("USE LIBRARY1"); // use librabry
                 stmt=connection.createStatement();
                 ResultSet rs=stmt.executeQuery(sql);
                 JTable book_list= new JTable(); //show data in table format
                 book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                   
                 JScrollPane scrollPane = new JScrollPane(book_list); //enable scroll bar
  
                 f.add(scrollPane); //add scroll bar
                 f.setSize(800, 400); //set dimensions of view books frame
                 f.setVisible(true);
                 f.setLocationRelativeTo(null);
             } catch (SQLException e1) {
                 // TODO Auto-generated catch block
                  JOptionPane.showMessageDialog(null, e1);
             }               
              
     }
     }
     );
      
     JButton my_book=new JButton("My Books");//creating instance of JButton  
     my_book.setBounds(150,20,120,25);//x axis, y axis, width, height 
     my_book.addActionListener(new ActionListener() { //Perform action
         public void actionPerformed(ActionEvent e){
              
                
             JFrame f = new JFrame("My Books"); //View books issued by user
             //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             int UID_int = Integer.parseInt(UID); //Pass user ID
  
             //.iid,issued.uid,issued.bid,issued.issued_date,issued.return_date,issued,
             Connection connection = connect(); //connect to database
             //retrieve data
             String sql="select distinct issued.*,books.bname,books.genre,books.price from issued,books " + "where ((issued.uid=" + UID_int + ") and (books.bid in (select bid from issued where issued.uid="+UID_int+"))) group by iid";
             String sql1 = "select bid from issued where uid="+UID_int;
             try {
                 Statement stmt = connection.createStatement();
                 //use database
                  stmt.executeUpdate("USE LIBRARY1");
                 stmt=connection.createStatement();
                 //store in array
                 ArrayList books_list = new ArrayList();
   
                 
                  
                 ResultSet rs=stmt.executeQuery(sql);
                 JTable book_list= new JTable(); //store data in table format
                 book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                 //enable scroll bar
                 JScrollPane scrollPane = new JScrollPane(book_list);
  
                 f.add(scrollPane); //add scroll bar
                 f.setSize(800, 400); //set dimensions of my books frame
                 f.setVisible(true);
                 f.setLocationRelativeTo(null);
             } catch (SQLException e1) {
                 // TODO Auto-generated catch block
                  JOptionPane.showMessageDialog(null, e1);
             }               
                  
     }
     }
     );
      
      
      
     f.add(my_book); //add my books
     f.add(view_but); // add view books
     f.setSize(400,300);//400 width and 500 height  
     f.setLayout(null);//using no layout managers  
     f.setVisible(true);//making the frame visible 
     f.setLocationRelativeTo(null);
        
}
public static Connection connect()
{
try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //System.out.println("Loaded driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library1","root","");
        //System.out.println("Connected to MySQL");
        return con;
 } 
 catch (Exception ex) {
        ex.printStackTrace();
 }
return null;
}
public static void create() {
        try {
        Connection connection=connect();
        ResultSet resultSet = connection.getMetaData().getCatalogs();
        //iterate each catalog in the ResultSet
            while (resultSet.next()) {
              // Get the database name, which is at position 1
              String databaseName = resultSet.getString(1);
              if(databaseName.equals("library1")) { 
                //System.out.print("yes");
                  Statement stmt = connection.createStatement();
                  //Drop database if it pre-exists to reset the complete database
                  String sql = "DROP DATABASE library1";
                  stmt.executeUpdate(sql);
              }
            }
              Statement stmt = connection.createStatement();
               
              String sql = "CREATE DATABASE LIBRARY1"; //Create Database
              stmt.executeUpdate(sql); 
             stmt.executeUpdate("USE LIBRARY1"); //Use Database
             //Create Users Table
              String sql1 = "CREATE TABLE USERS(UID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, USERNAME VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN)";
              stmt.executeUpdate(sql1);
              //Insert into users table
              stmt.executeUpdate("INSERT INTO USERS(USERNAME, PASSWORD, ADMIN) VALUES('admin','admin',TRUE)");
              //Create Books table
              stmt.executeUpdate("CREATE TABLE BOOKS(BID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BNAME VARCHAR(50), GENRE VARCHAR(20), PRICE INT)");
              //Create Issued Table
              stmt.executeUpdate("CREATE TABLE ISSUED(IID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BID INT, ISSUED_DATE VARCHAR(20), RETURN_DATE VARCHAR(20), PERIOD INT, FINE INT)");
              //Insert into books table
              stmt.executeUpdate("INSERT INTO BOOKS(BNAME, GENRE, PRICE) VALUES ('War and Peace', 'Mystery', 200),  ('The Guest Book', 'Fiction', 300), ('The Perfect Murder','Mystery', 150), ('Accidental Presidents', 'Biography', 250), ('The Wicked King','Fiction', 350)");
               
            resultSet.close();
        }
         catch (Exception ex) {
             ex.printStackTrace();
           }
        
        
	}
}




