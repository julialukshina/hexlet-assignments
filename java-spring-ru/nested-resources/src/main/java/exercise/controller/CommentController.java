package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import liquibase.pro.packaged.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN

//    GET /posts/{postId}/comments - вывод всех комментариев для конкретного поста.
//    Должны выводится только комментарии, принадлежащие посту.
    @GetMapping("/{postId}/comments")
    public Iterable <Comment> getCommentsOfPost(@PathVariable Long postId){
        return commentRepository.findAllByPostId(postId);
    }

//            GET /posts/{postId}/comments/{commentId} - вывод конкретного комментария для поста.
//            Должны выводится только комментарий, принадлежащие посту. Если такой комментарий не существует,
//            должен вернуться ответ с кодом 404.
    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByCommentIdAndPostId(@PathVariable Long postId, @PathVariable Long commentId){
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }
//    POST /posts/{postId}/comments - создание нового комментария для поста.
//    Должны выводится только комментарий, принадлежащие посту.
@PostMapping("/{postId}/comments")
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment){
        Post post = postRepository.findById(postId).get();
        comment.setPost(post);
        return commentRepository.save(comment);
}
//            PATCH /posts/{postId}/comments/{commentId} - редактирование конкретного комментария поста.
//            Если такой комментарий не существует, должен вернуться ответ с кодом 404.
    @PatchMapping("/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment){
      Comment updatingComment =  commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
      if(comment.getContent() != null){
          updatingComment.setContent(comment.getContent());
      }
      return commentRepository.save(updatingComment);
    }

//    DELETE /posts/{postId}/comments/{commentId} - удаление конкретного комментария у поста.
//    Если такой комментарий не существует, должен вернуться ответ с кодом 404.
@DeleteMapping("/{postId}/comments/{commentId}")
public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
    Comment comment =  commentRepository.findByIdAndPostId(commentId, postId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

     commentRepository.delete(comment);
}
    // END
}
