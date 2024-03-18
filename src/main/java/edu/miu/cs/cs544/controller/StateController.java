package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.dto.StateDTO;
import edu.miu.cs.cs544.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addState(@Valid @RequestBody StateDTO stateDTO){
        return new ResponseEntity<>(stateService.addState(stateDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllStates(){
        return new ResponseEntity<List<StateDTO>>( stateService.findAllStates(), HttpStatus.OK);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<?> getState(@PathVariable Integer stateId){
        return new ResponseEntity<StateDTO>(stateService.findById(stateId), HttpStatus.OK);
    }

    @PutMapping("/{stateId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateState(@PathVariable Integer stateId, @Valid @RequestBody StateDTO stateDTO){
        return new ResponseEntity<StateDTO>(stateService.updateState(stateId, stateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{stateId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteState(@PathVariable Integer stateId){
        return new ResponseEntity<String>(stateService.deleteById(stateId), HttpStatus.OK);
    }

}
