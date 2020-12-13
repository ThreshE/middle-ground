package com.cloud.paymentcenter.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserOrderForm {
    @NotNull(message = "页数不能为空")
    @Min(1)
    private Integer page;

    @NotNull(message = "每页记录数不能为空")
    @Range(min = 1, max = 50)
    private Integer length;

}
