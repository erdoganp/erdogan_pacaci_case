package factory;

import dto.CategoryRequestDto;
import dto.PetRequestDto;
import dto.PetStatus;
import dto.TagRequestDto;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class PetFactory {

    public static PetRequestDto createPet() {

        CategoryRequestDto category = CategoryRequestDto.builder()
                .id(1L)
                .name("Dogs")
                .build();

        TagRequestDto tag = TagRequestDto.builder()
                .id(1L)
                .name("cute")
                .build();

        return PetRequestDto.builder()
                .id(RandomUtils.nextLong())
                .category(category)
                .name("doggie")
                .photoUrls(List.of("url"))
                .tags(List.of(tag))
                .status(PetStatus.available)
                .build();
    }
}
