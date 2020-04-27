## jpa + dsl
 `   Searcher<CartItem> searcher = cartItemRepository.searcher();
     Where and = Where.and();
     and.eq("cartId",21);
     searcher.where(and);
     List<CartItem> cartItems = searcher.find();
     System.out.println(cartItems);
 `
 
 `        List<CartItem> cartItems = queryFactory.selectFrom(QCartItem.cartItem)
                  .leftJoin(QCart.cart).on(QCartItem.cartItem.cartId.eq(QCart.cart.id))
                  .where(QCart.cart.openId.eq("oddJ85USFtEWJNrAy95h96U1lPQg"))
                  .fetch();  
          BooleanExpression notNull = QCartItem.cartItem.productId.isNotNull();
          List<CartItem> byCartId = cartItemRepository.findByCartId(1);
          boolean exists = cartItemRepository.exists(notNull);
          `