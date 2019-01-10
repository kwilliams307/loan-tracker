package model;

import Exceptions.NotEnoughPoints;
import Interfaces.Customer;
import Interfaces.LoanTracker;

import java.util.*;

import Observer.ItemObserver;
import model.LoanedOut;


public class User implements Customer, LoanTracker, Observer {
    private String name;
    private String email;
    private int points;
    public List<LoanItem> loaned;



    public User(String name, String email, int points, List<LoanItem> loaned) {
        this.name = name;
        this.email = email;
        this.points = points;
        this.loaned = loaned;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }



    // EFFECTS: given a valid user name, print's out points and items loaned
    public void printReceipt() {
        System.out.println(name);
        System.out.println(points);
    }



    public void loanItem(LoanItem item) throws NotEnoughPoints {
        if (!alreadyLoaned(item)) {
            if (item.isUrgent()) {
                if (points < 2) {
                    throw new NotEnoughPoints();
                }
            } else if (points < 1) {
                throw new NotEnoughPoints();
            }
            addItem(item);
        }
    }


    public void addItem (LoanItem item){
            if (!item.isLoaned()){
                loaned.add(item);
//                LoanedOut.addKey(getName(), this, item);
                item.setLoaned(true);
            }

            else {
                item.addObserver(this);
            }
    }

    public void removeItem(LoanItem item){
        if(alreadyLoaned(item)){
            loaned.remove(item);
            LoanedOut.removeValue(getName(), this, item);
        }
    }


    public boolean alreadyLoaned (LoanItem item){
        if (loaned.contains(item)) return true;
        return false;
    }


    @Override
    public void update (Observable o, Object arg) {
        LoanItem l = (LoanItem) o;
        Set<User> u = (Set<User>) arg;

        if (l.isUrgent()){
            if (getPoints() >= 2) {
                u.add(this);
            }
        }

        else {
            if (getPoints() >= 1) {
                u.add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
