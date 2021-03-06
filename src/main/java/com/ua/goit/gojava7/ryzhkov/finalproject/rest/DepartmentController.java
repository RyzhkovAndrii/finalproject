package com.ua.goit.gojava7.ryzhkov.finalproject.rest;

import com.ua.goit.gojava7.ryzhkov.finalproject.converter.ModelConversionService;
import com.ua.goit.gojava7.ryzhkov.finalproject.dto.DepartmentRequest;
import com.ua.goit.gojava7.ryzhkov.finalproject.dto.DepartmentResponse;
import com.ua.goit.gojava7.ryzhkov.finalproject.model.Department;
import com.ua.goit.gojava7.ryzhkov.finalproject.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/departments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    private final ModelConversionService conversionService;

    @ApiOperation("view list of departments")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<DepartmentResponse> getList() {
        return conversionService.convert(departmentService.findAll(), DepartmentResponse.class);
    }

    @ApiOperation("search department with name") // todo same ulr like list
    @GetMapping(params = "name")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse getByName(@RequestParam String name) {
        return conversionService.convert(departmentService.findByName(name), DepartmentResponse.class);
    }

    @ApiOperation("search department with ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse get(@PathVariable UUID id) {
        return conversionService.convert(departmentService.findById(id), DepartmentResponse.class);
    }

    @ApiOperation("add department")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse save(@RequestBody DepartmentRequest departmentRequest) {
        Department department = conversionService.convert(departmentRequest, Department.class);
        return conversionService.convert(departmentService.save(department), DepartmentResponse.class);
    }

    @ApiOperation("update department")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable UUID id, @RequestBody DepartmentRequest departmentRequest) {
        Department department = conversionService.convert(departmentRequest, Department.class);
        department.setId(id);
        departmentService.save(department);
    }

    @ApiOperation("delete department")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) {
        departmentService.delete(id);
    }

}
