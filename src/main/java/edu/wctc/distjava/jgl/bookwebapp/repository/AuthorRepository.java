package edu.wctc.distjava.jgl.bookwebapp.repository;


import edu.wctc.distjava.jgl.bookwebapp.model.Author;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jlombardo
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    // example of a projection query
//    @Query("SELECT a.authorName FROM Author a")
//    public abstract Object[] findAllWithNameOnly();
    
    // Example to find all by name instead of id
//    public abstract List<Author> findByAuthorName(@Param ("authorName") String authorName);
    
    // Example to find all by name instead of id
    // 
//    @Query("select a from Author where a.authorName = :authorName")
//    public abstract List<Author> findByName(@Param ("authorName") String authorName);
}
