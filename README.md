# 🐾 Namma Pets SBA

![Platform](https://img.shields.io/badge/Platform-Android-blue.svg)
![Tech](https://img.shields.io/badge/Backend-Firebase-orange.svg)
![Language](https://img.shields.io/badge/Language-Java-yellow.svg)
![UI](https://img.shields.io/badge/UI-XML-green.svg)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

**Namma Pets SBA** is an Android-based mobile application designed to facilitate the buying and selling of pets in an engaging and interactive platform. It offers a user-friendly interface for browsing and listing pets, secure user authentication, and real-time data management using Firebase. Whether you're a pet lover looking to adopt or a seller wanting to find a new home for your furry friends, this app simplifies the process with seamless navigation and intuitive features.

---

## 🚀 Features  

### 🛒 Pet Categories & Listings
- Browse pets conveniently categorized by species, breed, age, and more.
- Filter and sort options for a quick and personalized search experience.
- View detailed information, images, and pricing for each pet.

### 🔒 User Authentication
- Secure sign-up and login using **Firebase Authentication**.
- Password recovery options for convenience and account safety. 

### 🛍️ Buying Platform
- Explore a variety of pets available for purchase.
- Add pets to a shopping cart for a smoother buying process.
- Directly connect with sellers through the app interface.

### 📤 Selling Platform
- List pets for sale with images, descriptions, and pricing.
- Manage and update listings through a personalized dashboard.

### 🛒 **Shopping Cart**  
- Add pets to your cart for a smooth purchasing experience.  
- View and manage cart items before checkout.  

### ❤️ **Favourites Option**  
- Save favorite pets for easy access later.  
- Organize your wishlist with a single tap.  

### 👤 **Profile Management**
- Create and customize your user profile.
- View your purchase history and selling activity.

---

## 🛠️ **Tech Stack**  

- **Frontend**: Java, XML  
- **Backend**: Firebase (Authentication, Realtime Database)
- **Tools**: Android Studio  

---

## 📂 Project Structure

```
NammaPets-SBA/
└── app/
    └── src/
        └── main/
            ├── java/
            │   └── com/example/nammapetssba/
            │       ├── BirdsAdapter.java       # Adapter for displaying birds in a list
            │       ├── BirdsFragment.java      # Fragment for birds-related UI
            │       ├── CartItem.java           # Model class for cart items
            │       ├── CategoryActivity.java   # Activity for category selection
            │       ├── CategoryUIManager.java  # Utility for managing category UI
            │       ├── CatsAdapter.java        # Adapter for displaying cats in a list
            │       ├── CatsFragment.java       # Fragment for cats-related UI
            │       ├── CowsAdapter.java        # Adapter for displaying cows in a list
            │       ├── CowsFragment.java       # Fragment for cows-related UI
            │       ├── DogsAdapter.java        # Adapter for displaying dogs in a list
            │       ├── DogsFragment.java       # Fragment for dogs-related UI
            │       ├── Favourites.java         # Activity/Fragment for managing favorites
            │       ├── FishesAdapter.java      # Adapter for displaying fishes in a list
            │       ├── FishesFragment.java     # Fragment for fishes-related UI
            │       ├── GoatsAdapter.java       # Adapter for displaying goats in a list
            │       ├── GoatsFragment.java      # Fragment for goats-related UI
            │       ├── LoginActivity.java      # Activity for user login
            │       ├── MainActivity.java       # Main entry point activity
            │       ├── MainUIManager.java      # Utility for managing main UI components
            │       ├── NavigationHandler.java  # Utility for handling navigation
            │       ├── PaymentActivity.java    # Activity for payment processing
            │       ├── Pet.java                # Model class for pet data
            │       ├── PetDetailFragment.java  # Fragment for pet details UI
            │       ├── PetListings.java        # Activity/Fragment for pet listings
            │       ├── ProfileActivity.java    # Activity for user profile management
            │       ├── PugsAdapter.java        # Adapter for displaying pugs in a list
            │       ├── PugsFragment.java       # Fragment for pugs-related UI
            │       ├── RabbitsAdapter.java     # Adapter for displaying rabbits in a list
            │       ├── RabbitsFragment.java    # Fragment for rabbits-related UI
            │       ├── SellPetActivity.java    # Activity for selling a pet
            │       ├── ShoppingCart.java       # Activity/Fragment for shopping cart
            │       ├── SignupActivity.java     # Activity for user signup
            │       └── SplashActivity.java     # Splash screen activity
            └── res/
                ├── layout/                     # XML UI layouts
                │   ├── activity_category.xml   # Layout for category activity
                │   ├── activity_favourites.xml # Layout for favorites activity/fragment
                │   ├── activity_login.xml      # Layout for login activity
                │   ├── activity_main.xml       # Layout for main activity
                │   ├── activity_payment.xml    # Layout for payment activity
                │   ├── activity_pet_listings.xml # Layout for pet listings activity/fragment
                │   ├── activity_profile.xml    # Layout for profile activity
                │   ├── activity_sell_pet.xml   # Layout for sell pet activity
                │   ├── activity_shopping_cart.xml # Layout for shopping cart activity/fragment
                │   ├── activity_signup.xml     # Layout for signup activity
                │   ├── activity_splash.xml     # Layout for splash activity
                │   ├── bird_item.xml           # Layout for bird item in a list
                │   ├── cart_item.xml           # Layout for cart item in a list
                │   ├── cart_item_layout.xml一向 # Layout for cart item container
                │   ├── cat_item.xml            # Layout for cat item in a list
                │   ├── cow_item.xml            # Layout for cow item in a list
                │   ├── dialog_pet_details.xml  # Layout for pet details dialog
                │   ├── dog_item.xml            # Layout for dog item in a list
                │   ├── favourite_item.xml      # Layout for favorite item in a list
                │   ├── fish_item.xml           # Layout for fish item in a list
                │   ├── fragment_birds.xml      # Layout for birds fragment
                │   ├── fragment_cats.xml       # Layout for cats fragment
                │   ├── fragment_cows.xml       # Layout for cows fragment
                │   ├── fragment_dogs.xml       # Layout for dogs fragment
                │   ├── fragment_fishes.xml     # Layout for fishes fragment
                │   ├── fragment_goats.xml      # Layout for goats fragment
                │   ├── fragment_pet_detail.xml # Layout for pet detail fragment
                │   ├── fragment_pugs.xml       # Layout for pugs fragment
                │   ├── fragment_rabbits.xml    # Layout for rabbits fragment
                │   ├── goat_item.xml           # Layout for goat item in a list
                │   ├── pug_item.xml            # Layout for pug item in a list
                │   └── rabbit_item.xml         # Layout for rabbit item in a list
                ├── menu/                       # Menu resources
                └── values/                     # String, style, and other resources
                    └── strings.xml, styles.xml
                    └── values-night/           # Night mode resources
```
---

## 📲 Installation & Setup

### Prerequisites
- Android Studio installed
- Firebase project setup

### Steps
1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/Namma-Pets-SBA.git
   ```
2. **Open in Android Studio** and sync dependencies.
3. **Configure Firebase**:
   - Add `google-services.json` to `app/` directory.
   - Enable Firebase Authentication & Realtime Database.
3. **Run the app** on an emulator or a physical device.

---

## 🤝 Contributing
Pull requests are welcome! Feel free to **fork the repository** and submit improvements.

### Contributions are welcome! Follow these steps:
1. **Fork the project.**
2. **Create a feature branch:**
   ```sh
   git checkout -b feature-name
   ```
3. **Commit your changes:**
   ```sh
   git commit -m "Add feature description"
   ```
4. **Push to the branch:**
   ```sh
   git push origin feature-name
   ```
5. **Open a pull request.**

---

## 📧 **Contact**
For queries or suggestions:
- 📧 Email: spreveen123@gmail.com
- 🌐 LinkedIn: www.linkedin.com/in/preveen-s-17250529b/

---

## 🌟 **Show your support**
If you find this project interesting, please consider giving it a ⭐ on GitHub to show your support!

