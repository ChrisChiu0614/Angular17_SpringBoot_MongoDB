package com.chris.spring.data.mongodb.service;

import com.chris.spring.data.mongodb.model.Tutorial;
import com.chris.spring.data.mongodb.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public Page<Tutorial> getAllTutorials(String title, Pageable paging) {

        return (title == null) ? tutorialRepository.findAll(paging) : tutorialRepository.findByTitleContaining(title, paging);
    }

    public Optional<Tutorial> getTutorialById(String id) {
        return tutorialRepository.findById(id);
    }

    public Tutorial createTutorial(Tutorial tutorial){
        return tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    }

    public Tutorial updateTutorial(String id, Tutorial tutorial){
        return tutorialRepository.findById(id).map(
                existingTutorial -> {
                    existingTutorial.setTitle(tutorial.getTitle());
                    existingTutorial.setDescription(tutorial.getDescription());
                    existingTutorial.setPublished(tutorial.isPublished());
                    return tutorialRepository.save(existingTutorial);
                }).orElseGet(()->null);
    }

    public boolean deleteTutorial(String id){
       if(tutorialRepository.existsById(id)){
           tutorialRepository.deleteById(id);
           return true;
       }
       return false;
    }

    public boolean deleteAllTutorial(){
        if(tutorialRepository.count()>0){
            tutorialRepository.deleteAll();
            return true;
        }
        return false;
    }

    public Page<Tutorial> findByPublished(Pageable paging){
        return tutorialRepository.findByPublished(true, paging);
    }
}
