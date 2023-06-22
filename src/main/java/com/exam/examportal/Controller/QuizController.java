package com.exam.examportal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examportal.model.exam.Category;
import com.exam.examportal.model.exam.Quiz;
import com.exam.examportal.service.QuizService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://16.171.79.89/")
public class QuizController {
	@Autowired
	private QuizService quizService;
	
	@PostMapping("/")
	public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
	Quiz quiz1=this.quizService.addQuiz(quiz);
	return ResponseEntity.ok(quiz1);
	}
	
	@GetMapping("/{qid}")
	public Quiz getQuiz(@PathVariable("qid")Long qid) {
		return quizService.getQuiz(qid);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getQuizzes(){
		return ResponseEntity.ok(this.quizService.getQuizzes());
	}
	
	@PutMapping("/")
	public Quiz updateQuiz(@RequestBody Quiz quiz) {
		return this.quizService.updateQuiz(quiz);
	}
	
	@DeleteMapping("/{qid}")
	public void deleteQuiz(@PathVariable("qid")Long qid) {
		this.quizService.deleteQuiz(qid);
	}
	
	@GetMapping("/category/{cid}")
	public List<Quiz> getQuizzesOfCategory(@PathVariable("cid") Long cid){
		Category category=new Category();
		category.setCid(cid);
		return this.quizService.getQuizzesOfCategory(category);
		
	}
	
	@GetMapping("/active")
	public List<Quiz> getActiveQuizzes(){
		return this.quizService.getActiveQuizzes();
	}
	
	@GetMapping("/category/active/{cid}")
	public List<Quiz> getActiveQuizzes(@PathVariable("cid") Long cid){
		Category category=new Category();
		category.setCid(cid);
		return this.quizService.getActiveQuizzesOfCategory(category);
		
	}
}
