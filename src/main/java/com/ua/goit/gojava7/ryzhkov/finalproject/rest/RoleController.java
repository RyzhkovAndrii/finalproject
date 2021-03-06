package com.ua.goit.gojava7.ryzhkov.finalproject.rest;

import com.ua.goit.gojava7.ryzhkov.finalproject.converter.ModelConversionService;
import com.ua.goit.gojava7.ryzhkov.finalproject.dto.RoleRequest;
import com.ua.goit.gojava7.ryzhkov.finalproject.dto.RoleResponse;
import com.ua.goit.gojava7.ryzhkov.finalproject.model.Role;
import com.ua.goit.gojava7.ryzhkov.finalproject.service.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final ModelConversionService conversionService;

    @ApiOperation("view list of roles")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<RoleResponse> getList() {
        return conversionService.convert(roleService.findAll(), RoleResponse.class);
    }

    @ApiOperation("search role with name") // todo same ulr like list
    @GetMapping(params = "name")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse getByName(@RequestParam String name) {
        return conversionService.convert(roleService.findByName(name), RoleResponse.class);
    }

    @ApiOperation("search role with ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse get(@PathVariable UUID id) {
        return conversionService.convert(roleService.findById(id), RoleResponse.class);
    }

    @ApiOperation("add role")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse save(@RequestBody RoleRequest roleRequest) {
        Role role = conversionService.convert(roleRequest, Role.class);
        return conversionService.convert(roleService.save(role), RoleResponse.class);
    }

    @ApiOperation("update role")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable UUID id, @RequestBody RoleRequest roleRequest) {
        Role role = conversionService.convert(roleRequest, Role.class);
        role.setId(id);
        roleService.update(role);
    }

    @ApiOperation("delete role")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) {
        roleService.delete(id);
    }

}
