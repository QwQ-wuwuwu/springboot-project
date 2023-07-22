package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select * from user where name=:name")
    User findByName(@Param("name") String name);
    @Query("select * from user u where u.role=0 and u.teacher_id is null")
    List<User> listUnSelect();
    @Query("select * from user u where u.role=0 and u.teacher_name=:teacherName;")
    List<User> listSelect(@Param("teacherName") String teacherName);
    @Query("select u.id, u.name,u.total,u.count from user u where u.role=1")
    List<User> listTeacher();
    @Modifying
    @Query("update user u set u.password=:newPassword where u.name=:name")
    boolean updatePassword(@Param("name") String name,@Param("newPassword") String newPassword);
    @Modifying
    @Query("update user u set u.password=:newPassword where u.role = 0 or u.role = 1")
    boolean reSetPassword(@Param("newPassword") String newPassword);
    @Query("select * from user u where u.teacher_name is not null")
    List<User> listStudentAndTeacher();
    @Query("select * from user u where u.id=:id for update;")//for update是悲观锁，保证并发性
    User findByIdForUpdate(long id);
}
