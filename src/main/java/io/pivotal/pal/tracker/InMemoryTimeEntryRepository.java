package io.pivotal.pal.tracker;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private long available_id;
    private Map<Long, TimeEntry> store;

    public InMemoryTimeEntryRepository(){
        this.available_id = 1L;
        this.store = new HashMap<>();
    }

    public TimeEntry create(TimeEntry entry){

        TimeEntry record = new TimeEntry(available_id, entry);

        store.put(available_id, record);
        available_id++;

        return record;
    }

    public TimeEntry find(long id){

        return store.getOrDefault(id, null);
    }

    public TimeEntry update(long id, TimeEntry entry){

        if(this.store.containsKey(id)){
            TimeEntry record = new TimeEntry(id, entry);
            store.put(id, record);

            return record;
        }

        return null;
    }

    public void delete(long id){

        store.remove(id);
    }

    public List<TimeEntry> list(){

        return new ArrayList<>(store.values());
    }
}
