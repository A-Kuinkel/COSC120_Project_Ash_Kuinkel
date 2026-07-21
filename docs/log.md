# <u> Project Log </u>

### <u> 16/07/2026 </u>
It's now time to decide the project. Initial thought, maybe like an end user scenario that replicates the function of an ecom store would be good. But thinking about this for a while,Ifigure narrowing the scope
down to just one category for the store would be good. I'm thinking like a computer hardware parts/acessories would be good, probably a better option than a sneaker store, like a management system for a dental clinic/
school or whatever. Simply because I think computers are more interesting than trying to build a dental clinic management system as one of my projects.... Anyways, so that's the final decision, a computer hardware parts/
accessories store.

Name of store/name of end user scenario: ByteBazaar

Core functionality: 
 - Display home page featuring 'featured products'
 - Browse product by category
 - Search for product w/ multiple filters i.e. category, brand, max price etc.
 - display the matching products & allow user to view detailed info about one specific product if they want
 - add products to cart 
 - view/edit cart (clearing cart functionality, removing item functionality, increasing quantity functionality)
 - collect customer info using most likely JOptionPane (payment flow not required for this task)
 - save completed orders to a dedicated file for like orders
 - handle cases i.e. multiple matches, one match, no match, user cancels intended actions, filtering matches by price, date etc.

I will first start off by deciding e.g. what fields each product will hold, enums, and also creating the dataset....

### <u> 18/07/2026 </u>
Just considering what I really want for product fields, i.e. category, brand, price, etc.
Initialising some key components I will need within the program, i.e. class for cart, product....

### <u> 20/07/2026 </u>
Initialise Product class with some fields I know I will need, more to be added later (if I find is needed ofc)
Initialise enums for category & brand

Lol, attempting to not use the internet for the syntax and having to search through the beginning java 17 fundamentals
book makes it take a little more time than things should, but hey, call it a learning experience I guess...

### <u> 21/07/2026 </u>
I've decided I want to add product images as a field as well for the Product class. How I will do it, is I just add the 
displayImage field into the Product class & put the cloudinary links.... but wait, that means I may have to link the 
cloudinary API etc., rather let's just have like photos for the products (there will only be like 50 products) in the 
assets folder, much simpler that way without going overboard.

Also, this week's content is a real wake-up call into how a good program should behave. I most likely will need to separate
the fields that people will search for when search for products, i.e. name, brands into the DreamProduct class & the fields
that are used to identify a product which people will not search for, i.e. id, quantity etc. This ensures we adhere to 
OOD principles, in particular each program must have a small scope at which it excels in. I cannot just attempt to have 
the product class do the job of both DreamProduct & Products themselves.

So in regard to the images, I will pick a handful of product images & embed my products with links to the images (stored 
in the assets folder).