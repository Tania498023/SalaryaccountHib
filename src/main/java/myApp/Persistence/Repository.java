package myApp.Persistence;


import myApp.models.*;


import java.time.LocalDate;

public class Repository implements IRepository{

    public Repository() {

        addFakeDataUser();
        addFakeDataRecord();
    }

        public UserHib addFakeDataUser () {

            UserHib users = new UserHib("ЯЯ", UserRoleHib.MANAGER);
            return users;
        }
    public  RecordHib addFakeDataRecord () {

        RecordHib rec = new RecordHib(LocalDate.now().minusDays(3),8,"ok",addFakeDataUser());
        return rec;
    }
    public RecordHib addFakeDataRecord (UserHib us) {

        RecordHib rec = new RecordHib(LocalDate.now().minusDays(3),8,"ok",us);
        return rec;
    }
    }
