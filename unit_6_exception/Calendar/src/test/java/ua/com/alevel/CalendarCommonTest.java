package ua.com.alevel;

import org.junit.jupiter.api.Test;
import ua.com.alevel.entity.CalendarDate;
import ua.com.alevel.service.impl.CalendarImpl;

import java.util.ArrayList;
import java.util.List;

public class CalendarCommonTest {

    private CalendarDate cd1 = new CalendarDate();
    private CalendarDate cd2 = new CalendarDate();
    private CalendarDate cd3 = new CalendarDate();
    private final CalendarImpl calendar = new CalendarImpl();

    @Test
    public void test(){
        cd1 = calendar.convertToDate("01/12/21", "dd/mm/yy");
        cd2 = calendar.convertToDate("3/4/2021", "m/d/yyyy");
        cd3 = calendar.convertToDate("March 4 21", "mmm-d-yy");



        List<CalendarDate> datesList= new ArrayList<>();
        datesList.add(cd1);
        datesList.add(cd2);
        datesList.add(cd3);
        datesList = calendar.sortAsc(datesList);
        for (CalendarDate date: datesList) {
            date.print();
        }
        datesList = calendar.sortDesc(datesList);
        for (CalendarDate date: datesList) {
            date.print();
        }
    }
}
