package webapp.controllers;

import data.User;

record UserResponse(
    String name
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.name());
    }
}
