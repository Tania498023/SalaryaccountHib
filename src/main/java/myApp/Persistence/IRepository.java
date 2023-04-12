package myApp.Persistence;


import myApp.models.RecordHib;
import myApp.models.UserHib;

public interface IRepository {
    UserHib addFakeDataUser ();
 RecordHib addFakeDataRecord ();
}
