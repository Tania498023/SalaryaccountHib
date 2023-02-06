package Persistence.PersonsJava;


import Persistence.PeopleXML.UserXML;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "persons")
public class Persons {
    @XmlElement(name = "users")
    private List<Role> usrs = new ArrayList<>();

    public void add(Role user) {
        usrs.add(user);
    }

    public List<Role> getUsrRec() {
        return usrs;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(usrs.toArray());
    }
}