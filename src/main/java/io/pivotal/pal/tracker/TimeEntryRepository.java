package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry entry);
    TimeEntry find(long id);
    TimeEntry update(long id, TimeEntry entry);
    void delete(long id);
    List<TimeEntry> list();
}
