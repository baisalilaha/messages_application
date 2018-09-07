# messages_application
1.     An endpoint to read the message list for the current user (as identified by their HTTP Basic authentication credentials). Include messages they have sent and messages sent by users they follow. Support a “search=” parameter that can be used to further filter messages based on keyword.

http://localhost:5050/get_all_messages/{id}

http://localhost:5050/get_all_messages/{id}?search={string}

Login username : user 

      password : password



Example: http://localhost:5050/get_all_messages/8

         http://localhost:5050/get_all_messages/7?search=Loki

 

2.     Endpoints to get the list of people the user is following as well as the followers of the user.

http://localhost:5050/all_followers/{user_id}



Example: http://localhost:5050/all_followers/7



3.     An endpoint to start following another user.

http://localhost:5050/unfollow/{followers_user_id}?following={user_id_to_be_followed}



Example http://localhost:5050/start_following/7?user_id=10

To be Noted: If any user is already following another user, this API will return 500 Internal Server Error, on duplicate user/follower entry.

4.     An endpoint to unfollow another user. 



http://localhost:5050/unfollow/{Follower_id}?following={unfollowing_id}



Example: http://localhost:5050/unfollow/7?following=10





                 Additional Q:     An endpoint that returns a list of all users, paired with their most "popular" follower. The more followers someone has, the more "popular" they are. Hint: this is possible to do with a single SQL query!
         http://localhost:5050/popular_followers

Apart from the APis asked, I have created an additional API to create posts/replies:
http://localhost:5050/create_message
HEADER--   content-type:application/json
Input -- {"user_id" : "8", "message" : "I'm Loki", "replying_to":"18"}
