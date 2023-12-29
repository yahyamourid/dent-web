package com.dentsbackend.controllers;

import com.dentsbackend.entities.*;
import com.dentsbackend.services.GroupService;
import com.dentsbackend.services.PWService;
import com.dentsbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {
    @Autowired
    GroupService groupeService;

    @Autowired
    UserService userService;

    @Autowired
    PWService pwService;

    @PostMapping("/add/{id}")
    public ResponseEntity<Object> create(@RequestBody Groupe groupe,@PathVariable long id){
        Professor user = (Professor) userService.findById(id);
        if(user == null)
            return ResponseEntity.ok(Map.of("message","user does not exist"));
        else{
            groupe.setProfessor(user);
            groupeService.create(groupe);
            return ResponseEntity.ok(Map.of("message","group created successfully"));
        }

    }

    @GetMapping("/all")
    public List<Groupe> getAll(){
        return groupeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id){
        Groupe groupe = groupeService.findById(id);
        if(groupe == null)
            return new ResponseEntity<>(Map.of("message","group does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(groupe);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Groupe groupe){
        Groupe groupe1 = groupeService.findById(id);
        if (groupe1 == null)
            return new ResponseEntity<>(Map.of("message","group does not exist"), HttpStatus.NOT_FOUND);
        else{
            groupe.setId(id);
            groupeService.update(groupe);
            return ResponseEntity.ok(Map.of("message","group updated successfully"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id){
        Groupe groupe = groupeService.findById(id);
        if (groupe == null)
            return new ResponseEntity<>(Map.of("message","group does not exist"), HttpStatus.NOT_FOUND);
        else{
            groupeService.delete(groupe);
            return ResponseEntity.ok(Map.of("message","group deleted successfully"));
        }
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<?> getByProfessor(@PathVariable long id){
        Professor prof = (Professor) userService.findById(id);
        if(prof == null)
            return new ResponseEntity<>(Map.of("message","Professor does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(groupeService.findByProfessor(prof));
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getByStudent(@PathVariable long id){
        Student student = (Student) userService.findById(id);
        if(student == null)
            return new ResponseEntity<>(Map.of("message","student does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(groupeService.findByStudent(student));
    }
    @GetMapping("/pw/{id}")
    public ResponseEntity<?> getByPw(@PathVariable long id){
        PW pw =  pwService.findById(id);
        if(pw == null)
            return new ResponseEntity<>(Map.of("message","PW does not exist"), HttpStatus.NOT_FOUND);
        else
            return ResponseEntity.ok(groupeService.findByPW(pw));
    }

    @PostMapping("/pw/{groupId}/{pwId}")
    public ResponseEntity<Object> addPwtoGroup(@PathVariable Long groupId, @PathVariable Long pwId){
        Groupe group = groupeService.findById(groupId);
        PW pw = pwService.findById(pwId);
        if(group == null || pw ==null){
            return new ResponseEntity<>(Map.of("message","PW or group does not exist"), HttpStatus.NOT_FOUND);
        }
        else{
            group.getPws().add(pw);
            groupeService.update(group);
            return ResponseEntity.ok(group);
        }
    }

    @DeleteMapping("/pw/{groupId}/{pwId}")
    public ResponseEntity<Object> deletePwfromGroup(@PathVariable Long groupId, @PathVariable Long pwId){
        Groupe group = groupeService.findById(groupId);
        PW pw = pwService.findById(pwId);
        if(group == null || pw ==null){
            return new ResponseEntity<>(Map.of("message","PW or group does not exist"), HttpStatus.NOT_FOUND);
        }
        else{
            group.getPws().remove(pw);
            groupeService.update(group);
            return ResponseEntity.ok(group);
        }
    }
}
