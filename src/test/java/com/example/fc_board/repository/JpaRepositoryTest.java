package com.example.fc_board.repository;

import com.example.fc_board.config.JpaConfig;
import com.example.fc_board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data.sql")
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_WhenSelecting_thenWorksFine() {
        //Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }
//    @DisplayName("insert 테스트")
//    @Test
//    void givenTestData_WhenInserting_thenWorkFine() {
//        //Given
//        Long previousCount = articleRepository.count();
//        Article article = Article.of("new article", "new content", "#spring");
//
//        // When
//        articleRepository.save(article);
//        System.out.println("##### Insert : " + previousCount);
//        System.out.println("##### Insert : " + articleRepository.count());
//        // Then
//        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
//    }
    @DisplayName("update 테스트")
    @Test
    void givenTestData_WhenUpdating_thenWorkFine() {
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }
//    @DisplayName("Delete 테스트")
//    @Test
//    void givenTestData_WhenDeleting_thenWorkFine() {
//        //Given
//        Article article = articleRepository.findById(1L).orElseThrow();
//        long  previousArticleCount = articleRepository.count();
//        long  previousArticleCommentCount = articleCommentRepository.count();
//        int deletedCommentSize = article.getArticleComments().size();
//
//        // When
//        articleRepository.delete(article);
//
//        // Then
//        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
//        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
//    }
}