package org.projii.serverside.cs;

public class UserInfo {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private int experience;

    public UserInfo(int id, String email, String password, String nickname, int experience) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getExperience() {
        return experience;
    }
}
