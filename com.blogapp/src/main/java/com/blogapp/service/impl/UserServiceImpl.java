package com.blogapp.service.impl;

import com.blogapp.config.AppConstants;
import com.blogapp.dao.RoleDao;
import com.blogapp.dao.UserDao;
import com.blogapp.entities.Role;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto getUserById(int userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return userToDto(userDao.getReferenceById(userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user: userList) {
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleDao.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        return userToDto(userDao.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        User updatedUser = userDao.save(dtoToUser(userDto));
        return userToDto(updatedUser);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        userDao.deleteById(userId);
    }

    public User dtoToUser(UserDto userDto) {
//        User user = new User();
//        user.setUserId(userDto.getUserId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getUserId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return modelMapper.map(user, UserDto.class);
    }
}
