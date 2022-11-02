package exercise.controller;

import exercise.model.Article;
import exercise.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        this.articleRepository.deleteById(id);
    }

//    Создайте метод, который обрабатывает PATCH-запросы по пути /articles/{id} и обновляет данные сущности.
//
//    Создайте метод, который обрабатывает GET-запросы по пути /articles/{id} и возвращает данные конкретной статьи по её id в виде JSON.
//
//    Запустите приложение и попробуйте отправлять ему различные запросы при помощи POSTMAN. Убедитесь, что при просмотре конкретной статьи
//    данные статьи содержат данные категории, которой принадлежит статья. Категории статей, которые добавляются в базу данных,
//    можно посмотреть в файле src/main/resources/import.sql

    // BEGIN
    @PostMapping
    public Article createArticle(@RequestBody Article article){
        return this.articleRepository.save(article);
    }

    @PatchMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article){
        article.setId(id);
        return this.articleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id){
        return this.articleRepository.findById(id).get();
    }
    // END
}
