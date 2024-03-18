package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.adapter.StateAdapter;
import edu.miu.cs.cs544.domain.State;
import edu.miu.cs.cs544.dto.StateDTO;
import edu.miu.cs.cs544.repository.StateRepository;
import edu.miu.cs.cs544.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StateServiceImpl implements StateService {

    @Autowired
    private final StateAdapter stateAdapter;

    @Autowired
    private final StateRepository stateRepository;

    @Override
    public StateDTO addState(StateDTO stateDTO) {
        try {
            State state = stateAdapter.dtoToEntity(stateDTO);
            State stateResponse = stateRepository.save(state);
            return stateAdapter.entityToDto(stateResponse);
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to add this state");
        }
    }

    @Override
    public List<StateDTO> findAllStates() {
        return stateAdapter.entityToDtoAll(stateRepository.findAll());
    }
    @Override
    public StateDTO findById(Integer id) {
        Optional<State> stateOptional = stateRepository.findById(id);
        State state = stateOptional.orElseThrow(() -> new EntityNotFoundException("State with id " + id + " not found"));
        return stateAdapter.entityToDto(state);
    }
    @Override
    public StateDTO updateState(Integer id, StateDTO stateDTO) {
        try {
            Optional<State> state = stateRepository.findById(id);
            if (state.isPresent()) {
                State stateEntity = stateAdapter.dtoToEntity(stateDTO);
                stateEntity.setId(id);
                State stateResponse = stateRepository.save(stateEntity);
                return stateAdapter.entityToDto(stateResponse);
            } else {
                throw new EntityNotFoundException("State with id " + id + " not found");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to update this state");
        }
    }

    @Override
    public String deleteById(Integer id) {
        try {
            stateRepository.deleteById(id);
            return "State deleted successfully";
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Failed to delete this member");
        }
    }
}
