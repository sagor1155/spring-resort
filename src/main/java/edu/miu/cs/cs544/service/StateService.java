package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.State;
import edu.miu.cs.cs544.dto.CountryDTO;
import edu.miu.cs.cs544.dto.StateDTO;

import java.util.List;

public interface StateService {

    public StateDTO addState(StateDTO stateDTO);
    public List<StateDTO> findAllStates();
    public StateDTO findById(Integer id);
    public StateDTO updateState(Integer id, StateDTO stateDTO);
    public String deleteById(Integer id);
}
