package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Category;
import models.Tag;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetResponseDto {

    private Long id;
    private CategoryResponseDto category;
    private String name;
    private String[] photoUrls;
    private TagResponseDto[] tags;
    private PetStatus  status;
}
