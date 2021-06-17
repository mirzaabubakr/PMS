const express = require('express');
const router = express.Router();

const authCtrl = require('../Controllers/authController');

router.post('/register', authCtrl.CreateUser);
router.post('/login', authCtrl.LoginUser);

module.exports = router;