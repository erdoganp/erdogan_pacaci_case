package dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetRequestDto {

    private Long id;
    private CategoryRequestDto category;
    private String name;
    private List<String> photoUrls;
    private List<TagRequestDto> tags;
    private PetStatus  status;
}
