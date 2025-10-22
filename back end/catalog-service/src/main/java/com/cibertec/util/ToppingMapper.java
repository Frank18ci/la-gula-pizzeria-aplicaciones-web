package com.cibertec.util;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.dto.ToppingResponse;
import com.cibertec.model.Topping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToppingMapper {
    @Mapping(source = "image", target = "image", qualifiedByName = "extractToppingImageName")
    Topping toEntity(ToppingRequest toppingRequest, @Context String name);
    ToppingResponse toDto(Topping topping);
    List<ToppingResponse> toDtoList(List<Topping> toppings);

    @Named("extractToppingImageName")
    static String extractToppingImageName(MultipartFile image, @Context String name) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        // Retornar el nuevo nombre con la extensión
        return name + ".jpg";
    }
}
