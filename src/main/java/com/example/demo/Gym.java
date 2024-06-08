package com.example.demo;

public class Gym {
    private String username;
    private String gym_email;
    private String password1;
    private String password2;

    public Gym(String username, String gym_email,
                    String password1,String password2) {

        this.username = username;
        this.gym_email = gym_email;
        this.password1 = password1;
        this.password2 = password2;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGym_email() {
        return gym_email;
    }

    public void setGym_email(String gym_email) {
        this.gym_email = gym_email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
