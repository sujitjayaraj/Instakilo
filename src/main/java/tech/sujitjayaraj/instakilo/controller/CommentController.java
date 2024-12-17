package tech.sujitjayaraj.instakilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.sujitjayaraj.instakilo.dto.CommentDto;
import tech.sujitjayaraj.instakilo.entity.Comment;
import tech.sujitjayaraj.instakilo.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Comment comment = commentService.save(commentDto);

        return ResponseEntity.ok(comment);
    }
}
