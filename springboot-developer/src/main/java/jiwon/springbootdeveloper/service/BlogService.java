package jiwon.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import jiwon.springbootdeveloper.domain.Article;
import jiwon.springbootdeveloper.dto.AddArticleRequest;
import jiwon.springbootdeveloper.dto.UpdateArticleRequest;
import jiwon.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 클래스를 빈으로 서블릿 컨테이너에 등록해줌
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) { // save는 JpaRepository에서 지원하는 저장 메서드
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional //트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
