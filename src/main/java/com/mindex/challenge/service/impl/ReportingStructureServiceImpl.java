package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reporting structure with id [{}]", id);

        Employee employee = employeeService.read(id);

        return new ReportingStructure(employee, getNumberOfReports(employee));
    }

    /**
     * Returns the number of distinct reports for a given employee.
     *
     * @param employee The employee from which to get the number of reports.
     * @return The number of reports.
     */
    private int getNumberOfReports(Employee employee) {
        Set<String> employeeSet = new HashSet<>();
        // Separated into another method so the consumer of this method doesn't have to manage a set.
        return getNumberOfReports(employeeSet, employee);
    }

    /**
     * Returns the recursively calculated number of distinct reports for a given employee.
     *
     * @param employeeSet The set of employee IDs.
     * @param employee The employee from which to calculate.
     * @return The number of reports.
     */
    private int getNumberOfReports(Set<String> employeeSet, Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        int numberOfReports = 0;

        if (!CollectionUtils.isEmpty(directReports)) {
            for (Employee directReport : directReports) {
                String employeeId = directReport.getEmployeeId();
                if (!employeeSet.contains(employeeId)) {
                    employeeSet.add(employeeId);
                    // The Employee objects of Employee#directReports only contain the employeeId and not their own direct reports.
                    // One way to resolve this is to query the direct report Employee.
                    // Another way is to use the relevant MongoDB decorators in Employee to fully resolve the direct reports.
                    // - The main decorator would be @DBRef on Employee#directReports with "lazy = true".
                    numberOfReports += (1 + getNumberOfReports(employeeSet, employeeService.read(employeeId)));
                }
            }
        }

        return numberOfReports;
    }
}
