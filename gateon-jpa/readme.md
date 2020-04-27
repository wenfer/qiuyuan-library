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
          
## 使用querydsl 需要生成类,添加Maven插件化后compile 然后把target/generated-sources/java 复制到对应的service的target中
`
    <dependencies>
        <dependency>
            <groupId>com.querydsl</groupId>
            <artifactId>querydsl-apt</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>com.mysema.maven</groupId>
                <artifactId>apt-maven-plugin</artifactId>
                <version>1.1.3</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>process</goal>
                        </goals>
                        <configuration>
                            <includes>
                                <include>cn.gateon.mengbg.cart.model</include>
                            </includes>
                            <outputDirectory>target/generated-sources/java</outputDirectory>
                            <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>`