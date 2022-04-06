package com.example.springweb.controller;

import com.example.springweb.dao.DepartmentDao;
import com.example.springweb.dao.EmployeeDao;
import com.example.springweb.entities.Department;
import com.example.springweb.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    @GetMapping("/emps")
    //查询所有员工页面，返回列表页面
    public String list(Model model){
        Collection<Employee> all = employeeDao.getAll();
        //放在请求域中进行共享
        model.addAttribute("emps",all);

        return "emp/list";
    }
    //员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){

        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        //来到添加页面
        return "emp/add";
    }
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("保存员工信息"+employee);

        employeeDao.save(employee);
        //redirect 重定向到一个地址
        //forward:表示转发到一个地址
        return "redirect:/emps";
    }
    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);

        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        //回到修改页面(add是一个修改添加二合一的页面);
        return "emp/add";
    }

    //员工修改；需要提交员工id；
    @PutMapping("/emp")
    public String updateEmployee(Employee employee){
        System.out.println("修改的员工数据："+employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }



}
