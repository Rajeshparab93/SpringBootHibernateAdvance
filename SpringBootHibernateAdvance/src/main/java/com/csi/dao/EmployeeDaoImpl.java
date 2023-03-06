package com.csi.dao;

import com.csi.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDaoImpl implements EmployeeDao {

    private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();

    @Override
    public void signUp(Employee employee) {

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(employee);
        transaction.commit();

    }

    @Override
    public boolean signIn(String empEmailId, String empPassword) {
        boolean flag = false;
        for (Employee employee:getAllData()) {

            if(employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)) {

                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Employee getDataById(int empId) {
        Session session = factory.openSession();

        Employee employee = (Employee) session.get(Employee.class , empId);
        return employee;
    }

    @Override
    public void saveBulkOfData(List<Employee> employees) {

        Session session = factory.openSession();
        for(Employee employee: employees) {

            Transaction transaction = session.beginTransaction();

            session.save(employee);
            transaction.commit();
        }

    }

    @Override
    public List<Employee> getDataByUsingAnyInput(String input) {
        List<Employee>employeeList=new ArrayList<>();

        for (Employee employee:getAllData()){

            String dob;
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
            dob=simpleDateFormat.format(employee.getEmpDOB());
            if (employee.getEmpName().equals(input)|| String.valueOf(employee.getEmpId()).equals(input)|| String.valueOf(employee.getEmpSalary()).equals(input)||
                    String.valueOf(employee.getEmpContactNumber()).equals(input)||dob.equals(input)|| employee.getEmpPassword().equals(input)||employee.getEmpEmailId().equals(input)){

                employeeList.add(employee);
            }}

        return employeeList;
    }


    @Override
    public List<Employee> getAllData() {

        Session session = factory.openSession();

        return session.createQuery("from Employee").list();
    }

    @Override
    public void updateData(int empId, Employee employee) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Employee employee1 = getDataById(empId);

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpPassword(employee.getEmpPassword());

        session.update(employee1);
        transaction.commit();
    }

    @Override
    public void deleteDataById(int empId) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = getDataById(empId);

        session.delete(employee);
        transaction.commit();

    }

    @Override
    public void deleteAllData() {

        Session session = factory.openSession();

        for(Employee employee: getAllData()) {
            Transaction transaction = session.beginTransaction();

            session.delete(employee);
            transaction.commit();
        }

    }


}
