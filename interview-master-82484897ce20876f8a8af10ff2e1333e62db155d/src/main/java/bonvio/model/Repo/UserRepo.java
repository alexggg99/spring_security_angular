package bonvio.model.Repo;


import bonvio.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by alexggg99 on 04.12.15.
 */

@Transactional
public interface UserRepo extends CrudRepository<User, Long> {

//        @Query("select u from User u where u.idVk = ?1")
//        public User findByVKuser(int idVk);

        public User findByUsername(String username);

        public User findById(Integer id);

        public User findByIdVk(long idVk);

}