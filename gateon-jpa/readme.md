## jpa + dsl
 ` Searcher<CartItem> searcher = cartItemRepository.searcher();
     Where and = Where.and();
     and.eq("cartId",21);
     searcher.where(and);
     List<CartItem> cartItems = searcher.find();
     System.out.println(cartItems);
 `