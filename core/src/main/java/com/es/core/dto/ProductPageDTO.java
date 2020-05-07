package com.es.core.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductPageDTO {

    @NotNull
    @Valid
    private List<ProductInfoDTO> productInfoDTOs;

    public ProductPageDTO() {
    }

    public List<ProductInfoDTO> getProductInfoDTOs() {
        return productInfoDTOs;
    }

    public void setProductInfoDTOs(List<ProductInfoDTO> productInfoDTOs) {
        this.productInfoDTOs = productInfoDTOs;
    }
}
