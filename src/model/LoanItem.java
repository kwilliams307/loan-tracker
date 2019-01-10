package model;

import Exceptions.NotEnoughPoints;
import Observer.Subject;

import java.util.*;

public abstract class LoanItem extends Observable {
    private String name;
    private String colour;
    private String size;
    private boolean urgent = false;
    private int returnByMonth;
    private int returnByDay;
    private boolean loaned = false;
    private List<User> emailList = new ArrayList<>();


    public LoanItem(String name, String colour, String size, boolean urgent,
                    int returnByMonth, int returnByDay, boolean loaned, List<User> observers) {
        this.name=name;
        this.colour=colour;
        this.size=size;
        this.urgent=urgent;
        this.returnByMonth=returnByMonth;
        this.returnByDay=returnByDay;
        this.loaned=false;
        for (User u : observers) {
            addObserver(u);
        }
    }


    public String getName() {
        return name;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isLoaned(){
        return loaned;
    }

    public void setLoaned(boolean loaned){
        this.loaned=loaned;
    }

    public void setReturnByMonth(int month){
        this.returnByMonth = month;
    }

    public void setReturnByDay(int day){
        this.returnByDay = day;
    }

    public int getReturnByMonth(){ return returnByMonth; }

    public int getReturnByDay(){ return returnByDay;}

    @Override
    public String toString() {
        return "LoanItem{" +
                "name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                ", size='" + size + '\'' +
                ", urgent=" + urgent +
                ", returnByMonth=" + returnByMonth +
                ", returnByDay=" + returnByDay +
                '}';
    }

    public boolean overdue (int month, int day){

        if (month <= returnByMonth) {
            if (day <= returnByDay) {
                return true;
            }
        }
        return false;
    }

    public abstract void returnItem (User u, int month, int day) throws NotEnoughPoints ;


    public void processReturn(User u, int points, int month, int day) throws NotEnoughPoints {
        if (overdue(month,day)) {
            u.setPoints((u.getPoints()) + points);
            System.out.println("Thank you for returning on time.");
        }
        else System.out.println("This is item is overdue. You've lost "+points+"  point.");

        setChanged();
        notifyObservers(emailList);
        for (User o : emailList) {
            System.out.println(o.getName());
        }
       // emailList.get(0).loanItem(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanItem loanItem = (LoanItem) o;
        return Objects.equals(name, loanItem.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
