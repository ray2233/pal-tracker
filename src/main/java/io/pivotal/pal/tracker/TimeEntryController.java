package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/time-entries")
@RestController
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;
    private final GaugeService gauge;
    private final CounterService counter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, GaugeService gauge, CounterService counter) {

        this.timeEntryRepository = timeEntryRepository;
        this.gauge = gauge;
        this.counter = counter;
    }

    @PostMapping("")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {


        ResponseEntity<TimeEntry> r;
        r = new ResponseEntity<>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return r;
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntryV = timeEntryRepository.find(timeEntryId);
        if(timeEntryV == null){
            counter.increment("TimeEntry.read");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        r = new ResponseEntity<>(timeEntryV, HttpStatus.OK);

        return r;
    }

    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntryV = timeEntryRepository.update(timeEntryId, expected);
        if(timeEntryV == null){
            counter.increment("TimeEntry.update");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        r = new ResponseEntity<>(timeEntryV, HttpStatus.OK);

        return r;
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
      //  ResponseEntity<TimeEntry> r;
        timeEntryRepository.delete(timeEntryId);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }
}
