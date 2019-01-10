package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListOfUsers {
    private Set<User> listofUsers = new HashSet<>();

    public ListOfUsers(){
        Set<User> listofUsers = new HashSet<>();
    }

    public void addToUserList(User user){
        listofUsers.add(user);
    }
}
