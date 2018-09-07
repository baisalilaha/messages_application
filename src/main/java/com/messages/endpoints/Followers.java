package com.messages.endpoints;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.messages.dao.User;
import com.messages.utilities.DataBaseUtilities;

@RestController
@ComponentScan({"com.messages.dao", "com.messages.utilities"})
public class Followers{
	
	@Autowired
	DataBaseUtilities utility;
	
	
	/**
	 * 3.	An endpoint to start following another user
	 * @param followerId
	 * @param userId
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/start_following/{id}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity startFollowing(@PathVariable("id") String followerId, @RequestParam("user_id") String userId, HttpServletResponse response){
		//validate inputs, Also a cache of current users will help in validation process as well.
		try{
			int user_id = Integer.parseInt(userId);
			int follower_id = Integer.parseInt(followerId);
			
			if(utility.createFollowers(user_id, follower_id)){
				return new ResponseEntity(HttpStatus.OK);
			}else{
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(NumberFormatException n){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	
	/**
	 * 4.	An endpoint to unfollow another user.
	 * @param followerId
	 * @param userId
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/unfollow/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity unFollowUser(@PathVariable("id") String follower, @RequestParam("following") String user){
		//validate inputs, Also a cache of current users will help in validation process as well.
		try{
			int user_id = Integer.parseInt(user);
			int follower_id = Integer.parseInt(follower);
			
			if(utility.deleteFollowers(user_id, follower_id)){
				return new ResponseEntity(HttpStatus.OK);
			}else{
				return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(NumberFormatException n){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * 2.	Endpoints to get the list of people the user is following as well as the followers of the user.
	 * @param id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value = "/all_followers/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllFollowers(@PathVariable("id") String id){
		try{
			int userId = Integer.parseInt(id);
			Map<String, List<User>> list_of_followers = utility.retrieveAllFollowers(userId);
//			JSONObject follower_json = new JSONObject(list_of_followers);
			return new ResponseEntity(list_of_followers, HttpStatus.OK);
		}catch(NumberFormatException n){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value = "/popular_followers", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getAllPopularFollower(){
		try{
			Map<String, Map<String, Object>> popular_followers = utility.retrieveMostPopularFollowers();
			JSONObject follower_json = new JSONObject(popular_followers);
			return new ResponseEntity(follower_json, HttpStatus.OK);
		}catch(NumberFormatException n){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
}