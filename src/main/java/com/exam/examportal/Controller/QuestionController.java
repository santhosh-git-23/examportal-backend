package com.exam.examportal.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.exam.examportal.model.exam.Question;
import com.exam.examportal.model.exam.Quiz;
import com.exam.examportal.service.QuestionService;
import com.exam.examportal.service.QuizService;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://16.171.79.89/")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	@PostMapping("/")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question){
	Question question1=this.questionService.addQuestion(question);
	return ResponseEntity.ok(question1);
	}
	
	@PutMapping("/")
	public Question updateQuestion(@RequestBody Question question) {
		return this.questionService.updateQuestion(question);
	}
	
	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable ("qid") Long qid){
//		Quiz quiz=new Quiz();
//		quiz.setQuid(qid);
//		Set<Question> questionsOfQuiz=this.questionService.getQuestionsOfQuiz(quiz);
//		return ResponseEntity.ok(questionsOfQuiz);
		
		Quiz quiz=this.quizService.getQuiz(qid);
		Set<Question> questions=quiz.getQuestions();
		List list=new ArrayList(questions);
		if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())) {
			list=list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
		}
		list.forEach((q)->{
			((Question) q).setAnswer("");
		});
		
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
		
	}
	
	@GetMapping("/quiz/all/{qid}")
	public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable ("qid") Long qid){
	Quiz quiz=new Quiz();
	quiz.setQuid(qid);
	Set<Question> questionsOfQuiz=this.questionService.getQuestionsOfQuiz(quiz);
	return ResponseEntity.ok(questionsOfQuiz);
		
		
	}
	
	@GetMapping("/{quesId}")
	public Question getQuestion(@PathVariable("quesId")Long quesId) {
		return this.questionService.getQuestion(quesId);
	}
	
	@DeleteMapping("/{quesId}")
	public void deleteQuestion(@PathVariable("quesId")Long quesId) {
		this.questionService.deleteQuestion(quesId);
	}
	
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
		Double marksGot=(double) 0;
		int correctAnswers=0;
		int attempted=0;
		for(Question q:questions) {
			Question question=this.questionService.get(q.getQuesId());
			if(question.getAnswer().equals(q.getGivenAnswer())) {
				correctAnswers++;
				double marksSingle=Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
						marksGot+=marksSingle;
			}
			if(q.getGivenAnswer()!=null){
				attempted++;
			}
		}
		Map<String,Object> map=Map.of("marksGot",marksGot,"correctAnswers",correctAnswers,"attempted",attempted);
		return ResponseEntity.ok(map);
	}
	
}
