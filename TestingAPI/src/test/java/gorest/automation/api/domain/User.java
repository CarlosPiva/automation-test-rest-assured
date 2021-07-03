package gorest.automation.api.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User{

    @JsonAlias("first_name")
    private String name;
    private String gender;
    private String email;
    private String status;

    public User(){}

    public User(String name, String gender, String email, String status){
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }
    public String getGender(){
        return this.gender;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return  this.status;
    }
}
