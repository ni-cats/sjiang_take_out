package com.sjiang.miaojj.dto;

import com.sjiang.miaojj.entity.Setmeal;
import com.sjiang.miaojj.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

@Data
@ApiModel("套餐封装模型Dto")
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
