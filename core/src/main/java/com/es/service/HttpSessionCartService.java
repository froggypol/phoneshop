package com.es.service;

import com.es.core.dto.ProductPageDTO;
import com.es.core.model.AddingToCartModel;
import com.es.core.model.CartItemModel;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.CartModel;
import com.es.core.model.PhoneModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("classpath:properties/application.properties")
public class HttpSessionCartService implements CartService {

    @Value("#{new java.lang.Integer(${quickItems.number})}")
    private int itemsOnQuickPage;

    @Resource
    private HttpSession session;

    @Resource
    private PhoneService phoneService;

    @Override
    public CartModel getCart() {
        CartModel cart = (CartModel) session.getAttribute("cart");
        return cart;
    }

    @Override
    public void addPhonesFromQuickPage(ProductPageDTO productPageDTO) {
        productPageDTO.getProductInfoDTOs().stream()
                                           .forEach(productDTO -> addPhone(new AddingToCartModel(productDTO.getProductId(),
                                                                            Long.valueOf(productDTO.getQuantityToAdd()))));
    }

    @Override
    public void addPhone(AddingToCartModel ajaxAddingToCartModel) throws OutOfStockException {
        Long quantity = ajaxAddingToCartModel.getQuantityToAdd();
        Long phoneId = ajaxAddingToCartModel.getIdOfAddingPhone();
        CartItemModel cartItem = new CartItemModel(quantity, phoneService.get(phoneId));
        PhoneModel phoneToAdd = phoneService.get(phoneId);
        CartModel cart = getCart();
        List<CartItemModel> cartItemList = cart.getCartItems();
        CartItemModel checkedCartItem = new CartItemModel(quantity, phoneToAdd);
        if (cartItemList.contains(checkedCartItem)) {
            CartItemModel addedCartItem = cartItemList.get(cartItemList.indexOf(checkedCartItem));
            if (quantity <= phoneToAdd.getStock() - addedCartItem.getQuantity()){
                cart.updateAddingToCart(cartItem, addedCartItem);
            }
        } else if (!cartItemList.contains(checkedCartItem) && quantity <= phoneToAdd.getStock()) {
            cart.addToCart(cartItem);
        } else {
            throw new OutOfStockException();
        }
    }

    @Override
    public void update(Map<Long, String> itemsWithNewQnt, List<CartItemModel> cartItemModels) {
        CartModel cart = getCart();
        itemsWithNewQnt.keySet().forEach(phoneId -> {
            PhoneModel addedPhoneModel = phoneService.get(phoneId);
            int index = 0;
            for(CartItemModel model : cartItemModels) {
                if (model.getPhone().equals(addedPhoneModel)) {
                    index = cartItemModels.indexOf(model);
                }
            }
            CartItemModel addedCartItemModel = new CartItemModel(cartItemModels.get(index).getQuantity(),
                    addedPhoneModel);
            CartItemModel updatedCartIem = new CartItemModel(Long.valueOf(itemsWithNewQnt.get(phoneId)), addedPhoneModel);
            cart.updateCart(addedCartItemModel, updatedCartIem);
        });
    }

    @Override
    public void remove(Long phoneId) {
        CartModel cartModel = getCart();
        PhoneModel phoneModelToDelete = phoneService.get(phoneId);
        cartModel.deleteFomCart(phoneModelToDelete);
    }

    public void saveItemsToSession() {
        session.setAttribute("itemsNumber", itemsOnQuickPage);
    }

    public int getItemsOnQuickPage() {
        return itemsOnQuickPage;
    }

    public void setItemsOnQuickPage(int itemsOnQuickPage) {
        this.itemsOnQuickPage = itemsOnQuickPage;
    }
}
