package org.hotelbooking.Reponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public  static ResponseEntity<Object>getResponse(HttpStatus status, String message,boolean isError,Object myResponse) {
        Map<String,Object> map = new HashMap<>();
        try {
            map.put("status", status.value());
            map.put("message", message);
            map.put("error", isError);
            map.put("myResponse", myResponse);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (Exception e) {
            map.put("status", status.value());
            map.put("message", message);
            map.put("error", isError);
            map.put("myResponse", myResponse);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
