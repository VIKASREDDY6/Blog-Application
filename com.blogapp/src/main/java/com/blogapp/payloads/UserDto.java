package com.blogapp.payloads;

import com.blogapp.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int userId;
    @NotEmpty
    @Size(min = 4, message = "Username must be atleast 4 characters long")
    private String name;

    @Email(message = "Email is NOT Valid")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 10, message = "Password length must be between 4 and 10")
    private String password;

    @NotEmpty
    private String about;

    private Set<CommentDto> comments = new HashSet<>();

    private Set<RoleDto> roles = new HashSet<>();
}
