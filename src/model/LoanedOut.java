package model;

import Interfaces.LoanTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanedOut {
    private static Map<String, List<LoanItem>> loanedOut = new HashMap<>();

    public static void addKey(String name, User u, LoanItem i) {

        List<LoanItem> items = loanedOut.get(name);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(i);
        loanedOut.put(name, items);
        u.addItem(i);

    }

    public static void printReciept(String name) {
        System.out.println(name);
    }

    public static void removeValue(String name, User user, LoanItem i) {
        List<LoanItem> items = loanedOut.get(name);
        if (items == null) {
            return;
        }
        items.remove(i);
        user.removeItem(i);
    }
}
