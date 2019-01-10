package model;


import Exceptions.NotEnoughPoints;

import java.util.List;

public class NonUrgentReturn extends LoanItem {


    public NonUrgentReturn(String name, String colour, String size, boolean urgent, int returnByMonth, int returnByDay, boolean loaned, List<User> observers) {
        super(name, colour, size, urgent, returnByMonth, returnByDay, loaned, observers);
        setUrgent(false);
    }

    public void returnItem (User u, int month, int day) throws NotEnoughPoints {
        processReturn(u,1 , month, day);
        u.printReceipt();
        u.removeItem(this);
    }

}
