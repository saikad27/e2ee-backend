package com.example.e2ee_backend.controller;

import com.example.e2ee_backend.dto.ConnectionDTO;
import com.example.e2ee_backend.dto.UserDTO;
import com.example.e2ee_backend.exception.UserNotFoundException;
import com.example.e2ee_backend.model.UserInfo;
import com.example.e2ee_backend.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private final UserService userService;
    private final Set<String> newRequestUserIds;
   // private final ConcurrentHashMap<String,DeferredResult<List<UserDTO>>> connectionRequestHashMap;

    public UserController(UserService userService, @Qualifier("newRequestUserIds") Set<String> newRequestUserIds){
        this.userService = userService;
        this.newRequestUserIds = newRequestUserIds;
       // this.connectionRequestHashMap = connectionRequestHashMap;
    }

    //4
    //This endpoint is called after user logs in
    @GetMapping("/get/connections")
    public List<UserDTO> getConnections(@AuthenticationPrincipal Jwt jwt){
        List<UserDTO> userDTOs = userService.getAssociatedUsers(jwt.getClaimAsString("sub"));
        for(UserDTO userDTO : userDTOs){
            System.out.println(userDTO);
        }
        return userDTOs;
    }

    //5
    //This endpoint is called when a searches a username

    @GetMapping("/get/user")
    public ConnectionDTO getUser(@AuthenticationPrincipal Jwt jwt,@RequestParam String userId){
        return userService.getUser(jwt.getClaimAsString("sub"),userId);
    }

    @GetMapping("/search")
    public UserDTO search(@RequestParam String username){
        System.out.println("Searching user : "+username);
        return userService.searchUser(username);
    }

    //6
    @PostMapping("/connection/request/send")
    public ConnectionDTO connect(@AuthenticationPrincipal Jwt jwt,@RequestParam String requestReceiverId){
        newRequestUserIds.add(requestReceiverId);
        return userService.addConnectionRequest(jwt.getClaimAsString("sub"),requestReceiverId);
    }
    @PostMapping("/connection/request/unsend")
    public void requestUnsend(){
        System.out.println("Unsending connection request");
    }

//    @PostMapping("/get/incoming/connection/requests")
//    public DeferredResult<?> getIncomingConnectionRequests(@AuthenticationPrincipal Jwt jwt){
//        DeferredResult<List<UserDTO>> result = new DeferredResult<>(30000L);
//        result.onTimeout( () -> result.setResult(new ArrayList<>()));
//        result.onCompletion(() -> connectionRequestHashMap.remove(jwt.getClaimAsString("sub")));
//        connectionRequestHashMap.put(jwt.getClaimAsString("sub"),result);
//        return result;
//    }

    @PostMapping("/get/new/incoming/connection/requests")
    public ResponseEntity<List<UserDTO>> getIncomingConnectionRequests(@AuthenticationPrincipal Jwt jwt){
        System.out.println("Fetching new connection Request for user : "+jwt.getClaimAsString("name"));
        if(newRequestUserIds.remove(jwt.getClaimAsString("sub"))){
            return ResponseEntity.ok(userService.getNewConnectionRequests(jwt.getClaimAsString("sub")));
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping("/get/all/incoming/connection/requests")
    public ResponseEntity<List<UserDTO>> getAllIncomingConnectionRequests(@AuthenticationPrincipal Jwt jwt){
        List<UserDTO> requests = userService.getAllConnectionRequests(jwt.getClaimAsString("sub"));
        if(requests.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(requests);
        }
    }

    @PostMapping("/decline/connection/request")
    public void declineConnectionRequest(@AuthenticationPrincipal Jwt jwt,@RequestParam String userId){
        userService.removeConnectionRequest(jwt.getClaimAsString("sub"),userId);
    }

    //7
    @PostMapping("/approve/connection/request")
    public void approve(@AuthenticationPrincipal Jwt jwt,@RequestParam String requestIssuerId){

        userService.activateConnection(requestIssuerId,jwt.getClaimAsString("sub"));
    }
    @PostMapping("/connection/remove")
    public void removeConnection(){
        System.out.println("Removing from connection");
    }
    //1
    @PostMapping("/verify/user")
    public ResponseEntity<String> verifyAadhaar(@AuthenticationPrincipal Jwt jwt)throws Exception{

        boolean aadhaarVerified;
        try{
            aadhaarVerified = userService.isUserAadhaarVerified(jwt.getClaimAsString("sub"));
        }catch(UserNotFoundException userNotFoundException){
            System.out.println("Registering new user");
            UserInfo newUser = new UserInfo();
            newUser.setUserId(jwt.getClaimAsString("sub"));
            newUser.setEmail(jwt.getClaimAsString("email"));
            newUser.setUsername(jwt.getClaimAsString("name"));
            newUser.setAadhaarVerified(false);
            userService.saveUser(newUser);
            aadhaarVerified = false;
        }
        if(!aadhaarVerified){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aadhaar not verified");
        }else{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Aadhaar verified successfully");       //call /get/connections
        }
    }



}
