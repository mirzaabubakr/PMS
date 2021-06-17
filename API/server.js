const express = require("express");
const mongoose = require("mongoose");
const cookieParser = require('cookie-parser');
const cors = require('cors');
const http = require('http');

const app = express();

app.use(cors()); // Cors Headers

const server = http.createServer(app);
const auth = require('./Routers/authRoute');
const user = require('./Routers/usersRoute');
const project = require('./Routers/projectRoute');
const resource = require('./Routers/resourceRoute');
const cpat = require('./Routers/cpatRoute');

//Secret file for Database Connection, token secret etc
const dbConfSecret = require('./Config/secret');

app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: true, limit: '50mb' }));
app.use(cookieParser());
// app.use(Logger('dev'));

//connect to Database
mongoose.Promise = global.Promise;
mongoose.connect(
  dbConfSecret.url,
  { useNewUrlParser: true, useUnifiedTopology: true }
);

// Routes
app.use('/api', auth);
app.use('/api', user);
app.use('/api', project);
app.use('/api', resource);
app.use('/api', cpat);

app.get('/', function (req, res) {
  res.send('The id you specified is ');
});

const port = process.env.PORT || 3002;
server.listen(port, async () => {
  console.log(`Listening at port ${port}...`);
});
