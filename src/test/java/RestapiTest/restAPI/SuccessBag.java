package RestapiTest.restAPI;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SuccessBag {
 private Integer id;
 private String token;

 public SuccessBag(Integer id, String token) {
  this.id = id;
  this.token = token;
 }

 public SuccessBag() {
 }

 public Integer getId() {
  return id;
 }

 public String getToken() {
  return token;
 }
}

