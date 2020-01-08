package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository repository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository repository,
                               MeterRegistry meterRegistry
    ){

        this.repository = repository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry entry){

        TimeEntry record = repository.create(entry);

        actionCounter.increment();
        timeEntrySummary.record(repository.list().size());

        return created(null).body(record);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id){

        TimeEntry record = repository.find(id);

        if (record != null) {

            actionCounter.increment();
            return ok().body(record);
        } else {

            return notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry entry){

        TimeEntry record = repository.update(id, entry);

        if (record != null) {

            actionCounter.increment();
            return ok().body(record);
        } else {

            return notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id){

        repository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(repository.list().size());

        return noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){

        actionCounter.increment();
        return ok().body(repository.list());
    }
}
