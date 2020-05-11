package com.es.phoneshop.web.controller.pages;

import com.es.core.dto.ProductInfoDTO;
import com.es.core.dto.ProductPageDTO;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartModel;
import com.es.service.HttpSessionCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@Validated
public class QuickAddingProductsController {

    @Resource
    private HttpSessionCartService cartService;

    @GetMapping(value = "/quickAdding")
    public String getPage(@ModelAttribute(name = "productDTO") ProductPageDTO productPageDTO, Model model) {
        CartModel cart = cartService.getCart();
        cartService.saveItemsToSession();
        model.addAttribute("cart", cart);
        return "quickProductAddingPage";
    }

    @PostMapping(value = "/quickAdding/add")
    public String addToCart(@ModelAttribute(name = "productDTO") @Valid ProductPageDTO productPageDTO,
                            BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            cartService.addPhonesFromQuickPage(productPageDTO);
            return "redirect:/quickAdding";
        } else {
            List<ProductInfoDTO> pageDTOList = productPageDTO.getProductInfoDTOs();
            pageDTOList.stream()
                       .filter(product -> checkIfErrorExists(bindingResult, pageDTOList, product))
                       .forEach(product -> {
                           Long quantityToAdd = Long.valueOf(product.getQuantityToAdd());
                           cartService.addPhone(new AddingToCartModel(product.getProductId(), quantityToAdd));
                       });
        }
        model.addAttribute("errors", bindingResult);
        return "quickProductAddingPage";
    }

    private boolean checkIfErrorExists(BindingResult bindingResult,  List<ProductInfoDTO> pageDTOList, ProductInfoDTO product) {
        return bindingResult.getFieldError("productInfoDTOs[" + pageDTOList.indexOf(product) + "].modelToAdd") == null
               && bindingResult.getFieldError("productInfoDTOs[" + pageDTOList.indexOf(product) + "].quantityToAdd") == null;
    }
}