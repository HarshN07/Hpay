const e = require('express')
const express = require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient
const bodyParser = require('body-parser');
const Split = require('./models/Split');
const Transaction = require('./models/Transaction');
const User = require('./models/User');

const url = "mongodb://localhost:27017"

app.use(express.json())

mongoClient.connect(url,(err,db)=>{
    if(err){
        console.log("Error while connecting mongo client")
    }
    else{
        const myDb=db.db('myDb')
        const transCollection=myDb.collection('Transactions-Table')
        const splitCollection=myDb.collection('Splits');
        const userCollection=myDb.collection('User')

        app.post('/signup',(req,res) =>{

            const newUser = {
                name: req.body.name,
                email: req.body.email,
                password: req.body.password
            }

            const query = {email: newUser.email}
            collection.findOne(query,(err,result)=> {
                if(result == null){
                    collection.insert(newUser,(err,result)=>{
                        res.status(200).send()
                    })
                }
                else{
                    res.status(400).send()
                }
            })

        })
        app.post('/login',(req,res)=>{

            const query = {
                email: req.body.email,
                password:req.body.password
            }

            collection.findOne(query,(err,result)=>{

                if (result != null) {
                     
                    const objToSend={
                        name:result.name,
                        email:result.email
                    }

                    res.status(200).send(JSON.stringify(objToSend))
                }
                else{
                    res.status(400).send()
                }
            })
        })
        app.post('/addsplit',(req,res)=>{
            const { name, description, amount } = req.body;
            const {selectedUsers} = req.body.selectedUsers;
            const newSplit = new Split(name, description, amount, {selectedUsers});

            splitsCollection.insertOne(newSplit, (err, result) => {
                if (err) {
                    res.status(500).send('Error adding new split');
                } else {
                    res.status(200).send('Split added successfully');
                }
            });
        })
        app.get('/getAllSplits', (req, res) => {
            splitsCollection.find({}).toArray((err, splits) => {
              if (err) {
                res.status(500).send('Error fetching splits');
              } else {
                res.json(splits);
              }
            });
        });
        app.post('/addTransaction', (req, res) => {
            const { userId, transactionId, amountPaid } = req.body;
      
            const newTransaction = new Transaction(userId, transactionId, amountPaid);
      
            transactionsCollection.insertOne(newTransaction, (err, result) => {
              if (err) {
                res.status(500).send('Error adding new transaction');
              } else {
                res.status(200).send('Transaction added successfully');
              }
            });
        });
        app.get('/getAllTransactions', (req, res) => {
            transactionsCollection.find({}).toArray((err, transactions) => {
              if (err) {
                res.status(500).send('Error fetching transactions');
              } else {
                res.json(transactions);
              }
            });
        });

    }
})

app.listen(3000,()=>{
    console.log("listening 3000.........")
})