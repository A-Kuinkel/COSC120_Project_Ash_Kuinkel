import javax.swing.*;

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

//    String hashed = ByteBazaar.hashPassword("SuperSecret123!");
//    System.out.println("HASH1: " +hashed);
//
//    String hashed2 = ByteBazaar.hashPassword("SuperSecret9999999!");
//    System.out.println("HASH2: " +hashed2);

//    User tempUserInfoHolder = new User("a","b","ab@gmail.com","password",
//            "1234567","country rd");
//
//    User signedInUser = tempUserInfoHolder;
//    signedInUser = null;
//    System.out.println(signedInUser);
//    // System.out.println("USR NAME:" + signedInUser.fullName());
//
//    System.out.println(signedInUser);

    JTextField weorfnewo = new JTextField();
    JTextField wkrufvr = new JTextField();

    Object[] message = {
            "Random:", weorfnewo,
            "Another random:", wkrufvr,
    };

    int option = JOptionPane.showConfirmDialog(null, message, "eukfdvwekf:", JOptionPane.OK_CANCEL_OPTION);

    if(option == JOptionPane.OK_OPTION){
        String wekfu = weorfnewo.getText();
        String ewkfdjbew = wkrufvr.getText();
        System.out.println("RANDOM TEXT: " + wekfu + " " + ewkfdjbew);
    }
}

