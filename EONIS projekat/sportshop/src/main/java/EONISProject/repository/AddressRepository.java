package EONISProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import EONISProject.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}