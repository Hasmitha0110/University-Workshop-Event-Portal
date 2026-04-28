# UniPlus - University Workshop & Event Portal

<video src="https://github.com/user-attachments/assets/34820ad1-9791-4a63-b856-243fb0230101" width="100%"></video>


## 📖 About UniPlus
UniPlus is a comprehensive university event and workshop management portal. It is designed to help university communities seamlessly plan, coordinate, and explore various events on campus.

### What can UniPlus do?
- **Browse Events:** Any user can explore a catalog of upcoming university events.
- **Weekly Highlights:** The home page automatically filters and displays events scheduled for the current week, making it easy to see what's happening soon.
- **Admin Registration & Login:** University staff or authorized students can apply for admin accounts using their university credentials. The platform is secured via JWT authentication.
- **Event Management:** Authenticated Admins have access to an Admin Dashboard where they can:
  - Create new events with details like title, description, venue, date, and cover images.
  - Edit and update their own events.
  - Delete events.
  - Manage and keep track of event timelines.
- **Real-time Status:** The system computes the real-time status of events (e.g., upcoming, ongoing, past) based on the event date.

## 🛠️ Technologies Used

The project is built with a modern full-stack architecture, ensuring high performance, security, and a great user experience.

### Frontend
- **React.js & Vite:** Used for building a fast, component-driven user interface.
- **Tailwind CSS:** Used for highly customizable, utility-first styling to ensure a responsive and aesthetic modern design (with custom themes like maroon, gold, and ink).
- **React Router DOM:** For seamless client-side routing and navigation between pages.
- **Axios:** Handles asynchronous API requests to the backend.
- **Swiper:** Used for implementing modern touch-slider components.

### Backend
- **Java Spring Boot:** The core framework used to build robust RESTful APIs.
- **Spring Security & JWT (JSON Web Tokens):** Ensures secure endpoints, handling user authentication and role-based authorization.
- **Spring Data JPA & Hibernate:** ORM frameworks used to interact with the database efficiently.
- **MySQL Database:** Relational database used to store admins, events, and related data securely.
- **Testing:** 
  - **Cucumber (BDD):** Used for Behavior-Driven Development testing.
  - **Selenium WebDriver:** Used for automated UI testing.
  - **JUnit 5 & Mockito:** Used for unit and integration testing.
- **Maven:** Dependency management and build tool.
