package com.cibertec.repository;

import com.cibertec.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    @Query("SELECT DISTINCT p FROM Pizza p JOIN p.sizes s JOIN p.toppings t WHERE p.basePrice BETWEEN :minPrice AND :maxPrice AND s.id = :sizeId AND t.id = :toppingId")
    List<Pizza> findByPriceRangeAndSizeIdAndToppingId(@Param("minPrice") BigDecimal minPrice,
                                        @Param("maxPrice") BigDecimal maxPrice,
                                        @Param("sizeId") Long sizeId,
                                        @Param("toppingId") Long toppingId);
    @Query("SELECT DISTINCT p FROM Pizza p JOIN p.sizes s WHERE p.basePrice BETWEEN :minPrice AND :maxPrice AND s.id = :sizeId")
    List<Pizza> findByPriceRangeAndSizeId(@Param("minPrice") BigDecimal minPrice,
                                          @Param("maxPrice") BigDecimal maxPrice,
                                          @Param("sizeId") Long sizeId);
    @Query("SELECT DISTINCT p FROM Pizza p JOIN p.toppings t WHERE p.basePrice BETWEEN :minPrice AND :maxPrice AND t.id = :toppingId")
    List<Pizza> findByPriceRangeAndToppingId(@Param("minPrice") BigDecimal minPrice,
                                          @Param("maxPrice") BigDecimal maxPrice,
                                             @Param("toppingId") Long toppingId);

    List<Pizza> findByBasePriceBetween(BigDecimal basePriceAfter, BigDecimal basePriceBefore);

    List<Pizza> findByNameContaining(String name);
}
