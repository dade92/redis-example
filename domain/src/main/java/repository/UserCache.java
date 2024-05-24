package repository;

import data.User;

public interface UserCache  {

    User retrieve(String key);

}