public static void main(String[] args){
//    User user = new User("John","Doe","johndoe1@gmail.com",
//            "john","+44 312 568 333","4 Country Rd London");
//
//    List<Product> products = new ArrayList<>();
//    Map<ProductAttributes,Object> existingProductAttributes = new LinkedHashMap<>();
//
//    existingProductAttributes.put(ProductAttributes.CATEGORY,Category.LAPTOPS);
//    existingProductAttributes.put(ProductAttributes.BRAND,Brand.ASUS);
//    existingProductAttributes.put(ProductAttributes.TAGS,Arrays.asList("Good","Great"));
//    existingProductAttributes.put(ProductAttributes.WIRELESS,true);
//    existingProductAttributes.put(ProductAttributes.COLOUR,"Black");
//    existingProductAttributes.put(ProductAttributes.ON_SALE,true);
//
//    DreamProduct addDreamFeaturesToExistingProduct = new DreamProduct(existingProductAttributes);
//
//    Product product = new Product("1","Product1",12.99,3,3.4f,2,
//            "Product1 is a brilliant product!","product1.img",addDreamFeaturesToExistingProduct);
//    Product product2 = new Product("2","Product2",15.50,1,4.5f,1,
//            "Product2 is also a brilliant product!","product2.img",addDreamFeaturesToExistingProduct);
//    products.add(product);
//    products.add(product2);
//
//    // Order sampleOrder = new Order(user,products);
//    Cart sampleCart = new Cart(products,user);
//
//    System.out.println(sampleCart);

    String hashed = ByteBazaar.hashPassword("SuperSecret123!");
    System.out.println("HASH1: " +hashed);

    String hashed2 = ByteBazaar.hashPassword("SuperSecret9999999!");
    System.out.println("HASH2: " +hashed2);
}

