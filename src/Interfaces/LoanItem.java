package Interfaces;

import model.User;

public interface LoanItem {

    // EFFECTS: test if an item to be returned is overdue
    boolean overdue();

    // EFFECTS: return an items, if item is on time then it adds back, if overdue prints "overdue, you have lost x points"
    void returnItem(User u);
}
