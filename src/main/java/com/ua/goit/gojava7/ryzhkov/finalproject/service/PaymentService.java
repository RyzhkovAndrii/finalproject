package com.ua.goit.gojava7.ryzhkov.finalproject.service;

import com.ua.goit.gojava7.ryzhkov.finalproject.model.Employee;
import com.ua.goit.gojava7.ryzhkov.finalproject.model.Payment;

import java.util.Collection;
import java.util.Date;

public interface PaymentService {

    Collection<Payment> getByEmployeeAndPeriod(Employee employee, Date startDate, Date finishDate);

}
