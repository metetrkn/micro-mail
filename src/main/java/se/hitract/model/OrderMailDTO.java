package se.hitract.model;


import java.util.List;
import se.hitract.model.enums.PRODUCT_TYPE;

public class OrderMailDTO {

    private Long orderId;
    private List<OrderItemDto> orderItems;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    // ==========================================
    // Nested DTOs
    // ==========================================

    public static class OrderItemDto {
        private List<UserProductDto> userProducts;
        private ProductOfferDto productOffer;

        public List<UserProductDto> getUserProducts() {
            return userProducts;
        }

        public void setUserProducts(List<UserProductDto> userProducts) {
            this.userProducts = userProducts;
        }

        public ProductOfferDto getProductOffer() {
            return productOffer;
        }

        public void setProductOffer(ProductOfferDto productOffer) {
            this.productOffer = productOffer;
        }
    }

    public static class UserProductDto {
        private Long userProductId;
        private String identifier;
        private String qrCode;

        public Long getUserProductId() {
            return userProductId;
        }

        public void setUserProductId(Long userProductId) {
            this.userProductId = userProductId;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }
    }

    public static class ProductOfferDto {
        // Verified: Matches "private String label;" in ProductOffer.java
        private String label;

        // Verified: Matches "private ProductBase productBase;" in ProductOffer.java
        private ProductBaseDto productBase;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public ProductBaseDto getProductBase() {
            return productBase;
        }

        public void setProductBase(ProductBaseDto productBase) {
            this.productBase = productBase;
        }
    }

    public static class ProductBaseDto {
        // Verified: Matches "private PRODUCT_TYPE productType;" in ProductBase.java
        private PRODUCT_TYPE productType;

        public PRODUCT_TYPE getProductType() {
            return productType;
        }

        public void setProductType(PRODUCT_TYPE productType) {
            this.productType = productType;
        }
    }
}