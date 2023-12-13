package com.main.service;

import com.main.Exception.BadRequestException;
import com.main.Exception.ResourceNotFoundException;
import com.main.Exception.ResponseClass;
import com.main.entity.Cases;
import com.main.entity.Clients;
import com.main.entity.Tasks;
import com.main.repo.CasesRepo;
import com.main.repo.TasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {

    @Autowired
    private TasksRepo tasksRepo;

    @Autowired
    private CasesRepo casesRepo;
    public ResponseEntity<?> registerTask(String caseNumber, Tasks tasks) {
        Cases cases = casesRepo.findByCaseNumber(caseNumber);
        if(cases == null)
        {
            throw  new ResourceNotFoundException("wrong case id");
        }
        tasks.setCases(cases);
        tasks.setRegisterTask(LocalDateTime.now());
        tasksRepo.save(tasks);
        List<Tasks> tasks1 = cases.getTasks();
        tasks1.add(tasks);
        cases.setTasks(tasks1);
        casesRepo.save(cases);
        String id = tasks.getTaskId();
        System.out.println(id);
        return ResponseClass.responseId(id,"Task Register Successfull");
    }

    public ResponseEntity<?> getTaskById(String taskId) {
        Tasks tasks = tasksRepo.findByTaskId(taskId);
        if(tasks == null)
        {
            throw  new ResourceNotFoundException("task not found");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    public ResponseEntity<?> getAllTaskCases(String caseId) {

        Cases cases = casesRepo.findByCaseNumber(caseId);
        if(cases == null)
        {
            throw  new ResourceNotFoundException("wrong case id");
        }
        List<Tasks> tasks = tasksRepo.findAllTaskByCases(caseId);
        return  ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    public ResponseEntity<?> updateTask(String taskId, Tasks tasks) {
        Tasks tasks1 = tasksRepo.findByTaskId(taskId);
        if(tasks1 == null)
        {
            throw  new ResourceNotFoundException("task not found from this Id");
        }
        tasks1.setTaskDetails(tasks.getTaskDetails());
        tasks1.setTaskStatus(tasks.isTaskStatus());
        tasks1.setLastModification(LocalDateTime.now());
        tasksRepo.save(tasks1);
        return  ResponseClass.response("Task Updates Successfully");
    }

    public ResponseEntity<?> deleteTask(String taskId) {
        Tasks tasks1 = tasksRepo.findByTaskId(taskId);
        if(tasks1 == null)
        {
            throw  new ResourceNotFoundException("task not found from this Id");
        }
        tasksRepo.delete(tasks1);
        return  ResponseClass.response("Task Deleted Successfully");
    }


    public ResponseEntity<?> getLatestTask(String caseId) {
        Cases cases = casesRepo.findByCaseNumber(caseId);
        if(cases == null)
        {
            throw  new ResourceNotFoundException("wrong case id");
        }
        List<Tasks> tasks = tasksRepo.findLatestModificationByCaseNumber(caseId);
        return  ResponseEntity.status(HttpStatus.OK).body(tasks);
    }
}

