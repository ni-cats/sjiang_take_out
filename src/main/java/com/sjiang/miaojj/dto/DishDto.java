package com.sjiang.miaojj.dto;

import com.sjiang.miaojj.entity.Dish;
import com.sjiang.miaojj.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
