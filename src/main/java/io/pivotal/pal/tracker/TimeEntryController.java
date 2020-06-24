package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository repository){

        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry entry){

        TimeEntry record = repository.create(entry);
        return created(null).body(record);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){

        TimeEntry record = repository.find(id);
        return record != null ? ok().body(record) : notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry entry){

        TimeEntry record = repository.update(id, entry);
        return record != null ? ok().body(record) : notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id){

        repository.delete(id);
        return noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){

        List<TimeEntry> records = repository.list();
        return ok().body(records);
    }
}
