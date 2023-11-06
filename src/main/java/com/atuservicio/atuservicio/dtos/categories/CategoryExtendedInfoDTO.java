package com.atuservicio.atuservicio.dtos.categories;

public class CategoryExtendedInfoDTO extends CategoryInfoDTO{
    private String image_id;

    public CategoryExtendedInfoDTO(String id, String name, String image_id) {
        super(id, name);
        this.image_id = image_id;
    }
}
