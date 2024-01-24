package com.blogapp.service;

import com.blogapp.payloads.RoleDto;

public interface RoleService {
    public RoleDto getRoleById(int roleId);
    public RoleDto createRole(RoleDto roleDto);
}
