package com.spring.devteams.service;

import com.spring.devteams.model.entity.History;
import com.spring.devteams.model.repo.HistoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {

    private final HistoryRepo historyRepo;
    
    



	public Page<History> listAll(int currentPage){
        Pageable pageable = PageRequest.of(currentPage -1 , 5);
        return historyRepo.findAll(pageable);
    }

}
