package nvb.dev.kavoshsampleproject.repository;

import nvb.dev.kavoshsampleproject.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
}
