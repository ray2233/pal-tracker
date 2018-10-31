package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;

    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

        TimeEntry body = this.timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity responseEntity = new ResponseEntity(body, HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry body = this.timeEntryRepository.find(timeEntryId);
        ResponseEntity responseEntity = new ResponseEntity(body,
                body == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {

        return ResponseEntity.status(HttpStatus.OK).body(timeEntryRepository.list());
    }

    @PutMapping("{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry body = this.timeEntryRepository.update(timeEntryId, expected);
        ResponseEntity responseEntity = new ResponseEntity(body, body == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        this.timeEntryRepository.delete(timeEntryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
