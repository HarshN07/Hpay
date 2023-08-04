class User {
    constructor(name, email, password) {
      this.name = name;
      this.email = email;
      this.password = password;
      this.balance = 1000; // You can initialize the balance here or update it later
    }
  }
  
  module.exports = User;