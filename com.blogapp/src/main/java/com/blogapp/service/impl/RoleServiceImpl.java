package com.blogapp.service.impl;

import com.blogapp.dao.RoleDao;
import com.blogapp.payloads.RoleDto;
import com.blogapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public RoleDto getRoleById(int roleId) {
        return null;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        return null;
    }
}
