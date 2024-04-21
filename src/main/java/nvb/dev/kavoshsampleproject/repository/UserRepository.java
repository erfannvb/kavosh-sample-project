package nvb.dev.kavoshsampleproject.repository;

import nvb.dev.kavoshsampleproject.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);

}
