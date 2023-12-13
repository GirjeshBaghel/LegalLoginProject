package com.main.controller;

import com.main.entity.Cases;
import com.main.entity.Tasks;
import com.main.repo.TasksRepo;
import com.main.service.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TasksController {



    @Autowired
    private TaskServices taskServices;

    @Autowired
    private TasksRepo tasksRepo;


    @PostMapping("/registerTask/{caseNumber}")
    public ResponseEntity<?> registerTask(@PathVariable  String caseNumber, @RequestBody Tasks tasks) {
        ResponseEntity<?> registeredUser = taskServices.registerTask(caseNumber,tasks);
        return  registeredUser;
    }

    @GetMapping("/getTaskById/{taskId}")
    ResponseEntity<?> getTaskById(@PathVariable  String taskId){
        ResponseEntity<?> response = taskServices.getTaskById(taskId);
        return response;
    }
    @GetMapping("/allTasks")
    public ResponseEntity<?> getAllTask() {
        List<Tasks> response= tasksRepo.findAll();
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/allTaskByCases/{caseNumber}")
    public ResponseEntity<?> getAllTaskCases(@PathVariable String caseNumber) {
        ResponseEntity<?> response= taskServices.getAllTaskCases(caseNumber);
        return  response;
    }

    @PutMapping("/updateTask/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable String taskId, @RequestBody Tasks tasks) {
        ResponseEntity<?> response= taskServices.updateTask(taskId,tasks);
        return  response;
    }
    @DeleteMapping("/deleteTaskById/{taskId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable String taskId) {
        ResponseEntity<?> response = taskServices.deleteTask(taskId);
        return  response;
    }

    @GetMapping("/getLatestTask/{caseNumber}")
    public ResponseEntity<?> getLatestTask(@PathVariable String caseNumber) {
        ResponseEntity<?> response= taskServices.getLatestTask(caseNumber);
        return  response;
    }

    @GetMapping("/getUnActiveTask/{caseNumber}")
    public List<Tasks> getUnActiveTask(@PathVariable String caseNumber){
        List<Tasks>  response = tasksRepo.findByTaskStatusAndCases_CaseNumber(false,caseNumber);
        return  response;
    }
    @GetMapping("/getActiveCases/{caseNumber}")
    public List<Tasks> getActiveTask(@PathVariable String caseNumber){
        List<Tasks>  response = tasksRepo.findByTaskStatusAndCases_CaseNumber(true,caseNumber);
        return  response;
    }


}

