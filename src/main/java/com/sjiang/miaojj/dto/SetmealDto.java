package com.sjiang.miaojj.dto;

import com.sjiang.miaojj.entity.Setmeal;
import com.sjiang.miaojj.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
