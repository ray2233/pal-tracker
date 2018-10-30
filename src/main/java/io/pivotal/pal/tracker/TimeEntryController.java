package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/time-entries")
@RestController
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {


        ResponseEntity<TimeEntry> r;
        r = new ResponseEntity<>(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
        return r;
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntryV = timeEntryRepository.find(timeEntryId);
        if(timeEntryV == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        r = new ResponseEntity<>(timeEntryV, HttpStatus.OK);

        return r;
    }

    @GetMapping("")
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        ResponseEntity<TimeEntry> r;
        TimeEntry timeEntryV = timeEntryRepository.update(timeEntryId, expected);
        if(timeEntryV == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        r = new ResponseEntity<>(timeEntryV, HttpStatus.OK);

        return r;
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
      //  ResponseEntity<TimeEntry> r;
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }
}
