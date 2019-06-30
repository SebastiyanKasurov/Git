package com.kpk.git.dao;

import com.kpk.git.model.Commit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CommitRowMapper implements RowMapper<Commit> {
	
	@Override
	public Commit mapRow(ResultSet rs, int i) throws SQLException {
		Commit commit = new Commit();
		
		commit.setTimeCreated(rs.getTimestamp("creation_date").toLocalDateTime());
		commit.setMessage(rs.getString("message"));
		
		return commit;
	}
}
