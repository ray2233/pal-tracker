package io.pivotal.pal.tracker;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> timeEntries = new ArrayList<>();
    int ctn = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        TimeEntry newTimeEntry = new TimeEntry(ctn + 1, timeEntry.getProjectId(),
                timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

        timeEntries.add(newTimeEntry);

        ctn++;

        return find(newTimeEntry.getId());
    }

    @Override
    public TimeEntry find(Long id) {
        // TimeEntry timeEntry = new TimeEntry();
        for (TimeEntry timeEntry1 : timeEntries) {
            if (timeEntry1.getId() == id) {
                return timeEntry1;
            }
        }
        return null;
    }

    @Override
    public List<TimeEntry> list() {

        return timeEntries;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry1) {
        TimeEntry newTimeEntry = new TimeEntry(id, timeEntry1.getProjectId(),
                timeEntry1.getUserId(), timeEntry1.getDate(), timeEntry1.getHours());
        for (int i = 0; i < timeEntries.size(); i++) {

            if (timeEntries.get(i).getId() == id) {

                timeEntries.set(i, newTimeEntry);
                return timeEntries.get(i);
            }

        }

        //timeEntries.add(newTimeEntry);
        return null;
    }

    @Override
    public void delete(Long id) {

        timeEntries.remove(find(id));
    }

}
