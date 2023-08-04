class Transaction {
    constructor(userId, splitId, amountPaid) {
      this.userId = userId;
      this.splitId = splitId;
      this.amountPaid = amountPaid;
    }
  }
  
  module.exports = Transaction;
  