package Persistence.RecordsClassXml;




import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "records")
public class Records {
    @XmlElement(name = "userRecord")
    private List<UserRecord> usrs = new ArrayList<>();

    public void add(UserRecord user) {
        usrs.add(user);
    }

    public List<UserRecord> getUsrRec() {
        return usrs;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(usrs.toArray());
    }
}
