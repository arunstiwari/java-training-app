package com.kcb.demoapp.repository;

import com.kcb.demoapp.model.Complexity;
import com.kcb.demoapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,String> {


    @Query("Select q from Question q "+
        "WHERE (:category IS NULL or q.category = :category)" +
          "AND (:complexity is NULL or q.complexity =:complexity)" +
            "AND (:questionType is NULL or q.questionType =:questionType)"+
            "AND (:tags is NULL or EXISTS (Select t from q.tags t WHERE t IN :tags))"
    )
    List<Question> search(@Param("category") String category, @Param("tags") List<String> tags, @Param("complexity") Complexity complexity, @Param("questionType") String questionType);
}
