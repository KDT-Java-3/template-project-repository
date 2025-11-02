package bootcamp.project.domain.purchase.repository;

import bootcamp.project.domain.purchase.entity.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {

    List<PurchaseProduct> findByPurchase_Id(Long purchaseId);

}
