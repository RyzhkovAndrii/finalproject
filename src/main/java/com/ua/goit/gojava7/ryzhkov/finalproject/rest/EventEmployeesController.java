package com.ua.goit.gojava7.ryzhkov.finalproject.rest;

import com.ua.goit.gojava7.ryzhkov.finalproject.converter.ModelConversionService;
import com.ua.goit.gojava7.ryzhkov.finalproject.dto.EmployeeResponse;
import com.ua.goit.gojava7.ryzhkov.finalproject.service.EventService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(value = "/events/{event}/employees", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
@RequiredArgsConstructor
public class EventEmployeesController {

    private final EventService eventService;

    private final ModelConversionService conversionService;

    @ApiOperation("view list of event's employees")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<EmployeeResponse> getEventEmployees(@PathVariable("event") UUID id) {
        return conversionService.convert(eventService.findById(id).getEmployees(), EmployeeResponse.class);
    }

    @ApiOperation("add employee to event")
    @PutMapping("/{employee}")
    @ResponseStatus(HttpStatus.OK)
    public void addEventEmployee(@PathVariable("event") UUID eventId, @PathVariable("employee") UUID employeeId) {
        eventService.addEmployeeToEvent(eventId, employeeId);
    }

    @ApiOperation("delete employee from event")
    @DeleteMapping("/{employee}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventEmployee(@PathVariable("event") UUID eventId, @PathVariable("employee") UUID employeeId) {
        eventService.deleteEmployeeFromEvent(eventId, employeeId);
    }

}
