package sns.util;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum DBManager {
	INSTANCE;
	
	private HikariDataSource ds;
	
	DBManager() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://localhost:3308/sns");
		config.setUsername("root");
		config.setPassword("1475");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		ds = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws Exception {
		return ds.getConnection();
	}
}
