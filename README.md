# 🐾 Namma Pets SBA
![Platform](https://img.shields.io/badge/Platform-Android-blue.svg)
![Tech](https://img.shields.io/badge/Backend-Firebase-orange.svg)
![Language](https://img.shields.io/badge/Language-Java-yellow.svg)
![UI](https://img.shields.io/badge/UI-XML-green.svg)
![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)

**Namma Pets SBA** is an Android-based mobile application designed to facilitate the buying and selling of pets in an engaging and interactive platform. It offers a user-friendly interface for browsing and listing pets, secure user authentication, and real-time data management using Firebase. Whether you're a pet lover looking to buy or a seller wanting to find a new home for your furry friends, this app simplifies the process with seamless navigation and intuitive features.

---

## 📋 Table of Contents

| No. | Sections |
|-----|----------|
| 01  | [🚀 Features](#features) |
| 02  | [🛠️ Tech Stack](#tech-stack) |
| 03  | [📂 Project Structure](#project-structure) |
| 04  | [📲 Installation & Setup](#installation-setup) |
| 05  | [🤝 Contributing](#contributing) |
| 06  | [📧 Contact](#contact) |
| 07  | [🌟 Show your support](#show-your-support) |

---

<a name="features"></a>
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

<a name="tech-stack"></a>
## 🛠️ **Tech Stack**
- **Frontend**: Java, XML
- **Backend**: Firebase (Authentication, Realtime Database)
- **Tools**: Android Studio
- **Target SDK**: 24+

---

<a name="project-structure"></a>
## 📂 Project Structure

```
NammaPets-SBA/
└── app/
    └── src/
        └── main/
            ├── java/
            │   └── com/example/nammapetssba/
            │       ├── BirdsAdapter.java   # Adapter for displaying birds in a list
            │       ├── activities/         # Login, Signup, Main, Profile, SellPet, etc.
            │       ├── fragments/          # DogsFragment, CatsFragment, PetDetailFragment, etc.
            │       ├── adapters/           # DogsAdapter, CatsAdapter, etc.
            │       ├── models/             # Pet.java, CartItem.java
            │       ├── utils/              # UI managers, Navigation handlers
            └── res/
                ├── drawable/               # Image resources
                ├── layout/                 # XML UI layouts
                ├── layout/                 # XML layouts for activities and fragments
                ├── menu/                   # Menu resources
                └── values/                 # String, style, and other resources
                    └── strings.xml, styles.xml
                    └── values-night/       # Night mode resources
```
---

<a name="installation-setup"></a>
## 📲 Installation & Setup

### 📋 Prerequisites
- Android Studio installed
- Firebase project setup

<a name="steps-to-run"></a>
### 🧑‍💻 Steps to Run
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

<a name="contributing"></a>
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

<a name="contact"></a>
## 📧 **Contact**
For queries or suggestions:
- 📧 Email: spreveen123@gmail.com
- 🌐 LinkedIn: https://linkedin.com/in/preveen-s

---

<a name="show-your-support"></a>
## 🌟 **Show your support**
If you find this project interesting, please consider giving it a ⭐ on GitHub to show your support!

