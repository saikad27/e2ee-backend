# e2ee-backend
User following endpoints in your frontend 

#(please send messageDTO JSON object in request body)
To send a message : http://host_name:port_number/send 

#(please send userId as a request parameter)
To fetch new messages after user login : http://host_name:port_number/fetch/new/messages 

#(please send both userIds as a request parameter)
To fetch messages between 2 users : http://host_name:port_number/fetch/messages 

MessageDTO JSON object format:
{
    "senderId":"SENDER_ID",
    "receiverId":"RECEIVER_ID",
    "message":"MESSAGE"
}