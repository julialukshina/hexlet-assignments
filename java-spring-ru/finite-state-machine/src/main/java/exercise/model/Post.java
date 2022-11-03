package exercise.model;

import exercise.PostNotFoundException;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.GenerationType;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Lob
    private String body;

    private PostState state = PostState.CREATED;


    // BEGIN
//    Создайте метод publish(), который публикует статью.
//    Метод переводит пост в состояние PUBLISHED.
//    Переход в состояние PUBLISHED возможен только из состояния CREATED.
//    Если переход из текущего состояния в целевое возможен, метод устанавливает новое состояние и возвращает true,
//    как успешность выполнения операции. Если переход не возможен, метод возвращает false.
//
//    Создайте метод archive(), который отправляет статью в архив.
//    Метод переводит пост в состояние ARCHIVED.
//    Переход в состояние ARCHIVED возможен из состояний CREATED и PUBLISHED.
//    Если переход из текущего состояния в целевое возможен, метод устанавливает новое состояние и возвращает true,
//    как успешность выполнения операции. Если переход не возможен, метод возвращает false.
    public boolean publish() {
        if(PostState.CREATED.equals(state)){
            return true;
        }
        return false;
    }

    public boolean archive() {
        if(!PostState.ARCHIVED.equals(state)){
            return true;
        }
        return false;
    }
    // END
}
