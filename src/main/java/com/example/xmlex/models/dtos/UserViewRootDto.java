package com.example.xmlex.models.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {

    @XmlElement(name = "user")
    private List<UserWithProductDto> products;

    public List<UserWithProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<UserWithProductDto> products) {
        this.products = products;
    }
}
