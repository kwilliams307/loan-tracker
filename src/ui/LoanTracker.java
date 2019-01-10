package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import  java.net.URL;


import Exceptions.ItemDoesntExist;
import Exceptions.NotEnoughPoints;
import model.*;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class LoanTracker extends JFrame implements ActionListener{

    JTextField userName;
    JTextField userEmail;
    User currentUser;
    JTextField itemName;
    LoanItem loanItem;
    JTextField itemColour;
    JTextField itemSize;
    JTextField itemReturnMonth;
    JTextField itemReturnDay;
    JTextField returnName;
    JTextField returnMonth;
    JTextField returnDay;

    JPanel loginPanel, mainPanel, loadPanel, loanPanel, returnPanel, urgentPanel, itemPanel;

    public static void main(String[] args) {
        new LoanTracker();
    }

    public LoanTracker() {
        super("Loan Tracker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
        setup();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    public static void main2(String[] args) throws MalformedURLException, IOException, NotEnoughPoints {
       Scanner scanner = new Scanner(System.in);
       String option = "";
       String user = "";
       String name= "";
       String email = "";
       String colour = "";
       String size = "";
       int returnMonth;
       int returnDay;
       String userName = "";

       BufferedReader br = null;

       try {
           String theURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html";
           URL url = new URL(theURL);
           br = new BufferedReader(new InputStreamReader(url.openStream()));

           String line;

           StringBuilder sb = new StringBuilder();

           while ((line = br.readLine()) != null) {

               sb.append(line);
               sb.append(System.lineSeparator());
           }

           System.out.println(sb);
       } finally {
           if (br != null) {
               br.close();
           }
       }

//       System.out.print("Create new user. Please type name.");
//       name = scanner.nextLine();
//       System.out.print("Please type email.");
//       email = scanner.nextLine();
        User newUser = null;
        //listOfUsers.add(newUser);


       while (true){
           System.out.println("Please select loan, return, save or load");
           option = scanner.nextLine();
           System.out.println("You selected "+ option);

           if (option.equals("loan")){
//
//               BufferedReader br1 = null;
//
//               try {
//                   String theURL = "https://www.zara.com/uk/en/category/811528/product/3222004/size-guide";
//                   URL url = new URL(theURL);
//                   br1 = new BufferedReader(new InputStreamReader(url.openStream()));
//
//                   String line;
//
//                   StringBuilder sb = new StringBuilder();
//
//                   while ((line = br1.readLine()) != null) {
//
//                       sb.append(line);
//                       sb.append(System.lineSeparator());
//                   }
//
//                   Document doc = (Document) Jsoup.parse(sb.toString());
//               } finally {
//
//                   if (br1 !=null) {
//                       br1.close();
//                   }
//               }
               System.out.println("Please select urgent loan or non-urgent loan");
               option = scanner.nextLine();
                if (option.equals("urgent loan")){
                        System.out.print("Please type item name");
                        option = scanner.nextLine();
                        LoanItem newItem;
                        try {
                            newItem = ItemSingleton.getInstance().getItem(option);
                        } catch (ItemDoesntExist itemDoesntExist){
                            System.out.print("This item does not exist. Please create new item.");
                            System.out.print("Please type item name");
                            name = scanner.nextLine();
                            System.out.print("Please type colour");
                            colour = scanner.nextLine();
                            System.out.print("Please type size");
                            size = scanner.nextLine();
                            System.out.print("Please type return month");
                            returnMonth = scanner.nextInt();
                            System.out.print("Please type return day");
                            returnDay = scanner.nextInt();
                            newItem = new UrgentReturn(name, colour, size, true, returnMonth, returnDay, false, new ArrayList<>());
                            ItemSingleton.getInstance().addToList(newItem);
                            continue;
                        }




                    try {
                        newUser.loanItem(newItem);
                    } catch (NotEnoughPoints notEnoughPoints) {
                        System.out.print("You don't have enough points to loan item.");
                        continue;
                    }
                    newUser.setPoints((newUser.getPoints())-2);
                   }
                   else if (option.equals("non-urgent loan")){
                        System.out.print("Please type item name");
                        name = scanner.nextLine();



                        System.out.print("Please type colour");
                        colour = scanner.nextLine();
                        System.out.print("Please type size");
                        size = scanner.nextLine();
                        System.out.print("Please type return month");
                        returnMonth = scanner.nextInt();
                        System.out.print("Please type return day");
                        returnDay = scanner.nextInt();
                        LoanItem newItem = new NonUrgentReturn(name, colour, size,false, returnMonth, returnDay, false, new ArrayList<>());
                       try {
                           newUser.loanItem(newItem);
                       } catch (NotEnoughPoints notEnoughPoints) {
                           System.out.print("You don't have enough points to loan item.");
                           continue;
                       }
                       newUser.setPoints((newUser.getPoints()-1));
                   }
               }

               else if (option.equals("return")){
               System.out.println("Please enter item name to return");
               option = scanner.nextLine();
               for(LoanItem item : newUser.loaned) {
                   if(item.getName().equals(option)) {
                       if (item instanceof UrgentReturn) {
                           ((UrgentReturn) item).returnItem(newUser,0 ,0);
                           break;
                       } else if(item instanceof NonUrgentReturn) {
                           ((NonUrgentReturn) item).returnItem(newUser, 0, 0);
                           break;
                       }

                   }
               }
               LoanedOut.printReciept(newUser.getName());
           }

           else if (option.equals("save")){
               List<String> lines = new ArrayList<>();
               lines.add(newUser.getName()+", "+newUser.getEmail()+", "+newUser.getPoints());

               try {
                   Files.write(Paths.get("username.txt"), lines);
               } catch (IOException e) {
                   System.out.println("Exception is thrown");
               }
           }

           else if (option.equals("load")){
               List<String> lines;

               try {
                  lines = Files.readAllLines(Paths.get("username.txt"));
               } catch (IOException e) {
                   System.out.println("User doesn't exist. Create new user.");
                   continue;
               }
               for(String line : lines) {
                   String[] parts = line.split(", ");
                   User u = new User(parts[0], parts[1], Integer.parseInt(parts[2]), new ArrayList<>());
                   System.out.println("User "+u.getName()+": "+u.getEmail()+" loaded with "+u.getPoints()+" points.");
               }
           }




       }
    }

    public void mainSetup() {
        if(mainPanel != null) {
            mainPanel.setVisible(true);
            return;
        }

        mainPanel = new JPanel();
        JLabel label = new JLabel("Please select loan, return, save, load");

        JButton loanBtn = new JButton("Loan");
        loanBtn.setActionCommand("loan");
        loanBtn.addActionListener(this);

        JButton returnBtn = new JButton("Return");
        returnBtn.setActionCommand("return");
        returnBtn.addActionListener(this);

        JButton saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);

        JButton loadBtn = new JButton("Load");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);

        mainPanel.add(label);
        mainPanel.add(loanBtn);
        mainPanel.add(returnBtn);
        mainPanel.add(saveBtn);
        mainPanel.add(loadBtn);
        mainPanel.setLayout(new GridLayout(5,1));
        mainPanel.setVisible(true);
        this.add(mainPanel);
        this.revalidate();


    }

    public void loanSetup(){
        if(loanPanel != null) {
            loanPanel.setVisible(true);
            return;
        }

        loanPanel = new JPanel();
        JLabel label = new JLabel("Please select urgent loan or non-urgent loan");

        JButton urgentBtn = new JButton("Urgent Loan");
        urgentBtn.setActionCommand("urgent");
        urgentBtn.addActionListener(this);

        JButton nonUrgentBtn = new JButton("Non-Urgent Loan");
        nonUrgentBtn.setActionCommand("not urgent");
        nonUrgentBtn.addActionListener(this);

        loanPanel.add(label);
        loanPanel.add(urgentBtn);
        loanPanel.add(nonUrgentBtn);
        loanPanel.setLayout(new GridLayout(3,1));
        loanPanel.setVisible(true);
        this.add(loanPanel);
        this.revalidate();
        this.repaint();

    }

    public void urgentSetup(){
        if(urgentPanel != null) {
            urgentPanel.setVisible(true);
            return;
        }

        urgentPanel = new JPanel();
        JLabel label = new JLabel("Please type item name");
        itemName= new JTextField(3);

        JButton urgentBtn = new JButton("OK");
        urgentBtn.setActionCommand("ok2");
        urgentBtn.addActionListener(this);

        urgentPanel.add(label);
        urgentPanel.add(itemName);
        urgentPanel.add(urgentBtn);
        urgentPanel.setLayout(new GridLayout(3,1));
        urgentPanel.setVisible(true);
        this.add(urgentPanel);

    }

    public void itemSetup(){
        if(itemPanel != null) {
            itemPanel.setVisible(true);
            return;
        }

        itemPanel = new JPanel();
        JLabel label = new JLabel("Create new item:");
        JLabel label1 = new JLabel("     ");
        JLabel name = new JLabel("Name:");
        itemName = new JTextField(3);
        JLabel colour = new JLabel("Colour:");
        itemColour = new JTextField(3);
        JLabel size = new JLabel("Size:");
        itemSize = new JTextField(3);
        JLabel returnMonth = new JLabel("Return Month:");
        itemReturnMonth = new JTextField(3);
        JLabel returnDay = new JLabel("Return Day:");
        itemReturnDay = new JTextField(3);

        JButton btn = new JButton("OK");
        btn.setActionCommand("ok3");
        btn.addActionListener(this);

        itemPanel.add(label);
        itemPanel.add(label1);
        itemPanel.add(itemName);
        itemPanel.add(name);
        itemPanel.add(itemColour);
        itemPanel.add(colour);
        itemPanel.add(itemSize);
        itemPanel.add(size);
        itemPanel.add(itemReturnMonth);
        itemPanel.add(returnMonth);
        itemPanel.add(itemReturnDay);
        itemPanel.add(returnDay);
        itemPanel.add(btn);
        itemPanel.setLayout(new GridLayout(7,1));
        itemPanel.setVisible(true);
        this.add(itemPanel);
        this.revalidate();
        //this.repaint();


    }

    public void returnSetup(){
        if(returnPanel != null){
            returnPanel.setVisible(true);
            return;
        }

        returnPanel = new JPanel();
        JLabel label = new JLabel("Type name of item:");
        returnName = new JTextField(3);
        JLabel label1 = new JLabel("Type month returned");
        returnMonth = new JTextField(3);
        JLabel label2 = new JLabel("Type day returned");
        returnDay = new JTextField(3);

        JButton btn = new JButton("OK");
        btn.setActionCommand("returnOK");
        btn.addActionListener(this);

        returnPanel.add(label);
        returnPanel.add(returnName);
        returnPanel.add(label1);
        returnPanel.add(returnMonth);
        returnPanel.add(label2);
        returnPanel.add(returnDay);
        returnPanel.add(btn);
        returnPanel.setLayout(new GridLayout(5,1));
        returnPanel.setVisible(true);
        this.add(returnPanel);
        this.revalidate();

    }


    public void setup(){
        if(loginPanel != null) {
            loginPanel.setVisible(true);
            return;
        }
        JPanel newPanel = new JPanel();
        JLabel label = new JLabel("Create new user:");
        JLabel name = new JLabel("Name:");
        userName = new JTextField(3);
        JLabel email = new JLabel("Email:");
        userEmail = new JTextField("example@example.com");


        JButton btn = new JButton("OK");
        btn.setActionCommand("ok");
        btn.addActionListener(this);

        newPanel.add(name);
        newPanel.add(userName);
        newPanel.add(email);
        newPanel.add(userEmail);

        loginPanel = new JPanel();

        loginPanel.add(label);
        loginPanel.add(newPanel);
        loginPanel.add(btn);
        loginPanel.setLayout(new GridLayout(3,1));
        loginPanel.setVisible(true);
        this.add(loginPanel);
    }

    public void playErrorSound(){
        try {
            String soundName = "error.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch ( Exception e) {
            System.out.println("Error");
        }
    }


    @Override
    public void actionPerformed(ActionEvent a) {

        System.out.println(a.getActionCommand());
        if(a.getActionCommand().equals("ok")) {
            System.out.println(userName.getText());
            System.out.println(userEmail.getText());
            currentUser = new User(userName.getText(), userEmail.getText(),10, new ArrayList<>());
            loginPanel.setVisible(false);
            mainSetup();
        }

        if (a.getActionCommand().equals("loan")){
            System.out.println("Loan");
            mainPanel.setVisible(false);
            loanSetup();

        }
        if (a.getActionCommand().equals("urgent")){
            loanPanel.setVisible(false);
            urgentSetup();
        }

        if (a.getActionCommand().equals("ok2")){
            urgentPanel.setVisible(false);
            try {
                loanItem = ItemSingleton.getInstance().getItem(itemName.getText());

                currentUser.loanItem(loanItem);
                currentUser.setPoints((currentUser.getPoints())-2);
                JOptionPane.showMessageDialog(this,"You have successfully been loaned this item! Please return by " + loanItem.getReturnByDay()+ " / " + loanItem.getReturnByMonth()
                        + ". You now currently have " + currentUser.getPoints() + " points.", "Loaned", JOptionPane.PLAIN_MESSAGE);
                mainPanel.setVisible(true);
                } catch (NotEnoughPoints notEnoughPoints) {
                playErrorSound();
                JOptionPane.showMessageDialog(this, "Sorry, you don't have enough points to loan this item.");
            } catch (ItemDoesntExist itemDoesntExist) {
                playErrorSound();
                JOptionPane.showMessageDialog(this, "This item does not exist. Please create a new one.","ITEM DOESN'T EXIST!", ERROR_MESSAGE, null);
                itemSetup();
            }

        }

        if(a.getActionCommand().equals("ok3")){
            loanItem = new UrgentReturn(itemName.getText(), itemColour.getText(), itemSize.getText(), true, Integer.parseInt(itemReturnMonth.getText()), Integer.parseInt(itemReturnDay.getText()), false, new ArrayList<>());
            try {
                currentUser.loanItem(loanItem);
                currentUser.setPoints((currentUser.getPoints())-2);
                JOptionPane.showMessageDialog(this,"You have successfully been loaned this item! Please return by " + loanItem.getReturnByDay()+ " / " + loanItem.getReturnByMonth()
                        + ". You now currently have " + currentUser.getPoints() + " points.", "Loaned", JOptionPane.PLAIN_MESSAGE);
                mainPanel.setVisible(true);
            } catch (NotEnoughPoints notEnoughPoints) {
                playErrorSound();
                JOptionPane.showMessageDialog(this, "Sorry, you don't have enough points to loan this item.");
            }
        }
        if (a.getActionCommand().equals("return")){
            mainPanel.setVisible(false);
            returnSetup();
        }

        if (a.getActionCommand().equals("returnOK")){
            for(LoanItem item : currentUser.loaned) {
                if(item.getName().equals(returnName.getText())){
                    if (item instanceof UrgentReturn) {
                        try {
                            ((UrgentReturn) item).returnItem(currentUser, Integer.parseInt(returnMonth.getText()), Integer.parseInt(returnDay.getText()));
                        } catch (NotEnoughPoints notEnoughPoints) {
                            playErrorSound();
                            JOptionPane.showMessageDialog(this, "Sorry, you don't have enough points to loan this item.");
                        }
                        break;
                    } else if(item instanceof NonUrgentReturn) {
                        try {
                            ((NonUrgentReturn) item).returnItem(currentUser, Integer.parseInt(returnMonth.getText()), Integer.parseInt(returnDay.getText()));
                        } catch (NotEnoughPoints notEnoughPoints) {
                            playErrorSound();
                            JOptionPane.showMessageDialog(this, "Sorry, you don't have enough points to loan this item.");
                        }
                        break;
                    }

                }
            }
            returnPanel.setVisible(false);
            JOptionPane.showMessageDialog(this, "Item returned!");
            mainPanel.setVisible(true);
        }

        if(a.getActionCommand().equals("save")) {
          List<String> lines = new ArrayList<>();
          lines.add(userName.getText()+", "+userEmail.getText()+", "+currentUser.getPoints());

          try {
              Files.write(Paths.get("username.txt"), lines);
          } catch (IOException e) {
              System.out.println("Exception is thrown");
          }
        }
    }
}
