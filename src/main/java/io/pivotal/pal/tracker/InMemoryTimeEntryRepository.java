package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> dataStore = new HashMap();
    private long idToAssign = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry persisted = new TimeEntry(idToAssign++, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        dataStore.put(persisted.getId(), persisted);
        return find(persisted.getId());
    }

    public TimeEntry find(long id) {
        return dataStore.get(id);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(dataStore.values());
//        List<TimeEntry> theList = new ArrayList();
//        for (TimeEntry entry: dataStore.values()) {
//            theList.add(entry);
//        }
//        return theList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry persisted = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        dataStore.put(persisted.getId(), persisted);
        return find(persisted.getId());
    }

    public void delete(long id) {
        dataStore.remove(id);
    }
}
