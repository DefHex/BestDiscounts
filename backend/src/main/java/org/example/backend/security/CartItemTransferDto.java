package org.example.backend.security;
public record CartItemTransferDto(String userId,String productId,Boolean addTrueFalseDelete) {}
//if add item is true item is added to the cart
// else item is removed from the cart
//(reason) duplicate code on add and delete