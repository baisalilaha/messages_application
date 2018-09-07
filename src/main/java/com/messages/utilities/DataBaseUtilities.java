package com.messages.utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.messages.dao.Message;
import com.messages.dao.User;

@Component
@ComponentScan({"com.messages.dao"})
public class DataBaseUtilities{


	@Autowired
	@Qualifier("jdbc")
	private JdbcTemplate jdbc;

	public boolean createMessages(Map<String, Object> data, int replying_to){
		boolean execution_ok = true;
		try{
			String create_sql = "Insert into messages(user_id, messages, replied_to, time_created) VALUES(?,?,?,?)";

			execution_ok = jdbc.execute(create_sql,new PreparedStatementCallback<Boolean>(){
				@Override
				public Boolean doInPreparedStatement(java.sql.PreparedStatement ps)
						throws SQLException, DataAccessException {

					ps.setInt(1, Integer.parseInt(data.get("user_id").toString()));  
					ps.setString(2, data.get("message").toString());
					ps.setInt(3, replying_to);
					ps.setLong(4, (System.currentTimeMillis()/1000));  
					return ps.execute();  
				}  
			});
		}catch(Exception ex){
			return false;
		}
		execution_ok = true;
		return execution_ok;
	}

	public boolean createFollowers(int user_id, int follower){
		boolean execution_ok = true;
		try{
			String create_sql = "Insert into followers(user_id, follower_id) VALUES(?,?)";

			execution_ok = jdbc.execute(create_sql,new PreparedStatementCallback<Boolean>(){
				@Override
				public Boolean doInPreparedStatement(java.sql.PreparedStatement ps)
						throws SQLException, DataAccessException {
					ps.setInt(1, user_id);   
					ps.setInt(2, follower);  
					return ps.execute();  
				}  
			});
		}catch(Exception ex){
			return false;
		}
		execution_ok = true;
		return execution_ok;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<User>> retrieveAllFollowers(int user_id){
		Map<String, List<User>> followers = new HashMap<String, List<User>>();
		String sql = "select user_id as following, follower_id as followers, users.name as user_name, u.name as followers_name "
					+ "from followers "
					+ "join users on users.id = followers.user_id "
					+ "join users as u on u.id = followers.follower_id "
					+ "where follower_id = ? OR user_id = ?";
		
		List<List<User>> allFollowers = jdbc.query(sql,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, user_id);
				ps.setInt(2, user_id);
			}

		}, new ResultSetExtractor<List<List<User>>>() {

			@Override
			public List<List<User>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<User> following= new ArrayList<User>();
				List<User> followers= new ArrayList<User>();
				List<List<User>> all = new ArrayList<List<User>>();				
				while(rs.next()){
					User u = new User();
					if(rs.getInt("following") != user_id){
						u.setId(rs.getInt("following"));
						u.setName(rs.getString("user_name"));
						following.add(u);
					}
					if(rs.getInt("followers") != user_id){
						u.setId(rs.getInt("followers"));
						u.setName(rs.getString("user_name"));
						followers.add(u);
					}
					
				}
				all.add(0,followers);
				all.add(1,following);
				return all;
			}
		});
		followers.put("followers", allFollowers.get(0));
		followers.put("following", allFollowers.get(1));
		return followers;
	}
	
	public boolean deleteFollowers(int user_id, int follower){
		boolean execution_ok = true;
		try{
			String create_sql = "Delete from followers where user_id = ? and follower_id = ?";

			execution_ok = jdbc.execute(create_sql,new PreparedStatementCallback<Boolean>(){
				@Override
				public Boolean doInPreparedStatement(java.sql.PreparedStatement ps)
						throws SQLException, DataAccessException {
					ps.setInt(1, user_id);   
					ps.setInt(2, follower);  
					return ps.execute();  
				}  
			});
		}catch(Exception ex){
			return false;
		}
		execution_ok=true;
		return execution_ok;
	}
	
	public Map<Integer, List<Message>> retrieveAllMessages(int user_id, String search){
		String sql = "select m.id as id, m.user_id as user_id, m.messages as messages, m.time_created as time_created, m.replied_to as replied_to, "
					+ "u.name as name "
					+ "from messages as m "
					+ "join users as u "
					+ "on m.user_id = u.id "
					+ "where (m.user_id IN (select user_id from followers where follower_id =?) OR m.user_id = ? )";
					
		
		if(!search.isEmpty()){
			sql = sql + " and  m.messages like ?";   
		}
		
		sql = sql + " order by time_created";

		Map<Integer, List<Message>> allMessages = jdbc.query(sql,new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, user_id);
				ps.setInt(2, user_id);
				if(!search.isEmpty()){
					ps.setString(3, "%" + search + "%");
				}
			}

		}, new ResultSetExtractor<Map<Integer, List<Message>>>() {
			@Override
			public Map<Integer, List<Message>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, List<Message>> messages = new HashMap<Integer, List<Message>>();
				
				while(rs.next()){
					Message m = new Message();
					int post_id = rs.getInt("id");
					m.setMessage(rs.getString("messages"));
					m.setUserId(rs.getInt("user_id"));
					m.setTime(rs.getLong("time_created"));
					m.setRepliedTo(rs.getInt("replied_to"));
					m.setName(rs.getString("name"));
					m.setPostId(post_id);
					
					List<Message> posts = new ArrayList<Message>();
					if(rs.getInt("replied_to") > 0){
						int original_post_id = rs.getInt("replied_to");
						if(messages.get(original_post_id) != null){
							posts = messages.get(original_post_id);
							post_id = original_post_id;
						}
					}
					posts.add(m);
					messages.put(post_id, posts);
				}
				return messages;
			}
		});

		return allMessages;
	}
	
	public Map<String, Map<String, Object>> retrieveMostPopularFollowers(){
		String sql = "SELECT u1.name as user_name, f.user_id as user_id, u.user_id as follower_id,  u2.name as follower_name, count(f.follower_id) as number_of_followers "
					+ "FROM "
					+ "followers as u LEFT OUTER JOIN followers as f  "
					+ "ON f.follower_id = u.user_id "
					+ "JOIN users as u1 "
					+ "ON u1.id = f.user_id "
					+ "JOIN users as u2 "
					+ "ON u2.id = u.user_id "
					+ "WHERE f.user_id IN (SELECT id FROM users) "
					+ "GROUP BY user_id, follower_id";

		Map<String, Map<String, Object>> followers = jdbc.query(sql, new ResultSetExtractor<Map<String, Map<String, Object>>>() {
				@Override
				public Map<String, Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Map<String, Object>> followers = new HashMap<String, Map<String, Object>>();
					while(rs.next()){
						Map<String, Object> row = new HashMap<String, Object>();
						String user_name = rs.getString("user_name");
						int number = rs.getInt("number_of_followers");
						if(followers.containsKey(user_name)){
							row = followers.get(user_name);
							if(Integer.parseInt(row.get("number_of_followers").toString()) < number){
								row.put("user_id", rs.getInt("user_id"));
								row.put("follower_id", rs.getInt("follower_id"));
								row.put("popular_follower_name", rs.getString("follower_name"));
								row.put("number_of_followers", number);
								followers.put(user_name, row);
							}	
						}else{
							row.put("user_id", rs.getInt("user_id"));
							row.put("follower_id", rs.getInt("follower_id"));
							row.put("popular_follower_name", rs.getString("follower_name"));
							row.put("number_of_followers", number);
							followers.put(user_name, row);
						}
					}
					return followers;
				}
			});
		
		return followers;
	} 
}