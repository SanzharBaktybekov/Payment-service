package com.baktybekov.demo.properties;

import com.baktybekov.demo.model.User;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class SampleUserDataProperties {
    UserData user;
    public void setUser(UserData user) {
        this.user = user;
    }
    public UserData getUser() {
        return user;
    }

    public User toUser() {
        return new User(user.username, user.password);
    }

    public static class UserData {
        String username;
        String password;

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
