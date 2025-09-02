# EQurbani

EQurbani is a mobile application designed to facilitate the buying and selling of sacrificial animals (Qurbani) and associated services in a streamlined, trustworthy manner. The application connects buyers, sellers, butchers, and transporters, making the Qurbani process digital and accessible.

## Key Features

- **Animal Listings:** Sellers can list animals for sale, including details such as type, breed, health status, gender, vaccination status, weight, and price.
- **Bidding System:** Buyers can place bids on listed animals. Each bid stores buyer details, amount offered, payment method, and references to both buyer and seller.
- **User Distance Matching:** Buyers and sellers can view information based on proximity, helping users find nearby animals and services.
- **Third-Party Services:** Integration with butchers and transporters for post-purchase services, including proposals and price negotiations.
- **Secure Transactions:** Payment methods and user references are tracked for secure and transparent transactions.
- **Validation Utilities:** Built-in validation for phone numbers and passwords to ensure user data integrity.

## Usage

### For Buyers

- Browse through the list of available animals.
- View details of each animal, including health, breed, and owner information.
- Place bids through a dialog, specifying offered amount, address, contact, and payment method.
- Receive notifications if a bid is accepted.

### For Sellers

- List animals for sale with comprehensive information.
- Receive and review bids from potential buyers.
- Accept or reject proposals based on offered amount and buyer profile.

### For Butchers & Transporters

- Register as a service provider.
- Receive proposals for slaughtering and transporting animals.
- Accept bookings and provide services as per requirements.

## Code Structure

- `firebase/entity`: Core data models for animals, bids, places, sellers, etc.
- `activities/buyer`: Buyer-focused activities and screens (e.g., BuyCowBull).
- `utils`: Utility classes for validation, distance calculation, dialog interfaces, etc.
- `adapter`: UI adapters for lists and data binding.
- `chat`: Chatbot integration for user support.

## Getting Started

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/tech-qasim/EQurbani.git
   ```
2. **Open in Android Studio.**
3. **Configure Firebase:** Add your Firebase credentials to enable real-time database features.
4. **Build & Run the App:** Deploy to your Android device or emulator.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for improvements or new features.

## License

This project currently does not specify a license. Please contact the repository owner for usage permissions.

---

For more information, visit the [GitHub repository](https://github.com/tech-qasim/EQurbani).
