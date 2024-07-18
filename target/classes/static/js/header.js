const headerTemplate = document.createElement('template');
const headerTemplate2 = document.createElement('template');
headerTemplate.innerHTML = `
  <style>
    .nav {
        /*position: fixed;  Fixed position */
        top: 0; /* Stick to the top */
        left: 0;
        right: 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #4CAF50; /* Updated background color */
        /*padding: 10px; Add some padding for spacing */
    }

    .nav-child {
        flex: 1;
    }

    .nav-child a {
        color: white; /* Set link color */
        text-decoration: none; /* Remove underlines from links */
        margin-right: 10px; /* Add spacing between links */
    }

    /* Header */
    h1 {
        margin: 0; /* Remove default margin */
    }

    .nav-child.right {
        text-align: right;
    }

    .nav-child.center {
        text-align: center;
    }
  </style>
  <header xmlns:th="http://www.thymeleaf.org">
    <nav class="nav" style="background-color:chartreuse ">
        <!-- Add your navigation links here (e.g., Home, About, Contact) -->
        <div class="nav-child">
            <a href="/about">About</a>
        </div>
        <header class="nav-child center">
            <a href="/"> <h1>Welcome to SoBlogg</h1></a>
        </header>
        <!--button th:onclick="geturl('/profile')">Profile</button-->
        <div class="nav-child right">
            <a href="/signup">Sign Up</a>
            <a sec:authorize="!isAuthenticated()" href="/login">Log In</a>
        </div>
    </nav>
  </header>
`;

headerTemplate2.innerHTML = `
  <style>
    .nav {
        /*position: fixed;  Fixed position */
        top: 0; /* Stick to the top */
        left: 0;
        right: 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #4CAF50; /* Updated background color */
        /*padding: 10px; Add some padding for spacing */
    }

    .nav-child {
        flex: 1;
    }

    .nav-child a {
        color: white; /* Set link color */
        text-decoration: none; /* Remove underlines from links */
        margin-right: 10px; /* Add spacing between links */
    }

    /* Header */
    h1 {
        margin: 0; /* Remove default margin */
    }

    .nav-child.right {
        text-align: right;
    }

    .nav-child.center {
        text-align: center;
    }
  </style>
  <header xmlns:th="http://www.thymeleaf.org">
    <nav class="nav" style="background-color:chartreuse ">
        <!-- Add your navigation links here (e.g., Home, About, Contact) -->
        <div class="nav-child">
            <a href="/about">About</a>
        </div>
        <header class="nav-child center">
            <a href="/"> <h1>Welcome to SoBlogg</h1></a>
        </header>
        <!--button th:onclick="geturl('/profile')">Profile</button-->
        <div class="nav-child right">
            <a sec:authorize="isAuthenticated()" href="/profile">Profile</a>
            <a sec:authorize="isAuthenticated()" href="/logout">Logout</a>
            <a href="/signup">Sign Up</a>
        </div>
    </nav>
  </header>
`;


class Header extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const shadowRoot = this.attachShadow({ mode: 'closed' });

    shadowRoot.appendChild(headerTemplate.content);
  }
}

customElements.define('header-component', Header);

class Header2 extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const shadowRoot = this.attachShadow({ mode: 'closed' });

    shadowRoot.appendChild(headerTemplate2.content);
  }
}

customElements.define('header-component2', Header2);