package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long,TimeEntry> mapTimeEntry = new HashMap<>();
    private long timeEntryId = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createTimeEntry = new TimeEntry(timeEntryId++,timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        mapTimeEntry.put(createTimeEntry.getId(),createTimeEntry);
        return createTimeEntry;
    }

    @Override
    public TimeEntry find(long id)
    {
        return mapTimeEntry.get(id);
    }

    @Override
    public List<TimeEntry> list() {

        List<TimeEntry> list = new ArrayList();

        list.addAll(mapTimeEntry.values());

        return list;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = new TimeEntry(id,timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        mapTimeEntry.put(updatedTimeEntry.getId(),updatedTimeEntry);
        return updatedTimeEntry;
    }

    @Override
    public void delete(long id) {
        mapTimeEntry.remove(id);
    }
}
