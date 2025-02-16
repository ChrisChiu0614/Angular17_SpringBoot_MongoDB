package com.chris.spring.data.mongodb.controller;

import com.chris.spring.data.mongodb.model.Tutorial;
import com.chris.spring.data.mongodb.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * is a RestController which has request mapping methods for RESTful requests such as: getAllTutorials, createTutorial, updateTutorial, deleteTutorial, findByPublished…
 */
@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<Map<String, Object>> getAllTutorials(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize
    ) {
        try {
            List<Tutorial> tutorials;
            // page and size
            Pageable paging = PageRequest.of(page, pageSize);

            //get all tutorials info
            Page<Tutorial> pageTutorials = tutorialService.getAllTutorials(title, paging);
            tutorials = pageTutorials.getContent();

//            if (tutorials.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }

            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", tutorials);
            response.put("currentPage", pageTutorials.getNumber());
            response.put("totalItems", pageTutorials.getTotalElements());
            response.put("totalPages", pageTutorials.getTotalPages());


            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
        try {
            Optional<Tutorial> tutorialData = tutorialService.getTutorialById(id);

            // Use lambda expression with Optional to simplify if-else logic.
            return tutorialData.map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            Tutorial savedTutorial = tutorialService.createTutorial(tutorial);
            return new ResponseEntity<>(savedTutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
        try {
            Tutorial savedTutorial = tutorialService.updateTutorial(id, tutorial);
            if (savedTutorial != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            if (tutorialService.deleteTutorial(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorial() {
        try {
            if(tutorialService.deleteAllTutorial()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Tutorial> tutorials;
            Pageable paging = PageRequest.of(page, size);
            Page<Tutorial> pageTutorials = tutorialService.findByPublished(paging);
//            if (tutorials.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
            tutorials = pageTutorials.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", tutorials);
            response.put("currentPage", pageTutorials.getNumber());
            response.put("totalItems", pageTutorials.getTotalElements());
            response.put("totalPages", pageTutorials.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
