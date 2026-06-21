package com.example.e2ee_backend.service;

import com.example.e2ee_backend.dto.ConnectionDTO;
import com.example.e2ee_backend.dto.UserDTO;
import com.example.e2ee_backend.exception.AadhaarNotVerifiedException;
import com.example.e2ee_backend.exception.UserNotFoundException;
import com.example.e2ee_backend.model.Connection;
import com.example.e2ee_backend.model.ConnectionRequest;
import com.example.e2ee_backend.model.UserInfo;
import com.example.e2ee_backend.repo.ConnectionRepository;
import com.example.e2ee_backend.repo.ConnectionRequestRepository;
import com.example.e2ee_backend.repo.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;
    private final ConnectionRequestRepository connectionRequestRepository;
   // private final ConcurrentHashMap<String, DeferredResult<List<UserDTO>>> connectionRequestHashMap;
    private final Logger logger = Logger.getLogger(UserService.class.getClass().getName());
   // private final ConcurrentHashMap<String, LocalDateTime> lastPolledIncomingRequests;
    public UserService(UserRepository userRepository,ConnectionRepository connectionRepository,ConnectionRequestRepository connectionRequestRepository){
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.connectionRequestRepository = connectionRequestRepository;
      //  this.connectionRequestHashMap = connectionRequestHashMap;
      //  this.lastPolledIncomingRequests = lastPolledIncomingRequests;

    }

    //Resolved
    public List<UserDTO> getAssociatedUsers(String userId){
        return connectionRepository.getAssociatedUsers(userId).stream().map(uId ->
             new UserDTO(userRepository.findById(uId).get())
        ).toList();
    }


    //Resolved
    public UserDTO searchUser(String username){
        UserInfo userInfo = userRepository.findUserByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userInfo.getUsername());
        userDTO.setUserId(userInfo.getUserId());
        return userDTO;
    }

    //Resolved
    public ConnectionDTO getUser(String requestIssuerId,String userId){

        UserInfo userInfo = userRepository.findById(userId).get();
        String username = userInfo.getUsername();
        ConnectionRequest request = connectionRequestRepository.findRequest(requestIssuerId,userId);
        if(request!=null){
            return new ConnectionDTO(userId,username,"pending");
        }
        Connection connection = connectionRepository.findConnection(requestIssuerId,userId);
        if(connection!=null){
            return new ConnectionDTO(userId,username,"active");
        }
        return new ConnectionDTO(userId,username,"none");

        //connectionRepository.findConnection(requestIssuerId,userRepository.findUserByUsername(username).getUserId());
    }


    //Resolved
    public ConnectionDTO addConnectionRequest(String requestIssuerId,String requestReceiverId){
        ConnectionRequest connectionRequest = new ConnectionRequest(requestIssuerId,requestReceiverId);
        connectionRequestRepository.save(connectionRequest);
        return new ConnectionDTO(requestReceiverId,userRepository.findById(requestIssuerId).get().getUsername(),"Pending");
    }
    //Resolved
    public void activateConnection(String userId1, String userId2){
        ConnectionRequest connectionRequest = connectionRequestRepository.findRequest(userId1,userId2);
        Connection connection = new Connection(connectionRequest.getRequestIssuerId(),connectionRequest.getRequestReceiverId());
        connectionRepository.save(connection);
        connectionRequestRepository.delete(connectionRequest);
    }
    public boolean isUserAadhaarVerified(String userId) throws UserNotFoundException{
        Optional<UserInfo> userInfoOptional = userRepository.findById(userId);
        if(userInfoOptional.isEmpty()){
            throw new UserNotFoundException();
        }
        return userInfoOptional.get().isAadhaarVerified();
    }
    public void saveUser(UserInfo userInfo){
        userRepository.save(userInfo);
    }

    public void saveHashedAadhaar(String userId,byte[] hashedAadhaar)throws SQLException{
            userRepository.saveHashedAadhaar(userId, hashedAadhaar);
    }

//    @Scheduled(fixedDelay=6000L)
//    public void poll(){
//        logger.info("Polling Incoming incoming messages and requests");
//
//
//    }
    public void removeConnectionRequest(String requestReceiverId,String requestIssuerId){
        connectionRequestRepository.deleteConnectionRequest(requestReceiverId,requestIssuerId);
    }
    public List<UserDTO> getNewConnectionRequests(String requestReceiverId){
        List<UserDTO> newConnectionRequests =  connectionRequestRepository.findNewConnectionRequests(requestReceiverId).stream().map(r -> new UserDTO(userRepository.findById(r.getRequestIssuerId()).get())).toList();
        connectionRequestRepository.updateDeliveryStatus(requestReceiverId);
        return newConnectionRequests;
    }

    public List<UserDTO> getAllConnectionRequests(String requestReceiverId){
        List<UserDTO> allConnectionRequests =  connectionRequestRepository.findByRequestReceiverId(requestReceiverId).get().stream().map(r -> new UserDTO(userRepository.findById(r.getRequestIssuerId()).get())).toList();
        connectionRequestRepository.updateDeliveryStatus(requestReceiverId);
        return allConnectionRequests;
    }

}
