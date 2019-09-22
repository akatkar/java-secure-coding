package secure.coding.chapter09.fio.fio04;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @rule FIO04-J. Close resources when they are no longer needed
 * 
 * 
 * @description The Java garbage collector is called to free unreferenced but
 *              as-yet unreleased memory. However, the garbage collector cannot
 *              free nonmemory resources such as open file descriptors and
 *              database connections.
 * 
 *              Consequently, failing to release such resources can lead to
 *              resource exhaustion attacks. In addition, programs can
 *              experience resource starvation while waiting for finalize() to
 *              release resources such as Lock or Semaphore objects. This can
 *              occur because Java lacks any temporal guarantee of when
 *              finalize() methods execute, other than “sometime before program
 *              termination.” Finally, output streams may cache object
 *              references; such cached objects are not garbage-collected until
 *              after the output stream is closed. Consequently, output streams
 *              should be closed promptly after use.
 * 
 */

public class FileClosing {
	/**
	 * @category Noncompliant code
	 *
	 * @description The problem of resource pool exhaustion is exacerbated in the
	 *              case of database connections. Many database servers allow only a
	 *              fixed number of connections, depending on configuration and
	 *              licensing. Consequently, failure to release database connections
	 *              can result in rapid exhaustion of available connections. This
	 *              noncompliant code example fails to close the connection when an
	 *              error occurs during execution of the SQL statement or during
	 *              processing of the results.
	 * 
	 * @param sqlQuery
	 */
	public void getResults1(String sqlQuery) {
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);
			processResults(rs);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			/* forward to handler */
		}
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example attempts to address exhaustion of
	 *              database connections by adding cleanup code in a finally block.
	 *              However, rs, stmt, or conn could be null, causing the code in
	 *              the finally block to throw a NullPointerException.
	 * 
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public void getResults2(String sqlQuery) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			processResults(rs);
		} catch (SQLException e) {
			// forward to handler
		} finally {
			rs.close();
			stmt.close();
			conn.close();
		}
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description In this noncompliant code example, the call to rs.close() or the
	 *              call to stmt.close() might throw a SQLException. Consequently,
	 *              conn.close() is never called. This is a violation of rule
	 *              ERR05-J.
	 * 
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public void getResults3(String sqlQuery) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			processResults(rs);
		} catch (SQLException e) {
			// forward to handler
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
				if (conn != null) {
					conn.close();
				}
			}
		}
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution ensures that resources are released as
	 *              required.
	 * 
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public void getResults4(String sqlQuery) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			processResults(rs);
		} catch (SQLException e) {
			// forward to handler
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// forward to handler
			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
					// forward to handler
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						// forward to handler
					}
				}
			}
		}
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution uses the try-with-resources construct,
	 *              introduced in Java SE 7, to ensure that resources are released
	 *              as required.
	 * 
	 * @param sqlQuery
	 * @throws SQLException
	 */
	public void getResults5(String sqlQuery) throws SQLException {
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlQuery)) {
			processResults(rs);
		} catch (SQLException e) {
			// forward to handler
		}
	}

	private void processResults(ResultSet rs) {
	}

	private Connection getConnection() {
		return null;
	}
}