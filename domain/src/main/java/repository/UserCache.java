package repository;

import data.User;

public interface UserCache  {

    void add(User user);
    User retrieve(String key);

}