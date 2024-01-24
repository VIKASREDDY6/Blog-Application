package com.blogapp.service;

import com.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {
    public UserDto getUserById(int userId);
    public List<UserDto> getAllUsers();
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto, int userId);
    public void deleteUser(int userId);

}
