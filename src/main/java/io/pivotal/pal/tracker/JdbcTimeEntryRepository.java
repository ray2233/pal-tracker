package io.pivotal.pal.tracker;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;


    public JdbcTimeEntryRepository(DataSource dataSource){

        this.jdbcTemplate =  new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sqlInsert = "INSERT INTO time_entries (project_id,user_id, date, hours)"
                + " VALUES (?, ?, ?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sqlInsert
                    ,
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(Long id) {
        //TimeEntry timeEntry = new TimeEntry();
        String sql = "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?";
        TimeEntry timeEntry = jdbcTemplate.query(sql, new Object[]{id},extractor);
        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        String sqlList = "Select * from time_entries";

       List<TimeEntry> timeEntries = jdbcTemplate.query(sqlList, mapper);
        return timeEntries;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        String sqlUpdate = "UPDATE time_entries set project_id=?,user_id=?,date=?,hours=? where id=?";

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    sqlUpdate);


            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());
            statement.setLong(5,id);
            return statement;
        });

        return find(id);
    }

    @Override
    public void delete(Long id) {
        String sqlDelete = "DELETE FROM time_entries where id = ?";
        jdbcTemplate.update(sqlDelete ,id);

    }
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours"));

    private final ResultSetExtractor<TimeEntry> extractor = (rs) -> (rs.next())? mapper.mapRow(rs,1):null;
}
