package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;

import hexlet.code.model.UrlCheck;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck check) throws SQLException {
        var sql = """
            INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            check.setCreatedAt(LocalDateTime.now());

            stmt.setInt(1, check.getStatusCode());
            stmt.setString(2, check.getTitle());
            stmt.setString(3, check.getH1());
            stmt.setString(4, check.getDescription());
            stmt.setLong(5, check.getUrlId());
            stmt.setTimestamp(6, Timestamp.valueOf(check.getCreatedAt()));

            stmt.executeUpdate();

            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB did not return an id after saving UrlCheck");
            }
        }
    }

    public static List<UrlCheck> getAllChecks(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();

            var result = new ArrayList<UrlCheck>();

            while (resultSet.next()) {
                result.add(generateUrlCheck(resultSet));
            }

            return result;
        }
    }

    public static Optional<UrlCheck> findLastCheck(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return Optional.of(generateUrlCheck(resultSet));
            }

            return Optional.empty();
        }
    }

    public static void delete() throws SQLException {
        var sql = "DELETE FROM url_checks";
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static UrlCheck generateUrlCheck(ResultSet rs) throws SQLException {
        var id = rs.getLong("id");
        var statusCode = rs.getInt("status_code");
        var title = rs.getString("title");
        var h1 = rs.getString("h1");
        var description = rs.getString("description");
        var urlId = rs.getLong("url_id");
        var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        var check = new UrlCheck(statusCode, title, h1, description);
        check.setId(id);
        check.setUrlId(urlId);
        check.setCreatedAt(createdAt);
        return check;
    }
}
