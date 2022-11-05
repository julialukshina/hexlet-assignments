package exercise.repository;

import exercise.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // BEGIN
//    Добавьте в репозиторий метод, который позволит получить все комментарии
//    для определенного поста по его id. Метод должен вернуть список Iterable комментариев
//
//    Добавьте в репозиторий метод, который позволит получить комментарий по его id и id поста,
//    которому принадлежит комментарий.
    Iterable <Comment> findAllByPostId(Long postId);
    Optional<Comment> findByIdAndPostId(Long id, Long postId);
    // END
}
