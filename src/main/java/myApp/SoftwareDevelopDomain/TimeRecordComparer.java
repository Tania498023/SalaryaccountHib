//package myApp.SoftwareDevelopDomain;
//public class TimeRecordComparer
//    {
//
//public boolean Equals(Object x, Object y)
//        {
//        if(x == null && y == null)
//        return true;
//        else if(x == null || y == null)
//        return false;
//        else if (((TimeRecord)x).getDate() == ((TimeRecord)y).getDate() && ((TimeRecord)x).getName() == ((TimeRecord)y).getName()
//                && ((TimeRecord)x).getMessage() == ((TimeRecord)y).getMessage() && ((TimeRecord)x).getHours() == ((TimeRecord)y).getHours())
//        return true;
//        else
//        return false;
//        }
//public int GetHashCode(Object obj)
//        {
//        int hCode;
//        //TODO - int это костыль
//        hCode = (int)((TimeRecord)obj).getMilliSecs() ^   ((TimeRecord)obj).getMessage().length() ^ ((TimeRecord)obj).getName().length();
//        return hCode ;
//        }
//}
