package gradleProject.shop3.mapper;

import gradleProject.shop3.domain.Item;
import gradleProject.shop3.dto.ItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(ItemDto ItemDto);
    ItemDto toDto(Item Item);
}
