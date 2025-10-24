package com.cibertec.util;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.model.Pizza;
import com.cibertec.model.Topping;
import com.cibertec.repository.ToppingRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Mapper para convertir entre PizzaRequest, PizzaResponse y la entidad Pizza.
 * Utiliza ToppingMapper para mapear los toppings asociados a una pizza.
 * @author Frank
 */
@Mapper(componentModel = "spring", uses = {ToppingMapper.class, PizzaMapper.class})
public interface PizzaMapper {
    @Mapping(source = "toppingIds", target = "toppings")
    @Mapping(source = "image", target = "image", qualifiedByName = "extractPizzaImageName")
    Pizza toEntity(PizzaRequest pizzaRequest, @Context ToppingRepository toppingRepository, @Context String name);
    PizzaResponse toDto(Pizza pizza);
    List<PizzaResponse> toDtoList(List<Pizza> pizzas);

    default List<Topping> map(List<Long> toppingIds, @Context ToppingRepository toppingRepository) {
        if(toppingIds == null || toppingIds.isEmpty()) {
            return List.of();
        }
        return toppingRepository.findAllById(toppingIds);
    }
    /**
     * Genera el nombre del archivo de imagen basado en el nombre o SKU recibido.
     */
    @Named("extractPizzaImageName")
    static String extractPizzaImageName(MultipartFile image, @Context String name) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        String originalName = image.getOriginalFilename();
        if (originalName == null) {
            return null;
        }

        // Retornar el nuevo nombre con la extensión
        return name + ".jpg";
    }
}
