package Persistence.PeopleXML;

import Persistence.PeopleXML.UserXML;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "users")
public class UsersXML {
    @XmlElement(name = "user")
    private List<UserXML> usr = new ArrayList<>();

    public void add(UserXML user) {
        usr.add(user);
    }

    public List<UserXML> getUsr() {
        return usr;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(usr.toArray());
    }
}
