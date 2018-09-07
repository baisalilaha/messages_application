package com.messages.endpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.messages.dao.Message;
import com.messages.utilities.DataBaseUtilities;

@RestController
@ComponentScan({"com.messages.dao", "com.messages.utilities"})
public class Messages{
	
	@Autowired
	DataBaseUtilities utility;
	
	/**
	 * An endpoint to read the message list for the current user (as identified by their HTTP Basic
	 * authentication credentials). Include messages they have sent and messages sent by users they follow.
	 * @param userId
	 * @param response
	 * @return
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/get_all_messages/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity GetAllMessages(@PathVariable("id") String user, @RequestParam(value = "search", required = false) String search_string){
		try{
			int user_id = Integer.parseInt(user);
			String search = "";
			if(search_string != null && !search_string.isEmpty()){
				search = search_string;
			}
			Map<Integer, List<Message>> allMessages = utility.retrieveAllMessages(user_id, search);
			return new ResponseEntity(allMessages, HttpStatus.OK);
		}catch(NumberFormatException n){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * An endpoint to save messages as created by the users 
	 * @param userId
	 * @param message
	 * @param response
	 * @return 
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/create_message", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity CreateMessages(@RequestBody String messages){
		JSONParser parser = new JSONParser();
		Map<String, Object> request = new HashMap<String, Object>();
		try {
			request = (JSONObject) parser.parse(messages);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(request.get("user_id").toString().isEmpty()){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		if(request.get("message").toString().length() > 254){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		int replying_to =0;
		if(request.get("replying_to") != null){
			try{
				replying_to = Integer.parseInt(request.get("replying_to").toString());
			}catch(NumberFormatException n){
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}			
		/**TODO Can add validation for the user here, taken user id from cache**/
		
		//save it to database table : messages
		boolean updated_record =  utility.createMessages(request, replying_to);
		if(updated_record){
			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}